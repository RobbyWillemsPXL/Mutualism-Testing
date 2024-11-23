package be.pxl.mutualism.pages;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage {
    private Page page;
    private static final String URL = System.getProperty("app.url");

    // Define locators for the elements
    private final Locator userNameSelector;
    private final Locator passwordSelector;
    private final Locator loginButtonSelector;
    private final Locator errorSelector;
    private final Locator continueAsGuestButtonSelector;

    public LoginPage(Page page) {
        this.page = page;
        this.userNameSelector = page.getByLabel("Username");
        this.passwordSelector = page.getByLabel("Password");
        this.loginButtonSelector = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login"));
        this.errorSelector = page.getByText("Ongeldige gebruikersnaam of");
        this.continueAsGuestButtonSelector = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Ga verder als gast"));

    }

    public void navigate() {
        page.navigate(URL);
    }

    public void login(String username, String password) {
        userNameSelector.fill(username);
        passwordSelector.fill(password);
        loginButtonSelector.click();
    }

    public Page getPage() {
        return this.page;
    }

    public void loginRedirectsToCorrectPageTest(ExtentTest test) {
        loginRedirectsToCorrectPageTest(test, null, null);
    }

    public void loginRedirectsToCorrectPageTest(ExtentTest test, String username, String password) {
        if (username == null || password == null) {
            continueAsGuest();
        } else {
            login(username, password);
        }
        verifyLogin(test);
    }

    private void verifyLogin(ExtentTest test) {
        String mapURL = URL + "map";
        page.waitForURL(mapURL);

        boolean isMapPage = page.url().equals(mapURL);

        if (isMapPage) {
            test.pass("Login geslaagd.");
        } else {
            test.fail("Login niet geslaagd.");
        }
        assertTrue(isMapPage, "Login geslaagd.");
    }

    public void faultyLoginErrorMessageTest(ExtentTest test, String username, String password) {
        login(username, password);

        boolean isLoginPage = page.url().equals(URL);

        boolean isErrorVisible = false;
        try {
            errorSelector.waitFor(new Locator.WaitForOptions().setTimeout(5000));
            isErrorVisible = errorSelector.isVisible();
        } catch (PlaywrightException e) {
            test.fail("Foutmelding is niet zichtbaar.");
        }

        if (isErrorVisible) {
            test.pass("Foutmelding is aanwezig zoals verwacht.");
        } else {
            test.fail("Foutmelding is niet zichtbaar.");
        }

        assertTrue(isErrorVisible, "Foutmelding moet aanwezig zijn.");
    }


    public void faultLoginDoesNotRedirectTest(ExtentTest test, String username, String password) {
        login(username, password);
        page.waitForURL(URL);

        boolean isMapPage = page.url().equals(URL);

        if (isMapPage) {
            test.pass("Faulty login redirect niet zoals verwacht.");
        } else {
            test.fail("Faulty login is foutief geredirect.");
        }
        assertTrue(isMapPage, "Login geslaagd.");
    }

    public void continueAsGuest() {
        continueAsGuestButtonSelector.click();
    }

    public void navigateToUpload(String uploadURL) {
        page.navigate(uploadURL);
    }
    public void testURL(ExtentTest test, String testURL) {
        page.waitForURL(testURL);
        Boolean isCorrectURL = page.url().equals(testURL);

        if (isCorrectURL) {
            test.pass("Navigatie naar " + testURL + " is geslaagd.");
        } else {
            test.fail("Navigatie naar " + testURL + " is niet geslaagd.");
        }
        assertTrue(isCorrectURL, "Navigatie naar " + testURL + " is geslaagd.");
    }
}

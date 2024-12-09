package be.pxl.mutualism.pages;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.junit.jupiter.api.Assertions;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UploadPage {
    private Page page;
    private static final String PAGE = "upload";
    private static final String URL = System.getProperty("app.url") + PAGE;


    // Define locators for the elements
    private final Locator mapSelector;
    private final Locator logOutSelector;

    private final Locator fileUploadInputSelector;
    private final Locator fileUploadButtonSelector;
    private final Locator naamErrorSelector;
    private final Locator beschrijvingErrorSelector;


    public UploadPage(Page page) {
        this.page = page;
        this.mapSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("MAP"));
        this.logOutSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("LOGOUT"));
        this.fileUploadInputSelector = page.locator("input[type='file']");
        this.fileUploadButtonSelector =  page.locator("label:has-text('klik om een bestand te selecteren')");
        this.naamErrorSelector = page.getByText("Boom met index 0: Naam");
        this.beschrijvingErrorSelector = page.getByText("Boom met index 1:");
    }

    public void clickMap() {
        mapSelector.click();
    }

    public void testURL(ExtentTest test, String testedURL) {
        page.waitForURL(testedURL);
        Boolean isCorrectURL = page.url().equals(testedURL);
        if (isCorrectURL) {
            test.pass("Navigatie naar " + testedURL + " is geslaagd.");
        } else {
            test.fail("Navigatie naar " + testedURL + " is niet geslaagd.");
        }
        assertTrue(isCorrectURL, "Navigatie naar " + testedURL + " moet geslaagd zijn.");
        System.out.println(page.url());
    }

    public void logOut() {
        logOutSelector.click();
    }

    public void uploadTest(String filePath, ExtentTest test, String URL) {
        upload(filePath);
        testURL(test, URL);
    }

    public void upload(String filePath) {

        // Klik op het label dat het bestand input-element activeert
        fileUploadButtonSelector.click();

        // Wacht tot het bestand invoerveld beschikbaar is (indien nodig)
        fileUploadInputSelector.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.ATTACHED));

        // Upload het bestand via het verborgen input-element
        fileUploadInputSelector.setInputFiles(Paths.get(filePath));
    }

    public void navigate() {
        page.navigate(URL);
    }
    public void verifyErrorVisibility(ExtentTest test) {
        naamErrorSelector.waitFor();
        beschrijvingErrorSelector.waitFor();
        boolean isNaamErrorVisible = naamErrorSelector.isVisible();
        boolean isBeschrijvingErrorVisible = beschrijvingErrorSelector.isVisible();

        if (isNaamErrorVisible && isBeschrijvingErrorVisible) {
            test.pass("Beide foutmeldingen zijn zichtbaar: 'Naam' en 'Beschrijving'.");
        } else {
            if (!isNaamErrorVisible) {
                test.fail("Foutmelding voor 'Boom met index 0: Naam' is niet zichtbaar.");
            }
            if (!isBeschrijvingErrorVisible) {
                test.fail("Foutmelding voor 'Boom met index 1:' is niet zichtbaar.");
            }
        }

        Assertions.assertTrue(isNaamErrorVisible, "Foutmelding 'Boom met index 0: Naam' moet zichtbaar zijn.");
        Assertions.assertTrue(isBeschrijvingErrorVisible, "Foutmelding 'Boom met index 1:' moet zichtbaar zijn.");
    }


}

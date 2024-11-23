package be.pxl.mutualism.pages;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UploadPage {
    private Page page;
    private static final String PAGE = "upload";
    private static final String URL = System.getProperty("app.url") + PAGE;
    

    // Define locators for the elements
    private final Locator mapSelector;
    private final Locator logOutSelector;

    public UploadPage(Page page) {
        this.page = page;
        this.mapSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("MAP"));
        this.logOutSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("LOGOUT"));

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
    }

    public void logOut() {
        logOutSelector.click();
    }
}

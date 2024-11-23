package be.pxl.mutualism.pages;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapPage {
    private Page page;
    private static final String PAGE = "map";
    private static final String URL = System.getProperty("app.url") + PAGE;

    // Define locators for the elements
    private final Locator uploadSelector;
    private final Locator logoutSelector;

    public MapPage(Page page) {
        this.page = page;
        this.uploadSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("UPLOAD"));
        this.logoutSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("LOGOUT"));
    }

    public void clickUpload() {
        uploadSelector.click();
    }

    public Page getPage() {
        return this.page;
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

    public void logOut() {
        logoutSelector.click();
    }
}

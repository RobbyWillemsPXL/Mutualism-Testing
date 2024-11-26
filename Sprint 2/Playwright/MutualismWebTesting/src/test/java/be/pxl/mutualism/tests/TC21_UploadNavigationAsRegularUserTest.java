package be.pxl.mutualism.tests;

import be.pxl.mutualism.pages.LoginPage;
import be.pxl.mutualism.utils.BrowserFactory;
import be.pxl.mutualism.utils.ReporterFactory;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

public class TC21_UploadNavigationAsRegularUserTest {
    private static Playwright playwright;
    private static Browser browser;

    // New instance for each test method.
    private BrowserContext context;
    private LoginPage loginPage;
    private ExtentTest test;
    private String loginPageURL = System.getProperty("app.url");


    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = BrowserFactory.createBrowser(playwright, true);
        ReporterFactory.getInstance();
    }

    @BeforeEach
    void createContextAndPages() {
        context = browser.newContext();
        loginPage = new LoginPage(context.newPage());
        test = ReporterFactory.createTest("TC21_UploadNavigationAsRegularUserTest",
                "Test that checks upload functionality is not" +
                        "available to a regular user.");
    }

    @AfterAll
    static void closeBrowser() {
        browser.close();
        playwright.close();
        ReporterFactory.closeReport();
    }

    @AfterEach
    void closeContext() {
        context.close();
    }

    @Test
    public void uploadNavigationAsRegUserTest() {
        loginPageURL = System.getProperty("app.url");
        loginPage.navigateToUpload(System.getProperty("app.url") + "upload");
        loginPage.testURL(test, System.getProperty("app.url"));
    }
}

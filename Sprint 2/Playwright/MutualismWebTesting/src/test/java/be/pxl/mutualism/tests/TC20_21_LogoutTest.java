package be.pxl.mutualism.tests;

import be.pxl.mutualism.pages.LoginPage;
import be.pxl.mutualism.pages.MapPage;
import be.pxl.mutualism.pages.UploadPage;
import be.pxl.mutualism.utils.BrowserFactory;
import be.pxl.mutualism.utils.ReporterFactory;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

public class TC20_21_LogoutTest {
    private static Playwright playwright;
    private static Browser browser;

    // New instance for each test method.
    private BrowserContext context;
    private LoginPage loginPage;
    private ExtentTest test;
    private MapPage mapPage;
    private UploadPage uploadPage;


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
    public void TC20_logoutFromMapPageTest() {
        test = ReporterFactory.createTest("TC20a_LogoutTest - logoutFromMapPageTest",
                "Test that checks the" +
                " logout functionality from the map page.");
        loginPage.navigate();
        loginPage.login("string", "string");
        mapPage = new MapPage(loginPage.getPage());
        mapPage.logOut();
        mapPage.testURL(test, System.getProperty("app.url"));
    }

    @Test
    public void TC21_logoutFromUploadPageTest() {
        test = ReporterFactory.createTest("TC20b_LogoutTest - logoutFromUploadPageTest",
                "Test that checks the" +
                " logout functionality from the upload page.");
        loginPage = new LoginPage(context.newPage());
        loginPage.navigate();
        loginPage.login("string", "string");
        mapPage = new MapPage(loginPage.getPage());
        mapPage.clickUpload();
        uploadPage = new UploadPage(mapPage.getPage());
        uploadPage.logOut();
        uploadPage.testURL(test, System.getProperty("app.url"));
    }
}

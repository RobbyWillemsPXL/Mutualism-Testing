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

public class TC19_AdminNavigationTest {
    private static Playwright playwright;
    private static Browser browser;

    // New instance for each test method.
    private BrowserContext context;
    private LoginPage loginPage;
    private MapPage mapPage;
    private UploadPage uploadPage;
    private ExtentTest test;


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
        test = ReporterFactory.createTest("TC19_AdminNavigationTest", "Test that checks navigation " +
                "when logged in as admin.");
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
    public void TC19_AT_AdminNavigationTest() {
        loginPage.navigate();
        loginPage.login("string", "string");

        mapPage = new MapPage(loginPage.getPage());
        mapPage.clickUpload();

        uploadPage = new UploadPage(mapPage.getPage());
        String uploadURL = System.getProperty("app.url") + "upload";
        uploadPage.testURL(test, uploadURL);
        uploadPage.clickMap();

        String mapURL = System.getProperty("app.url") + "map";
        mapPage.testURL(test, mapURL);
    }
}

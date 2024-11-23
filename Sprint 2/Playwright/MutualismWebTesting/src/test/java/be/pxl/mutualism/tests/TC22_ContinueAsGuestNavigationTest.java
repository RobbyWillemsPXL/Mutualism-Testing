package be.pxl.mutualism.tests;

import be.pxl.mutualism.pages.LoginPage;
import be.pxl.mutualism.utils.BrowserFactory;
import be.pxl.mutualism.utils.ReporterFactory;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

public class TC22_ContinueAsGuestNavigationTest {
    private static Playwright playwright;
    private static Browser browser;

    // New instance for each test method.
    private BrowserContext context;
    private LoginPage loginPage;
    private ExtentTest test;


    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = BrowserFactory.createBrowser(playwright, false);
        ReporterFactory.getInstance();
    }

    @BeforeEach
    void createContextAndPages() {
        context = browser.newContext();
        loginPage = new LoginPage(context.newPage());
        test = ReporterFactory.createTest("TC22_ContinueAsGuestNavigationTest",
                "Test that checks a succesful login.");
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
    public void navigationTest() {
        loginPage.navigate();
        loginPage.loginRedirectsToCorrectPageTest(test);
    }
}

package be.pxl.mutualism.tests;

import be.pxl.mutualism.pages.LoginPage;
import be.pxl.mutualism.pages.MapPage;
import be.pxl.mutualism.pages.UploadPage;
import be.pxl.mutualism.utils.BrowserFactory;
import be.pxl.mutualism.utils.DatabaseUtil;
import be.pxl.mutualism.utils.ReporterFactory;
import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.*;

public class TC26_UploadErrorTest {
    private static Playwright playwright;
    private static Browser browser;

    private static String dbUrl = System.getProperty("db.url", System.getenv("DB_URL"));
    private static String dbUser = System.getProperty("db.user", System.getenv("DB_USER"));
    private static String dbPassword = System.getProperty("db.password", System.getenv("DB_PASSWORD"));

    private BrowserContext context;
    private LoginPage loginPage;
    private ExtentTest test;
    private MapPage mapPage;
    private UploadPage uploadPage;

    @BeforeAll
    static void launchBrowser() {
//        DatabaseUtil.clearTreesTable(dbUrl, dbUser, dbPassword);
        playwright = Playwright.create();
        System.out.println("playwright created");
        browser = BrowserFactory.createBrowser(playwright, true);
        ReporterFactory.getInstance();
    }

    @BeforeEach
    void createContextAndPages() {
        System.out.println("before start");
        context = browser.newContext();
        loginPage = new LoginPage(context.newPage());
        test = ReporterFactory.createTest("TC25_UploadErrorTest",
                "Test dat controleert of een foutieve upload een " +
                        "error toont.");
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
    public void TC26_AT_uploadErrorVisibleTest() {
        System.out.println("test start");
        loginPage = new LoginPage(context.newPage());
        loginPage.navigate();
        loginPage.login("string", "string");

        mapPage = new MapPage(loginPage.getPage());

        mapPage.clickUpload();

        uploadPage = new UploadPage(mapPage.getPage());

        String filePath = "errortrees.json";
        uploadPage.upload(filePath);

        uploadPage.verifyErrorVisibility(test);


    }
}

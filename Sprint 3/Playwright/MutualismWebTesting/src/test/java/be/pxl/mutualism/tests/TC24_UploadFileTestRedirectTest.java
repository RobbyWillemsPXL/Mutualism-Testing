//package be.pxl.mutualism.tests;
//
//import be.pxl.mutualism.pages.LoginPage;
//import be.pxl.mutualism.pages.MapPage;
//import be.pxl.mutualism.pages.UploadPage;
//import be.pxl.mutualism.utils.BrowserFactory;
//import be.pxl.mutualism.utils.DatabaseUtil;
//import be.pxl.mutualism.utils.ReporterFactory;
//import com.aventstack.extentreports.ExtentTest;
//import com.microsoft.playwright.Browser;
//import com.microsoft.playwright.BrowserContext;
//import com.microsoft.playwright.Playwright;
//import org.junit.jupiter.api.*;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TC24_UploadFileTestRedirectTest {
//    private static Playwright playwright;
//    private static Browser browser;
//
//    private static String dbUrl = System.getProperty("db.url", System.getenv("DB_URL"));
//    private static String dbUser = System.getProperty("db.user", System.getenv("DB_USER"));
//    private static String dbPassword = System.getProperty("db.password", System.getenv("DB_PASSWORD"));
//
//    // New instance for each test method.
//    private BrowserContext context;
//    private LoginPage loginPage;
//    private ExtentTest test;
//    private MapPage mapPage;
//    private UploadPage uploadPage;
//
//
//    @BeforeAll
//    static void launchBrowser() {
////        DatabaseUtil.clearTreesTable(dbUrl, dbUser, dbPassword);
//        playwright = Playwright.create();
//        browser = BrowserFactory.createBrowser(playwright, true);
//        ReporterFactory.getInstance();
//    }
//
//    @BeforeEach
//    void createContextAndPages() {
//        context = browser.newContext();
//        loginPage = new LoginPage(context.newPage());
//        test = ReporterFactory.createTest("TC23_UploadFileTestRedirectTest",
//                "Test that checks that a succesful upload" +
//                        "correctly redirects to the map page.");
//    }
//
//    @AfterAll
//    static void closeBrowser() {
//        browser.close();
//        playwright.close();
//        ReporterFactory.closeReport();
//    }
//
//    @AfterEach
//    void closeContext() {
//        context.close();
//    }
//
//    @Test
//    public void TC24_AT_UploadFileTestRedirectTest() {
//
//        loginPage = new LoginPage(context.newPage());
//
//        loginPage.navigate();
//
//        loginPage.login("string", "string");
//
//        mapPage = new MapPage(loginPage.getPage());
//
//        mapPage.clickUpload();
//
//        uploadPage = new UploadPage(mapPage.getPage());
//
//        String filePath = "coordinates.geojson";
//
//        uploadPage.uploadTest(filePath, test, System.getProperty("app.url") + "map");
//    }
//}
//

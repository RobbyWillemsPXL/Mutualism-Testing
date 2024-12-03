package be.pxl.mutualism.pages;

import com.aventstack.extentreports.ExtentTest;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class MapPage {
    private Page page;
    private static final String PAGE = "map";
    private static final String URL = System.getProperty("app.url") + PAGE;

    // Define locators for the elements
    private final Locator uploadSelector;
    private final Locator logoutSelector;
    private final Locator treeMarkersLocator;
    private final Locator coordinatesLocator;
    private final Locator closeButton;

    public MapPage(Page page) {
        this.page = page;
        this.uploadSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("UPLOAD"));
        this.logoutSelector = page.getByRole(AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("LOGOUT"));
        this.treeMarkersLocator = page.locator("img[alt^='dynamic-marker-']");
        this.coordinatesLocator = page.locator("p:has(strong:has-text('Coördinaten:'))");
        this.closeButton = page.locator("img[alt='Close']");
    }

    public void clickUpload() {
        uploadSelector.click();
    }

    public Page getPage() {
        return this.page;
    }

    public void logOut() {
        logoutSelector.click();
    }

    public void treeIsUploadedTest(double[] expectedCoordinates, double margin, ExtentTest test) {
        treeMarkersLocator.scrollIntoViewIfNeeded();
        treeMarkersLocator.click();

        coordinatesLocator.waitFor();

        String coordinatesText = coordinatesLocator.textContent();

        Pattern pattern = Pattern.compile("Coördinaten:\\s*([0-9.]+),\\s*([0-9.]+)");
        Matcher matcher = pattern.matcher(coordinatesText);

        boolean isFound = false;

        if (matcher.find()) {
            double latitude = Double.parseDouble(matcher.group(1));
            double longitude = Double.parseDouble(matcher.group(2));

            if (Math.abs(latitude - expectedCoordinates[0]) <= margin && Math.abs(longitude - expectedCoordinates[1]) <= margin) {
                isFound = true;
            }
        }

        if (isFound) {
            test.pass("Marker gevonden met coördinaten binnen de marge: " + margin);
        } else {
            test.fail("Geen marker gevonden met coördinaten binnen de marge: " + margin);
        }

        Assertions.assertTrue(isFound, "Marker is niet gevonden binnen de marge.");
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


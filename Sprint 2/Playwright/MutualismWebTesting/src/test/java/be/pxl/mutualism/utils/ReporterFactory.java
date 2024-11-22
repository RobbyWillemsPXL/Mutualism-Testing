package be.pxl.mutualism.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReporterFactory {
    private static ExtentReports extent;
    private static ExtentTest test;

    // Initialize and configure
    public static ExtentReports getInstance() {
        if (extent == null) {
            // Initialize ExtentSparkReporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("report.html"); // specify the report file path
            sparkReporter.config().setReportName("Test Execution Report");
            sparkReporter.config().setDocumentTitle("Automation Test Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Tester", "Robby Willems");
            extent.setSystemInfo("Environment", "QA");
        }
        return extent;
    }

    // New test in report
    public static ExtentTest createTest(String testName, String description) {
        test = getInstance().createTest(testName, description);
        return test;
    }

    // Close report
    public static void closeReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}


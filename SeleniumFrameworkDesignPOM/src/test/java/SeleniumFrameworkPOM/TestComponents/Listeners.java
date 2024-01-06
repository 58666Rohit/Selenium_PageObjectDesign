package SeleniumFrameworkPOM.TestComponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import seleniumFreamworkPOM.resources.ExtentReporterNG;

public class Listeners extends BaseTest implements ITestListener {

	ExtentReports extent = ExtentReporterNG.getReport();
	ExtentTest test;
	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>(); // Thread-safe
	
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test); // uniques thread id (for ErrorValidationTest)
	}

	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
	}

	public void onTestFailure(ITestResult result) {

		extentTest.get().fail(result.getThrowable());
		
		try {
			driver = (WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());	
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String path = null;
		try {
			 path = getScreenShot(result.getMethod().getMethodName(), driver);
			 test.addScreenCaptureFromPath(path, result.getMethod().getMethodName());
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
		
	}

	public void onTestSkipped(ITestResult result) {

	}
	
	public void onFinish(ITestContext context) {
	   extent.flush();
	  }
}

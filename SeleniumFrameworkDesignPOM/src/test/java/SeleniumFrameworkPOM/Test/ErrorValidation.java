package SeleniumFrameworkPOM.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import SeleniumFrameworkPOM.TestComponents.BaseTest;
import SeleniumFrameworkPOM.TestComponents.Retry;
import SeleniumFrameworkPOM.pages.CartPage;
import SeleniumFrameworkPOM.pages.CheckOutPage;
import SeleniumFrameworkPOM.pages.ConfirmationPage;
import SeleniumFrameworkPOM.pages.LandingPage;
import SeleniumFrameworkPOM.pages.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class ErrorValidation extends BaseTest {

	@Test(groups= {"ErrorHandling"})
	public void LoginErrorValidation() throws IOException, InterruptedException {

		landingPage.loginApplication("rohit@1gmai", "Rohit@00");
		Assert.assertEquals("Incorrect email or password.", landingPage.getErrorMessage());

	}
	
	
	@Test(retryAnalyzer = Retry.class)
	public void productErrorValidation() throws IOException, InterruptedException {
		
		String productName = "IPHONE 13 PRO";
		
		ProductCatalogue productCatalogue = landingPage.loginApplication("pardeshi@99gmail.com", "Pardeshi#0101");
		List<WebElement> products = productCatalogue.getProductList(); 
		productCatalogue.addProductToCart(productName);
		CartPage cartPage = productCatalogue.goToCartPage();	
		boolean match = cartPage.verifyProductTitles(productName);
		Assert.assertFalse(match);
		
		
	}

}

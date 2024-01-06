package SeleniumFrameworkPOM.Test;

import java.io.IOException;
import java.sql.DriverManager;
import java.time.Duration;
import java.util.HashMap;
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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;

import SeleniumFrameworkPOM.TestComponents.BaseTest;
import SeleniumFrameworkPOM.pages.CartPage;
import SeleniumFrameworkPOM.pages.CheckOutPage;
import SeleniumFrameworkPOM.pages.ConfirmationPage;
import SeleniumFrameworkPOM.pages.LandingPage;
import SeleniumFrameworkPOM.pages.OrderPage;
import SeleniumFrameworkPOM.pages.ProductCatalogue;
import io.github.bonigarcia.wdm.WebDriverManager;

public class submitOrderTest extends BaseTest {
	String productName = "IPHONE 13 PRO";

	@Test(dataProvider = "getData", groups = { "purchase" })
	public void submitOrder(HashMap<String, String> map) throws IOException, InterruptedException {

		ProductCatalogue productCatalogue = landingPage.loginApplication(map.get("email"), map.get("password"));
		List<WebElement> products = productCatalogue.getProductList();
		productCatalogue.addProductToCart(map.get("productName"));

		CartPage cartPage = productCatalogue.goToCartPage();
		boolean match = cartPage.verifyProductTitles(map.get("productName"));
		Assert.assertTrue(match);

		CheckOutPage checkOutPage = cartPage.goToCheckout();
		checkOutPage.selectCountry("India");

		ConfirmationPage confirmationPage = checkOutPage.submitorder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));

	}

	@Test(dependsOnMethods = { "submitOrder" })
	public void orderHistoryTest() throws InterruptedException // to verify product displaying in order page
	{
		ProductCatalogue productCatalogue = landingPage.loginApplication("rohitp@1gmail.com", "Rohit@00");
		OrderPage orderPage = productCatalogue.goToOrdersPage();
		Thread.sleep(1000);
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));

	}

	/*
	 * @DataProvider public Object[][] getData() { return new Object[][]
	 * {{"rohitp@1gmail.com","Rohit@00","IPHONE 13 PRO"},{"pardeshi@99gmail.com",
	 * "Pardeshi#0101","ADIDAS ORIGINAL"}}; }
	 */

	@DataProvider
	public Object[][] getData() throws IOException {
		/*
		 * HashMap<String, String> map = new HashMap<String, String>(); map.put("email",
		 * "rohitp@1gmail.com"); map.put("password", "Rohit@00"); map.put("productName",
		 * "IPHONE 13 PRO");
		 * 
		 * HashMap<String, String> map1 = new HashMap<String, String>();
		 * map1.put("email", "pardeshi@99gmail.com"); map1.put("password",
		 * "Pardeshi#0101"); map1.put("productName", "ADIDAS ORIGINAL");
		 */

		List<HashMap<String, String>> data = getJsonDataToMap(
				System.getProperty("user.dir") + "\\data\\PurchaseOrder.json");
		return new Object[][] { { data.get(0) }, { data.get(1) } };
	}

}

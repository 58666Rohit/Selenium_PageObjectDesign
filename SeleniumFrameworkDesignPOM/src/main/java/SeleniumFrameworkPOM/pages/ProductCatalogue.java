package SeleniumFrameworkPOM.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import SeleniumAbstractComponents.AbstractComponent;

public class ProductCatalogue extends AbstractComponent{

	WebDriver driver;
	
	public ProductCatalogue(WebDriver driver) // constructor to get driver 
	{
		super(driver);
		// initialization
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	//PageFactory webElements
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	@FindBy(css=".ng-animating")
	WebElement spinner;
	
	
	By productWait = By.cssSelector(".mb-3");   // pagefactory not applicable for By Element
	By addToCart = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");
	
	public List<WebElement> getProductList()
	{
		waitForElementToAppear(productWait); // Wait until element appear on webpage.
		return products;
	}
	
	public WebElement getProductByName(String productName)
	{
		WebElement prodName = getProductList().stream().filter(product -> 
		product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);
		return prodName;
	}
	
	public void addProductToCart(String productName) throws InterruptedException
	{
		 WebElement prod = getProductByName(productName);
		 prod.findElement(addToCart).click();
		 waitForElementToAppear(toastMessage);
		 waitForElementToDisappear(spinner);
		
	}
}

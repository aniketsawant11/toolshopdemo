package stepDefinitons;

import hooks.Hooks;
import io.cucumber.java.en.*;

import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CartSteps {
	  private final WebDriver driver = Hooks.driver;
	    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    @Given("the user has at least a product in the cart")
	    public void ensure_cart_has_product() {
	    	  By cartElement = By.xpath("//*[@id='navbarSupportedContent']/ul/li[5]/a");

	          try {
	              WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(cartElement));

	              if (cart.isDisplayed()) {
	                  cart.click();

	                  // Check if cart has items
	                  By cartItems = By.cssSelector(".cart-items .cart-item");
	                  if (driver.findElements(cartItems).isEmpty()) {
	                      System.out.println("Cart is empty. Navigating to main page to add an item...");
	                      addItemToCart();
	                  } else {
	                      System.out.println("Cart already has items.");
	                      Assert.assertTrue(true);
	                  }
	              }
	          } catch (Exception e) {
	              System.out.println("Cart element not found. Navigating to main page to add an item...");
	              addItemToCart();
	          }
	      }

	      private void addItemToCart() {
	          // Navigate to home page
	          driver.get("https://practicesoftwaretesting.com/");

	          // Example: select first product link
	          By firstProduct = By.xpath("(//a[contains(@href,'/product')])[1]");
	          wait.until(ExpectedConditions.elementToBeClickable(firstProduct)).click();

	          // Add to cart button
	          By addToCartBtn = By.xpath("//button[@id='add-to-cart' or @id='btn-add-to-cart']");
	          wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();

	          // View cart link
	          By viewCartLink = By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[5]/a");
	          if (!driver.findElements(viewCartLink).isEmpty()) {
	              wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
	          } else {
	              driver.get("https://practicesoftwaretesting.com/checkout");
	          }
	    	
	    	}
	        
	    

	    @When("the user views the cart")
	    public void user_views_cart() {
	    	By viewCartLink = By.id("view-cart");
	        if (!driver.findElements(viewCartLink).isEmpty()) {
	            wait.until(ExpectedConditions.elementToBeClickable(viewCartLink)).click();
	        } else {
	            driver.get("https://practicesoftwaretesting.com/checkout");
	        }
	    }

	    @Then("the cart page should show the product list")
	    public void cart_page_should_show_product_list() {
	    	By cartItems = By.xpath("//app-cart//table/tbody/tr");
	    	wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(cartItems));
	    	Assert.assertFalse(driver.findElements(cartItems).isEmpty(), "Cart product list is empty!");

	    }

	    @Then("the cart total should be displayed")
	    public void cart_total_should_be_displayed() {
	    	By cartTotal = By.xpath("/html/body/app-root/div[2]/app-checkout/aw-wizard/div/aw-wizard-step[1]/app-cart/div/table/tfoot/tr/td[4]");
	    	wait.until(ExpectedConditions.presenceOfElementLocated(cartTotal));
	    	Assert.assertTrue(driver.findElement(cartTotal).isDisplayed(), "Cart total is not displayed!");

	    }
}

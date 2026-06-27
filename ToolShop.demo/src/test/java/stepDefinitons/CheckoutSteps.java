package stepDefinitons;




import io.cucumber.java.en.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import hooks.Hooks;

import java.time.Duration;


public class CheckoutSteps {
	 private final WebDriver driver = Hooks.driver;
	    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    @Given("the user has at least one product in the cart")
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

	    @And("the user is on the cart page")
	    public void user_is_on_cart_page() {
	        driver.get("https://practicesoftwaretesting.com/checkout");
	    }

	    @When("the user proceeds to checkout")
	    public void user_proceeds_to_checkout() throws InterruptedException {
	        By checkoutBtn = By.xpath("/html/body/app-root/div[2]/app-checkout/aw-wizard/div/aw-wizard-step[1]/app-cart/div/div/button[2]");
	        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
	        By emailField = By.id("email");
	        By passwordField = By.id("password");

	        // Using XPath instead of CSS
	         

	        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
	        driver.findElement(emailField).sendKeys("customer3@practicesoftwaretesting.com");
	        driver.findElement(passwordField).clear();
	        driver.findElement(passwordField).sendKeys("pass123");
	        Thread.sleep(100);
	      driver.findElement(By.className("btnSubmit")).click();
	      driver.findElement(By.xpath("/html/body/app-root/div[2]/app-checkout/aw-wizard/div/aw-wizard-step[2]/app-login/div/div/div/div/button")).click();
	    }

	    @And("the user fills shipping details with housenumber {string},postalCode {string}")
	    public void user_fills_shipping_details(String housen,String pc) throws InterruptedException {
	       
	        
	        driver.findElement(By.id("postal_code")).sendKeys(pc);
	        driver.findElement(By.id("house_number")).sendKeys(housen);
	       Thread.sleep(3000);
	    }

	    @Then("the user confirms the order")
	    public void user_confirms_order() {
	    	   driver.findElement(By.id("postal_code")).sendKeys("4555");
		        driver.findElement(By.id("house_number")).sendKeys("222");
	    	
	    	 By confirmOrderBtn = By.xpath("/html/body/app-root/div[2]/app-checkout/aw-wizard/div/aw-wizard-step[3]/app-address/div/div/div/div/button");

	    	    // Wait until the element is present
	    	    WebElement button = wait.until(ExpectedConditions.elementToBeClickable(confirmOrderBtn));

	    	    // Scroll the element into view
	    	    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
	    	        "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
	    	        button
	    	    );

	    	    // Now click the button
	    	    wait.until(ExpectedConditions.elementToBeClickable(button)).click();
	    }
@And("the user choose payment method")
public void the_user_chose_payment_method() throws InterruptedException {
	By dropdwn =By.id("payment-method");
	WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdwn));
	Select dropdown = new Select(dropdownElement);
	dropdown.selectByVisibleText("Cash on Delivery");
	driver.findElement(By.xpath("/html/body/app-root/div[2]/app-checkout/aw-wizard/div/aw-wizard-completion-step/app-payment/div/div/div/div/button")).click();
	Thread.sleep(1500);
}

	    @Then("the order confirmation message should be displayed")
	    public void order_confirmation_displayed() throws InterruptedException {
	    	Thread.sleep(1000);
	    	driver.findElement(By.xpath("/html/body/app-root/div[2]/app-checkout/aw-wizard/div/aw-wizard-completion-step/app-payment/div/div/div/div/button")).click();
	        By confirmationMessage = By.id("order-confirmation");
	        wait.until(ExpectedConditions.presenceOfElementLocated(confirmationMessage));
	        Assert.assertTrue(!driver.findElements(confirmationMessage).isEmpty());
	    }
}

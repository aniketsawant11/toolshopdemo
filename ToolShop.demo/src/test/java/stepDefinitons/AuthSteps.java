package stepDefinitons;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import hooks.Hooks;

import java.time.Duration;
import java.util.Map;


public class AuthSteps {
	
	    private final WebDriver driver = Hooks.driver;
	    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

	    @Given("the user is on the ToolShop home page")
	    public void user_on_the_home_page() {
	        Assert.assertTrue(driver.getCurrentUrl().contains("practicesoftwaretesting"));
	    }

	    @When("the user logs in with credentials:")
	    public void user_logs_in_with_credentials(DataTable table) throws InterruptedException {
	        Map<String, String> creds = table.asMap(String.class, String.class);

	       
	        By loginLink = By.xpath("//*[@id=\"navbarSupportedContent\"]/ul/li[4]/a");

	        // Using ID directly
	        By emailField = By.id("email");
	        By passwordField = By.id("password");

	        // Using XPath instead of CSS
	         

	        wait.until(ExpectedConditions.elementToBeClickable(loginLink)).click();
	        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).clear();
	        driver.findElement(emailField).sendKeys(creds.get("username"));
	        driver.findElement(passwordField).clear();
	        driver.findElement(passwordField).sendKeys(creds.get("password"));
	        Thread.sleep(200);
	      driver.findElement(By.className("btnSubmit")).click();
	    }

	    @Then("the user should be logged in")
	    public void user_should_be_logged_in() {
	        // Using XPath instead of CSS
	        By userProfile = By.xpath("//*[@id=\"menu\"]");
	        wait.until(ExpectedConditions.presenceOfElementLocated(userProfile));
	        Assert.assertTrue(!driver.findElements(userProfile).isEmpty());
	    }

	    @When("the user logs out")
	    public void user_logs_out() {
	    	 driver.findElement(By.id("menu")).click();
	        // Using LinkText / PartialLinkText instead of CSS
	        By logoutLink = By.partialLinkText("Sign out");
	        wait.until(ExpectedConditions.elementToBeClickable(logoutLink)).click();
	    }

	    @Then("the user should be logged out")
	    public void user_should_be_logged_out() {
	        // Using LinkText / PartialLinkText instead of CSS
	        By loginLink = By.xpath("/html/body/app-root/div[2]/app-login/div/div/div/h3");
	        wait.until(ExpectedConditions.presenceOfElementLocated(loginLink));
	        Assert.assertFalse(driver.findElements(loginLink).isEmpty());
	    }
	}

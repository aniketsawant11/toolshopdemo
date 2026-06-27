package stepDefinitons;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import hooks.Hooks;

import java.time.Duration;
public class SearchSteps {
	private final WebDriver driver = Hooks.driver;
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("the user is on the home page")
    public void user_on_home_page() {
        Assert.assertTrue(driver.getCurrentUrl().contains("practicesoftwaretesting"));
    }

    @When("the user search for {string}")
    public void user_searches_for(String query) {
    	 
    	 By searchBox = By.id("search-query");
         By searchButton = By.cssSelector("button[type='submit'], button.search-button");

         // Wait for the search box to be present
         WebElement box = wait.until(ExpectedConditions.presenceOfElementLocated(searchBox));

         // Scroll the search box into view using JavascriptExecutor
         ((JavascriptExecutor) driver).executeScript(
             "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'nearest'});",
             box
         );

         // Optionally add a small pause to allow any lazy UI to settle (avoid Thread.sleep in production)
         // wait.until(ExpectedConditions.elementToBeClickable(searchBox));

         // Interact with the search box and button
         wait.until(ExpectedConditions.elementToBeClickable(searchBox)).clear();
         driver.findElement(searchBox).sendKeys(query);
         wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    @Then("search results should contain at least one product")
    public void search_results_should_contain_at_least_one_product() {
        By resultsList = By.className("container");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(resultsList));
        Assert.assertTrue(!driver.findElements(resultsList).isEmpty());
    }
}

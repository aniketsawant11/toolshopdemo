package stepDefinitons;

import io.cucumber.java.en.*;
import org.testng.Assert;

import hooks.Hooks;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductSteps {
    private final WebDriver driver = Hooks.driver;
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Given("the user is on the Tool Shop home page")
    public void user_on_the_home_page() {
        Assert.assertTrue(driver.getCurrentUrl().contains("practicesoftwaretesting"));
    }
    @When("the user searches for {string}")
    public void user_searches_for(String query) throws InterruptedException  {
    	 
   	 By searchBox = By.id("search-query");
        By searchButton = By.xpath("//*[@id=\"filters\"]/form[2]/div/button[2]");

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
        Thread.sleep(200);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    @When("the user selects the first product from search results")
    public void user_selects_first_product() {
        By firstResult = By.xpath("/html/body/app-root/div[2]/app-overview/div[3]/div[2]/div[1]/a[1]");
        wait.until(ExpectedConditions.elementToBeClickable(firstResult)).click();
    }

    @Then("the product details page should be displayed")
    public void product_details_page_should_be_displayed() throws InterruptedException {
        By productDetail = By.id("description");
        wait.until(ExpectedConditions.presenceOfElementLocated(productDetail));
        Assert.assertTrue(!driver.findElements(productDetail).isEmpty());
        Thread.sleep(200);
    }

    @When("the user adds the product to the cart")
    public void user_adds_product_to_cart() {
        By addToCartBtn = By.xpath("//*[@id=\"btn-add-to-cart\"]");
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }

    @Then("a success message for adding to cart should be shown")
    public void success_message_for_adding_to_cart_should_be_shown() throws InterruptedException {
    	Thread.sleep(2000);
        By successToast = By.id("toast-container");
        wait.until(ExpectedConditions.presenceOfElementLocated(successToast));
        Assert.assertTrue(!driver.findElements(successToast).isEmpty());
    }

}

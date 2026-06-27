package hooks;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
public class Hooks {
    public static WebDriver driver;
    private String baseUrl;

    @Before
    public void setUp() {
        // Selenium Manager will resolve the driver automatically when you create ChromeDriver
        EdgeOptions options = new EdgeOptions();

        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-popup-blocking");
        driver = new EdgeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        baseUrl = loadBaseUrl();
        driver.get(baseUrl);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private String loadBaseUrl() {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            Properties p = new Properties();
            p.load(in);
            return p.getProperty("base.url", "https://practicesoftwaretesting.com");
        } catch (Exception e) {
            return "https://practicesoftwaretesting.com";
        }
    }
}

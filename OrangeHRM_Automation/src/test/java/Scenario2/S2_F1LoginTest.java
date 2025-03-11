package Scenario2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import pages.S2_F11LoginPage;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class S2_F1LoginTest {
    WebDriver driver;
    S2_F11LoginPage loginPage;

    @BeforeMethod
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\KESHA\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        //options.addArguments("--headless=new");  // Run in headless mode (no UI)
        options.addArguments("--remote-allow-origins=*"); 
        driver = new ChromeDriver(options);
        options.addArguments("--disable-blink-features=AutomationControlled");  // Bypass bot detection
        options.addArguments("--start-maximized");  // Maximize browser
        options.addArguments("--remote-allow-origins=*");
        driver.manage().window().maximize();
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        System.out.println("Checking if login elements exist...");
        System.out.println("Username Field Found: " + driver.findElement(By.name("username")).isDisplayed());
        System.out.println("Password Field Found: " + driver.findElement(By.name("password")).isDisplayed());
        System.out.println("Login Button Found: " + driver.findElement(By.xpath("//button[contains(@class, 'oxd-button')]")).isDisplayed());
        loginPage = new S2_F11LoginPage(driver);
    }

    @Test
    public void testValidLogin() {
        loginPage.loginToOrangeHRM("Admin", "admin123");
        try {
            Thread.sleep(5000); // Pause for 5 seconds
        } catch (InterruptedException e) {
            e.printStackTrace(); // Print the error (optional)
        }
        // Add assertion here to validate login success
        String expectedURL = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        String actualURL = driver.getCurrentUrl();
        System.out.println("Current URL after login: " + actualURL);
        assert actualURL.contains("/dashboard") : "Login Failed!";
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}

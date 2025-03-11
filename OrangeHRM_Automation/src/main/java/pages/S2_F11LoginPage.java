package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class S2_F11LoginPage {
    WebDriver driver;
    WebDriverWait wait; // Ensure wait is initialized properly

    // Constructor
    public S2_F11LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Initialize wait
    }

    // Locators
    private By usernameField = By.xpath("//input[contains(@placeholder,'Username')]");
    private By passwordField = By.xpath("//input[contains(@placeholder,'Password')]");
    private By loginButton = By.xpath("//button[@type='submit']");

    // Methods
    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }

    public void loginToOrangeHRM(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }
}

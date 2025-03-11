package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.netty.handler.timeout.TimeoutException;

import java.time.Duration;
import java.util.List;

public class S2_F21AdminPage {
    WebDriver driver;
    WebDriverWait wait;
    Actions actions;

    // Constructor
    public S2_F21AdminPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased timeout
        this.actions = new Actions(driver);
    }

    // Locators
    private By leftMenuOptions = By.xpath("//ul[@class='oxd-main-menu']/li");
    private By adminMenuItem = By.xpath("//span[text()='Admin']");
    private By usernameField = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[1]/div/div[2]/input");
    private By searchButton = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[2]");
    private By resetButton = By.xpath("//button[contains(@class, 'oxd-button')]//i[contains(@class, 'bi-x')]");
    private By totalRecords = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span");

    private By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')]");
    private By userRoleOptions = By.xpath("//div[contains(@class,'oxd-select-dropdown')]/div");

    private By userStatusDropdown = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')]");
    private By userStatusOptions = By.xpath("//div[contains(@class,'oxd-select-dropdown')]/div");

    private By resultCount = By.xpath("//span[contains(text(),'Records Found')]");

    // Navigate to Admin Page
    public void navigateToAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuItem)).click();
    }

    // Search by Username
    public void searchByUsername(String username) {
        navigateToAdmin();
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        usernameInput.clear();
        usernameInput.sendKeys(username);

        waitForSearchButtonAndClick();
        //printResults();
    }

    // Search by User Role
    public void searchByUserRole(String role) {
        navigateToAdmin();
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown));
        dropdown.click();

        selectDropdownOption(userRoleOptions, role);
        waitForSearchButtonAndClick();
        //printResults();
    }

    // Search by User Status
    public void searchByUserStatus(String status) {
        navigateToAdmin();
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(userStatusDropdown));
        dropdown.click();

        selectDropdownOption(userStatusOptions, status);
        waitForSearchButtonAndClick();
        //printResults();
    }

    // Select an option from the dropdown
    private void selectDropdownOption(By locator, String value) {
        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        for (WebElement option : options) {
            if (option.getText().trim().equals(value)) {
                actions.moveToElement(option).click().perform();
                break;
            }
        }
    }

    // Wait for Search button to be clickable and click it
    private void waitForSearchButtonAndClick() {
    	try {
	        WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", searchBtn);
	        searchBtn.click();
	        System.out.println("Search button clicked successfully.");
	    } catch (Exception e) {
	        //System.out.println("Click failed. Attempting JavaScript click.");
	        WebElement searchBtn = wait.until(ExpectedConditions.presenceOfElementLocated(searchButton));
	        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchBtn);
	    }
    }

    // Print total records found after search
    /*private void printResults() {
        try {
            WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(resultCount));
            System.out.println("Search Result: " + resultElement.getText());
        } catch (Exception e) {
            System.out.println("No records found.");
        }
    }*/

    // Get all 12 menu options
    public int getLeftMenuOptions() {
        List<WebElement> options = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(leftMenuOptions));
        return options.size();
    }

    // Click on Admin Menu
    public void clickOnAdmin() {
        wait.until(ExpectedConditions.elementToBeClickable(adminMenuItem)).click();
    }

    // Get total records found
    public String getTotalRecords() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait for the total records element to be visible
        WebElement totalRecordsElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//div[contains(@class, 'orangehrm-horizontal-padding')]//span")
        ));

        String recordsText = totalRecordsElement.getText();
        String numberOnly = recordsText.replaceAll("[^0-9]", ""); // Extract only numbers
        return numberOnly.isEmpty() ? "0" : numberOnly; // Handle case where no records are found
    }

    // Refresh Page
    /*public void refreshPage() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        try {
            // Wait for the close button to be clickable
            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(@class, 'oxd-button')]//i[contains(@class, 'bi-x')]")
            ));
            closeButton.click();
            System.out.println("Close button clicked successfully.");
        } catch (TimeoutException e) {
            System.out.println("Close button not found within timeout. Attempting JavaScript click.");
            
            try {
                WebElement closeButton = driver.findElement(By.xpath("//button[contains(@class, 'oxd-button')]//i[contains(@class, 'bi-x')]"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
                System.out.println("JavaScript click executed.");
            } catch (Exception ex) {
                System.out.println("Close button still not found. Skipping refresh.");
            }
        }
    }*/
}

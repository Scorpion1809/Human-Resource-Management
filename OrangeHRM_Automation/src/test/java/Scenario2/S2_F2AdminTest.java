package Scenario2;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.S2_F11LoginPage;
import pages.S2_F21AdminPage;

import java.time.Duration;

public class S2_F2AdminTest {
    WebDriver driver;
    S2_F11LoginPage loginPage;
    S2_F21AdminPage adminPage;

    @BeforeMethod
    public void setUp() {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\KESHA\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        //options.addArguments("--headless=new");  // Run in headless mode (no UI)
        options.addArguments("--remote-allow-origins=*"); 
        driver = new ChromeDriver(options);
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*"); 
        //driver = new ChromeDriver(options);

        // Set implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Open URL
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        // Initialize LoginPage with driver
        loginPage = new S2_F11LoginPage(driver);
        loginPage.loginToOrangeHRM("Admin", "admin123");

        // Initialize AdminPage with driver
        adminPage = new S2_F21AdminPage(driver);
    }

    @Test
    public void testGetAllMenuOptions() {
        int menuCount = adminPage.getLeftMenuOptions();
        System.out.println("Total menu options: " + menuCount);
        assert menuCount == 12 : "Expected 12 menu options, but found " + menuCount;
        adminPage.clickOnAdmin();
        try {
            Thread.sleep(5000); // 5-second pause
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchByUsername() {
        adminPage.searchByUsername("Admin");
        int recordCount = Integer.parseInt(adminPage.getTotalRecords());
        System.out.println("Total records found: " + recordCount);
        //adminPage.refreshPage();
        try {
            Thread.sleep(5000); // 5-second pause
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchByUserRole() {
        adminPage.searchByUserRole("Admin");
        int recordCount = Integer.parseInt(adminPage.getTotalRecords());
        System.out.println("Total records found: " + recordCount);
        //adminPage.refreshPage();
        try {
            Thread.sleep(5000); // 5-second pause
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearchByUserStatus() {
        adminPage.searchByUserStatus("Enabled");
        int recordCount = Integer.parseInt(adminPage.getTotalRecords());
        System.out.println("Total records found: " + recordCount);
        //adminPage.refreshPage();
        try {
            Thread.sleep(5000); // 5-second pause
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}


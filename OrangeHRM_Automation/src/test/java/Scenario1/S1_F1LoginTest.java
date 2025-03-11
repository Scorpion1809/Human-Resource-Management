package Scenario1;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.*;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class S1_F1LoginTest {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    String filePath = "D:\\TrainingAutomation\\Capstone_project\\OrangeHRM\\TestData.xlsx";  // Update this
    String sheetName = "Sheet1";  // Update if needed

    @BeforeClass
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\KESHA\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*"); // Fixes session issues
        options.addArguments("--disable-extensions"); // Prevent extension conflicts
        options.addArguments("--disable-gpu"); // Fixes GPU-related failures
        options.addArguments("--no-sandbox"); // Bypass OS security restrictions
        options.addArguments("--disable-dev-shm-usage"); // Prevents session crashes
        options.addArguments("--start-maximized"); // Maximize browser window
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Extent Report setup
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        List<String[]> data = utils.S1_F2ExcelReader.getTestData(filePath, sheetName);
        return data.toArray(new Object[0][0]);
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        test = extent.createTest("Login Test - " + username);

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.xpath("//button[@type='submit']"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        loginButton.click();

        try {
            WebElement dashboard = driver.findElement(By.xpath("//h6[text()='Dashboard']"));
            if (dashboard.isDisplayed()) {
                System.out.println(username + ": PASS");
                test.pass("Login successful");
                captureScreenshot(username + "_pass.png");
                
                // Logout
                driver.findElement(By.className("oxd-userdropdown-name")).click();
                driver.findElement(By.xpath("//a[text()='Logout']")).click();
            }
        } catch (Exception e) {
            System.out.println(username + ": FAIL");
            test.fail("Login failed");
            captureScreenshot(username + "_fail.png");
        }
    }

    public void captureScreenshot(String fileName) {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            File destFile = new File("test-output/screenshots/" + fileName);
            FileUtils.copyFile(srcFile, destFile);
            test.addScreenCaptureFromPath("screenshots/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        extent.flush();  // Generate the Extent Report
        driver.quit();
    }
}

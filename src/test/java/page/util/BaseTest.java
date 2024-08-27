package page.util;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Date; 
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseTest {
	
    public WebDriver driver;

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().driverVersion("127.0.6533.120").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-headless");
        driver = new ChromeDriver(options);
    }
	
    @AfterTest
    public void tearDown() throws InterruptedException, IOException {
    	Thread.sleep(3000);
    	driver.quit();
    }
}



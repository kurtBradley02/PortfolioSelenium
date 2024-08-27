package page.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import page.object.LoginPage;
import page.util.BaseTest;

public class ValidateUserCantLoginInvalidUsername extends BaseTest {
    
    LoginPage loginPage;
    
    @Test(priority = 1)
    public void validateUserCantLoginInvalidUsername() {
    	
        loginPage = new LoginPage(driver);
        loginPage.navigate("file:///C:/Users/kurtb/OneDrive/Desktop/Login.html");
    	
        loginPage.getUsername().sendKeys("user");
        loginPage.getPassword().sendKeys("123456");
        loginPage.getLoginButton().click();
        
        assertEquals(loginPage.getFailModal().getText().equals("Login Fail"), true);
    }

}
package page.tests;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.*;

import page.object.LoginPage;
import page.util.BaseTest;
import page.util.Report;

public class ValidateUserCanLoginSuccessfully extends BaseTest {
    
    LoginPage loginPage;
    
    @Test(priority = 1)
    public void validateUserCanLoginSuccessfully() throws Exception {
    	
    	try {
    	
        loginPage = new LoginPage(driver);
        loginPage.navigate("file:///C:/Users/kurtb/OneDrive/Desktop/Login.html");
    	
        loginPage.getUsername().sendKeys("admin");
        loginPage.getPassword().sendKeys("123456");
        loginPage.getLoginButton().click();
 
        assertEquals(loginPage.getSuccessModal().getText().equals("Login Success"), true);

    	}catch(Exception e) {
    		
    		Report.generate("validateUserCanLoginSuccessfully", driver, e);
    		
    		throw e;
    	}
        
    }
    
    
    

}
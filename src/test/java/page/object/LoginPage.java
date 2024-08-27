package page.object;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;



public class LoginPage{
	
	WebDriver driver;
	
    @FindBy(id = "username1")
    private WebElement username;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(xpath = "/html/body/button")
    private WebElement loginButton;
    
    @FindBy(xpath = "//*[@id=\"successModal\"]/div/h3")
    private WebElement successModal;
    
    @FindBy(xpath = "//*[@id=\"failModal\"]/div/h3")
    private WebElement failModal;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

	public WebDriver getDriver() {
		return driver;
	}
	
	public void navigate(String address) {
		driver.get(address);
	}
	
	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	

	public WebElement getUsername() {
		return username;
	}

	public WebElement getPassword() {
		return password;
	}

	public WebElement getLoginButton() {
		return loginButton;
	}

	public WebElement getSuccessModal() {
		return successModal;
	}

	public WebElement getFailModal() {
		return failModal;
	}
	
}

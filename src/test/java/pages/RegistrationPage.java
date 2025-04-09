package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {
    private WebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement nameInput;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@type='password' and @name='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(),'Зарегистрироваться')]")
    private By registerButton;

    public  By registerButtonLocator = By.xpath("//button[contains(text(),'Зарегистрироваться')]");

    // Геттер для доступа к локатору
    public By getRegisterButtonLocator() {
        return registerButtonLocator;
    }

    @FindBy(xpath = "//a[text()='Войти']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(),'Некорректный пароль')]")
    private WebElement passwordErrorMessage;

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterName(String name) {
        nameInput.sendKeys(name);
    }

    public void enterEmail(String email) {
        emailInput.sendKeys(email);
    }

    public void enterPassword(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public void clickRegisterButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement registerButtonElement = wait.until(ExpectedConditions.elementToBeClickable(registerButton));
        registerButtonElement.click();
    }
}


package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {
    private WebDriver driver;

    @FindBy(xpath = "//input[@name='name']")
    private WebElement nameInput;

    @FindBy(xpath = "//label[text()='Email']/following-sibling::input")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@type='password' and @name='Пароль']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[contains(text(),'Зарегистрироваться')]")
    private WebElement registerButton;

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
        try {
            // Задержка 10 секунд
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Actions actions = new Actions(driver);

        try {
            // Первая попытка клика
            actions.moveToElement(registerButton).click().build().perform();
        } catch (StaleElementReferenceException e) {
            // Повторный поиск элемента и повтор клика
            registerButton = driver.findElement(By.xpath("//button[text()='Зарегистрироваться']")); // или другой локатор
            actions.moveToElement(registerButton).click().build().perform();
        }
    }


    public boolean isPasswordErrorDisplayed() {
        return passwordErrorMessage.isDisplayed();
    }
}


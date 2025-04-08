package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;

    // Поле Email
    @FindBy(xpath = "//input[@name='name']")
    private WebElement emailInput;

    // Поле Пароль
    @FindBy(xpath = "//input[@name='Пароль']")
    private WebElement passwordInput;

    // Кнопка "Войти"
    @FindBy(xpath = "//button[text()='Войти']")
    private WebElement loginButton;

    // Ссылка "Зарегистрироваться"
    @FindBy(xpath = "//a[text()='Зарегистрироваться']")
    private WebElement registerLink;

    // Ссылка "Восстановить пароль"
    @FindBy(xpath = "//a[text()='Восстановить пароль']")
    private WebElement forgotPasswordLink;

    // Конструктор
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Ввести Email
    public void enterEmail(String email) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(emailInput));
        emailInput.click(); // иногда помогает активировать поле
        emailInput.clear(); // очищаем поле, если там что-то есть
        emailInput.sendKeys(email);
    }

    // Ввести пароль
    public void enterPassword(String password) {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
    }

    public void clickLoginButton() {
          try {
        // Задержка 5 секунд
        Thread.sleep(10000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    Actions actions = new Actions(driver);
        actions.moveToElement(loginButton).click().build().perform();
}

    // Нажать на "Зарегистрироваться"
    public void clickRegisterLink() {
        registerLink.click();
    }

    // Нажать на "Восстановить пароль"
    public void clickForgotPasswordLink() {
        forgotPasswordLink.click();
    }
}

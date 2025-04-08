package tests;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.example.drivers.YandexDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.PersonalCabinetPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class AccountTests {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private PersonalCabinetPage personalCabinetPage;
    private final String baseUrl = "https://stellarburgers.nomoreparties.site";

    @Before
    public void setUp() {
        YandexDriver yandexDriver = new YandexDriver();
        driver = yandexDriver.getDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get(baseUrl);
        mainPage = new MainPage(driver);
    }

    @Step("Вход в аккаунт с логином {email}")
    private void login(String email, String password) {
        mainPage.clickPersonalCabinetButton();
        loginPage = new LoginPage(driver);
        loginPage.enterEmail(email);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        mainPage.clickPersonalCabinetButton();

        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/account/profile"));
    }

    @Test
    @Description("Проверка перехода в личный кабинет после логина")
    @Severity(SeverityLevel.CRITICAL)
    public void testNavigateToPersonalCabinet() {
        login("testuser@mail.com", "password123");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement personalCabinetButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Личный Кабинет']"))
        );

        Assert.assertTrue("Кнопка 'Личный Кабинет' не отображается после входа", personalCabinetButton.isDisplayed());
    }

    @Test
    @Description("Проверка перехода из личного кабинета в конструктор через кнопку и логотип")
    @Severity(SeverityLevel.NORMAL)
    public void testNavigateFromPersonalCabinetToConstructor() {
        login("testuser@mail.com", "password123");
        personalCabinetPage = new PersonalCabinetPage(driver);

        goToConstructor();
        goBackToConstructorViaLogo();
    }

    @Step("Переход по кнопке 'Конструктор'")
    private void goToConstructor() {
        personalCabinetPage.clickConstructor();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bunsTab = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Булки']"))
        );
        Assert.assertTrue("Элемент 'Булки' не найден", bunsTab.isDisplayed());
    }

    @Step("Переход по клику на логотип")
    private void goBackToConstructorViaLogo() {
        mainPage.clickPersonalCabinetButton();
        personalCabinetPage.clickLogo();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement bunsTab = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Булки']"))
        );
        Assert.assertTrue("Переход по логотипу не сработал", bunsTab.isDisplayed());
    }

    @Test
    @Description("Выход из аккаунта")
    @Severity(SeverityLevel.CRITICAL)
    public void testLogout() {
        login("testuser@mail.com", "password123");
        personalCabinetPage = new PersonalCabinetPage(driver);
        mainPage.clickPersonalCabinetButton();

        personalCabinetPage.clickLogout();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement loginButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Войти']"))
        );
        Assert.assertTrue("После выхода кнопка 'Войти' не отображается", loginButton.isDisplayed());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
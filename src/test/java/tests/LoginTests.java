package tests;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.drivers.YandexDriver;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

@Epic("Stellar Burgers")
@Feature("Авторизация")
public class LoginTests {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site/api";
    private static final String testEmail = "test-data@yandex.ru";
    private static final String testPassword = "password";
    private static String accessToken;

    @Before
    public void setUp() {
        YandexDriver yandexDriver = new YandexDriver();
        driver = yandexDriver.getDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://stellarburgers.nomoreparties.site");
        mainPage = new MainPage(driver);
        createTestUser();
    }

    @Step("Создание тестового пользователя через API")
    private static void createTestUser() {
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"Test User\"}", testEmail, testPassword);

        Response response = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth/register");

        if (response.statusCode() == 200) {
            accessToken = "Bearer " + response.jsonPath().getString("accessToken");
        } else {
            System.out.println("User already exists or error in creation: " + response.getBody().asString());
        }
    }

    @Step("Удаление тестового пользователя через API")
    private static void deleteTestUser(String token) {
        given()
                .baseUri(BASE_URI)
                .header("Authorization", token)
                .when()
                .delete("/auth/user")
                .then()
                .statusCode(202);
    }

    @Step("Авторизация тестового пользователя")
    private void login() {
        loginPage = new LoginPage(driver);
        loginPage.enterEmail(testEmail);
        loginPage.enterPassword(testPassword);
        loginPage.clickLoginButton();
    }

    @Test
    @Description("Авторизация через кнопку 'Войти в аккаунт' на главной странице")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginViaAccountButton() {
        clickLoginFromMainPage();
        login();
    }

    @Test
    @Description("Авторизация через кнопку 'Личный кабинет'")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginViaPersonalCabinetButton() {
        clickPersonalCabinetFromMainPage();
        login();
        // Проверка, что перешли в ЛК
        mainPage.clickPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/account/profile"));
    }

    @Test
    @Description("Авторизация через форму регистрации -> Войти")
    @Severity(SeverityLevel.NORMAL)
    public void loginFromRegistrationForm() {
        clickLoginFromMainPage();
        loginPage = new LoginPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        loginPage.clickRegisterLink();
        registrationPage.clickLoginButton();
        login();
        // Проверка, что перешли в ЛК
        mainPage.clickPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/account/profile"));
    }

    @Test
    @Description("Авторизация через форму восстановления пароля -> Войти")
    @Severity(SeverityLevel.NORMAL)
    public void loginFromForgotPasswordForm() {
        clickLoginFromMainPage();
        loginPage = new LoginPage(driver);
        loginPage.clickForgotPasswordLink();
        clickLoginFromForgotPassword();
        login();
        // Проверка, что перешли в ЛК
        mainPage.clickPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/account/profile"));
    }

    @Step("Кликаем на кнопку 'Войти в аккаунт'")
    private void clickLoginFromMainPage() {
        mainPage.clickLoginButton();
    }

    @Step("Кликаем на кнопку 'Личный кабинет'")
    private void clickPersonalCabinetFromMainPage() {
        mainPage.clickPersonalCabinetButton();
    }

    @Step("Переход из формы восстановления пароля на логин")
    private void clickLoginFromForgotPassword() {
        driver.findElement(By.xpath("//a[text()='Войти']")).click();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterClass
    public static void cleanup() {
        if (accessToken != null) {
            deleteTestUser(accessToken);
        }
    }
}

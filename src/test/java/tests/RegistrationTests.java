package tests;

import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.drivers.ChromeDriver;
import org.junit.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import pages.MainPage;
import pages.RegistrationPage;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

@Epic("Stellar Burgers")
@Feature("Регистрация")
public class RegistrationTests {
    private WebDriver driver;
    private MainPage mainPage;
    private LoginPage loginPage;
    private RegistrationPage registrationPage;

    private static final String BASE_URI = "https://stellarburgers.nomoreparties.site/api";
    private static final String testUser = "tester0904251";
    private static final String testEmail = "tester0904251@mail.com";
    private static final String testPassword = "password123";
    private static String accessToken;

    @Before
    public void setUp() {
        //YandexDriver yandexDriver = new YandexDriver();
       // driver = yandexDriver.getDriver();
        ChromeDriver chromeDriverSetup = new ChromeDriver();
        driver = chromeDriverSetup.getDriver();
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.get("https://stellarburgers.nomoreparties.site");
        registrationPage = new RegistrationPage(driver);
        mainPage = new MainPage(driver);
        // Попробуем получить токен, если пользователь уже зарегистрирован
        fetchAccessToken();

        // Если токен получен — удалим пользователя
        if (accessToken != null) {
            deleteTestUser(accessToken);
            accessToken = null; // сбросим, чтобы не мешал в тесте
        }
    }


    @Test
    @Description("Успешная регистрация с валидными данными")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulRegistration() {
        // Переход на страницу регистрации
        clickLoginFromMainPage();
        loginPage = new LoginPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        loginPage.clickRegisterLink();

        // Заполнение формы и регистрация
        fillRegistrationForm(testUser, testEmail, testPassword);

        // Используем By для поиска и клика по кнопке
          WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement registerButtonElement = wait.until(ExpectedConditions.elementToBeClickable(registrationPage.getRegisterButtonLocator()));
        registerButtonElement.click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(
                ExpectedConditions.urlContains("/login"));
        // Ожидание после нажатия на кнопку регистрации
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Авторизация после успешной регистрации
        login();
        // Проверяем, что залогинились
        mainPage.clickPersonalCabinetButton();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("/account/profile"));
    }

    @Test
    @Description("Ошибка при регистрации с коротким паролем")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidPasswordError() {
        clickLoginFromMainPage();
        loginPage = new LoginPage(driver);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        loginPage.clickRegisterLink();

        String shortPassword = "123";
        String uniqueEmail = "shortpass" + System.currentTimeMillis() + "@mail.com";
        String uniqueName = "Tester" + System.currentTimeMillis();

        fillRegistrationForm(uniqueName, uniqueEmail, shortPassword);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement registerButtonElement = wait.until(ExpectedConditions.elementToBeClickable(registrationPage.getRegisterButtonLocator()));
        registerButtonElement.click();
    }

    @Step("Заполнение формы регистрации: имя = {0}, email = {1}, пароль = {2}")
    private void fillRegistrationForm(String name, String email, String password) {
        registrationPage.enterName(name);
        registrationPage.enterEmail(email);
        registrationPage.enterPassword(password);
    }

    @Step("Авторизация под зарегистрированным пользователем")
    private void login() {
        loginPage = new LoginPage(driver);
        loginPage.enterEmail(testEmail);
        loginPage.enterPassword(testPassword);
        loginPage.clickLoginButton();

        // Получение токена после логина
        fetchAccessToken();
    }

    @Step("Получение accessToken через API")
    private void fetchAccessToken() {
        String requestBody = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", testEmail, testPassword);

        Response response = given()
                .baseUri(BASE_URI)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/auth/login");

        if (response.statusCode() == 200) {
            accessToken = response.jsonPath().getString("accessToken");
        } else {
            System.out.println("Ошибка получения токена: " + response.getBody().asString());
        }

    }

    @Step("Кликаем на кнопку 'Войти в аккаунт'")
    private void clickLoginFromMainPage() {
        mainPage.clickLoginButton();
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

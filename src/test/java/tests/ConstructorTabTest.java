package tests;

import io.qameta.allure.*;
import org.example.drivers.YandexDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

import static org.junit.Assert.assertEquals;

@Epic("Stellar Burgers")
@Feature("Навигация по табам конструктора")
public class ConstructorTabTest {
    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        YandexDriver yandexDriver = new YandexDriver();
        driver = yandexDriver.getDriver();
        driver.get("https://stellarburgers.nomoreparties.site/");
        mainPage = new MainPage(driver);
    }

    @Test
    @Description("Проверка, что таб 'Булки' активируется при клике")
    @Severity(SeverityLevel.NORMAL)
    public void checkBunsTabIsActive() {
        clickFillingsTab(); // Чтобы "Булки" был неактивен
        clickBunsTab();
        checkActiveTabText("Булки");
    }

    @Test
    @Description("Проверка, что таб 'Соусы' активируется при клике")
    @Severity(SeverityLevel.NORMAL)
    public void checkSaucesTabIsActive() {
        clickSaucesTab();
        checkActiveTabText("Соусы");
    }

    @Test
    @Description("Проверка, что таб 'Начинки' активируется при клике")
    @Severity(SeverityLevel.NORMAL)
    public void checkFillingsTabIsActive() {
        clickFillingsTab();
        checkActiveTabText("Начинки");
    }

    @Step("Кликаем на таб 'Булки'")
    private void clickBunsTab() {
        mainPage.clickBunsTab();
    }

    @Step("Кликаем на таб 'Соусы'")
    private void clickSaucesTab() {
        mainPage.clickSaucesTab();
    }

    @Step("Кликаем на таб 'Начинки'")
    private void clickFillingsTab() {
        mainPage.clickFillingsTab();
    }

    @Step("Проверяем, что активный таб — это: {expectedTabText}")
    private void checkActiveTabText(String expectedTabText) {
        String actualText = mainPage.getActiveTabText();
        assertEquals("Активный таб не соответствует ожидаемому", expectedTabText, actualText);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

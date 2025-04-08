package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Главная страница (Stellar Burgers).
 */
public class MainPage {
    private WebDriver driver;

    // Кнопка "Войти в аккаунт"
    @FindBy(xpath = "//button[text()='Войти в аккаунт']")
    private WebElement loginButton;

    // Ссылка/кнопка "Личный Кабинет" (верхний правый угол)
    @FindBy(xpath = "//p[text()='Личный Кабинет']")
    private WebElement personalCabinetButton;

    // Табы: "Булки", "Соусы", "Начинки"
    @FindBy(xpath = "//span[text()='Булки']")
    private WebElement bunsTab;

    @FindBy(xpath = "//span[text()='Соусы']")
    private WebElement saucesTab;

    @FindBy(xpath = "//span[text()='Начинки']")
    private WebElement fillingsTab;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Нажать на кнопку "Войти в аккаунт".
     */
    public void clickLoginButton() {
        try {
            // Задержка 5 секунд
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Actions actions = new Actions(driver);
        actions.moveToElement(loginButton).click().build().perform();

    }
    /**
     * Нажать на кнопку/ссылку "Личный Кабинет".
     */
    public void clickPersonalCabinetButton() {
      try {
        // Задержка 5 секунд
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
    Actions actions = new Actions(driver);
        actions.moveToElement(personalCabinetButton).click().build().perform();{

    }}

    /**
     * Клик по табу "Булки".
     */
    public void clickBunsTab() {
        bunsTab.click();
    }

    /**
     * Клик по табу "Соусы".
     */
    public void clickSaucesTab() {
        saucesTab.click();
    }

    /**
     * Клик по табу "Начинки".
     */
    public void clickFillingsTab() {
        fillingsTab.click();
    }

    public String getActiveTabText() {
        return driver.findElement(By.xpath("//div[contains(@class, 'tab_tab_type_current')]//span")).getText();
    }
}

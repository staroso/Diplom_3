package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Страница личного кабинета.
 */
public class PersonalCabinetPage {
    private WebDriver driver;

    // Кнопка "Выйти"
    @FindBy(xpath = "//button[text()='Выход']")
    private WebElement logoutButton;

    // Кнопка или ссылка "Конструктор"
    @FindBy(xpath = "//p[text()='Конструктор']")
    private WebElement constructorButton;

    // Логотип Stellar Burgers для возврата на главную страницу
    @FindBy(xpath = "//div[contains(@class, 'AppHeader_header__logo')]")
    private WebElement logo;

    public PersonalCabinetPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickLogout() {
         try {
          //  Задержка 5 секунд
           Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
       }
        Actions actions = new Actions(driver);
       actions.moveToElement(logoutButton).click().build().perform();
         //logoutButton.click();
    }


    public void clickConstructor() {
        try {
            // Задержка 5 секунд, чтобы страница успела обновиться (если необходимо)
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Actions actions = new Actions(driver);
        actions.moveToElement(constructorButton).click().build().perform();
    }


    public void clickLogo() {
        try {
            // Задержка 5 секунд
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Actions actions = new Actions(driver);
        actions.moveToElement(logo).click().build().perform();
       // logo.click();
    }

    public boolean isLogoutButtonDisplayed() {
        return logoutButton.isDisplayed();
    }
}

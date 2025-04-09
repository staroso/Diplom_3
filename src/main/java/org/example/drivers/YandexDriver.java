package org.example.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class YandexDriver {

    private WebDriver driver;

    // Конструктор, который настраивает драйвер для Яндекс.Браузера
    public YandexDriver() {
        // Укажите путь к ChromeDriver
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Pups\\Downloads\\chromedriver.exe");

        // Укажите путь к Яндекс.Браузеру
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Users\\Pups\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");

        // Создайте новый экземпляр драйвера
        driver = new ChromeDriver(options);
        System.out.println("Starting Yandex Browser...");

    }

    // Метод для получения WebDriver
    public WebDriver getDriver() {
        return driver;
    }

    // Метод для закрытия браузера
    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}


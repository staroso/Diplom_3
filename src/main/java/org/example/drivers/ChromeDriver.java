package org.example.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriver {

    private WebDriver driver;

    public ChromeDriver() {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Pups\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        driver = new org.openqa.selenium.chrome.ChromeDriver(options);
        System.out.println("Starting Google Chrome...");
    }


    public WebDriver getDriver() {
        return driver;
    }

    public void quit() {
        if (driver != null) {
            driver.quit();
        }
    }
}

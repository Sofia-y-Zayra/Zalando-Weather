package org.ulpgc.dacd.model.scraper;

import org.ulpgc.dacd.control.ProductParser;
import org.ulpgc.dacd.model.Product;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.*;

public class ZalandoScraper {

    private final ProductParser searcher = new ProductParser();
    public List<Product> scrape() {

        List<Product> products = new ArrayList<>();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);

        List<String> urls = List.of(

                "https://www.zalando.es/mujer/?q=camisetas",
                "https://www.zalando.es/mujer/?q=pantalones",
                "https://www.zalando.es/mujer/?q=chaquetas",

                "https://www.zalando.es/hombre/?q=camisetas",
                "https://www.zalando.es/hombre/?q=pantalones",
                "https://www.zalando.es/hombre/?q=chaquetas"
        );

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            for (String url : urls) {

                System.out.println("Entrando en: " + url);

                driver.get(url);

                try {
                    WebElement cookies = wait.until(
                            ExpectedConditions.elementToBeClickable(By.id("uc-btn-accept-banner"))
                    );
                    cookies.click();
                } catch (Exception ignored) {
                    System.out.println("Cookies ya aceptadas o no presentes");
                }

                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("article")));

                List<WebElement> items = driver.findElements(By.cssSelector("article"));

                System.out.println("Productos encontrados: " + items.size());

                for (WebElement item : items) {

                    Product product = searcher.find(item, url);

                    if (product != null) {
                        products.add(product);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error general: " + e.getMessage());
        }

        driver.quit();

        return products;
    }
}





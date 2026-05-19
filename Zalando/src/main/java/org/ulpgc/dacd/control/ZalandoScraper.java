package org.ulpgc.dacd.control;

import org.ulpgc.dacd.model.Product;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.ulpgc.dacd.model.ZalandoEvent;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class ZalandoScraper {

    private final ProductParser searcher = new ProductParser();

    private final EventPublisher publisher =
            new EventPublisher();

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

                System.out.println("\n========================");
                System.out.println("SCRAPING:");
                System.out.println(url);
                System.out.println("========================");

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
                        ZalandoEvent event =
                                new ZalandoEvent(
                                        UUID.randomUUID().toString(),
                                        Instant.now().toString(),
                                        "zalando",
                                        "NEW_PRODUCT",
                                        product
                                );

                        publisher.publish(
                                "Product",
                                event
                        );
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





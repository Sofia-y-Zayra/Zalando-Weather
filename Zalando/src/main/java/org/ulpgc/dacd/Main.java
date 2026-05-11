package org.ulpgc.dacd;

import org.ulpgc.dacd.control.EventPublisher;
import org.ulpgc.dacd.model.EventZalando;
import org.ulpgc.dacd.persistence.DataBaseManager;
import org.ulpgc.dacd.model.Product;
import org.ulpgc.dacd.model.scraper.ZalandoScraper;

import java.util.List;
import java.util.concurrent.*;


public class Main {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        DataBaseManager.createTable();

        Runnable task = () -> {
            ZalandoScraper scraper = new ZalandoScraper();
            List<Product> products = scraper.scrape();

            System.out.println("TOTAL PRODUCTOS: " + products.size());

            EventPublisher publisher = new EventPublisher();

            for (Product p : products) {

                EventZalando event = new EventZalando(
                        java.time.Instant.now().toString(),
                        "zalando-feeder",
                        p
                );

                try {
                    publisher.publish("Product", event);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.println("Datos guardados correctamente");
        };


        scheduler.scheduleAtFixedRate(task, 0, 24, TimeUnit.HOURS);
    }
}
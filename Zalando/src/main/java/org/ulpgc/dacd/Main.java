package org.ulpgc.dacd;

import org.ulpgc.dacd.persistence.DataBaseManager;
import org.ulpgc.dacd.model.Product;
import org.ulpgc.dacd.control.ZalandoScraper;

import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        ScheduledExecutorService scheduler =
                Executors.newScheduledThreadPool(1);

        DataBaseManager.createTable();

        Runnable task = () -> {

            ZalandoScraper scraper =
                    new ZalandoScraper();

            List<Product> products =
                    scraper.scrape();

            System.out.println(
                    "TOTAL PRODUCTOS: "
                            + products.size()
            );

            System.out.println(
                    "Datos enviados correctamente"
            );
        };

        scheduler.scheduleAtFixedRate(
                task,
                0,
                24,
                TimeUnit.HOURS
        );
    }
}
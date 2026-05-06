package org.ulpgc;

import org.ulpgc.control.EventSubscriber;
import org.ulpgc.persistence.DatamartStore;
import org.ulpgc.persistence.EventStoreLoader;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO BUSINESS UNIT (SPRINT 3) ===");


        DatamartStore datamart = new DatamartStore();


        EventStoreLoader loader = new EventStoreLoader(datamart);
        loader.loadAll();


        EventSubscriber subscriber = new EventSubscriber(datamart);
        subscriber.start();


        new RecommendationAPI(datamart).start();

        System.out.println("Sistema listo para recibir consultas.");
    }
}
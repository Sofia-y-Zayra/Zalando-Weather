package org.ulpgc;

import org.ulpgc.control.RecommendationAPI;
import org.ulpgc.model.EventStoreLoader;
import org.ulpgc.model.EventSubscriber;
import org.ulpgc.model.persistence.DatamartStore;

public class Main {

    public static void main(String[] args) {

        DatamartStore datamart =
                new DatamartStore();

        EventStoreLoader loader =
                new EventStoreLoader(datamart);

        loader.loadAll();

        EventSubscriber subscriber =
                new EventSubscriber(datamart);

        subscriber.start();

        RecommendationAPI api =
                new RecommendationAPI(datamart);

        api.start();
    }
}
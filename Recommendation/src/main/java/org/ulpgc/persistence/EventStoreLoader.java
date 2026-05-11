package org.ulpgc.persistence;

import org.ulpgc.model.*;
import org.ulpgc.service.EventRestorer;

import java.util.List;


public class EventStoreLoader {

    private final EventFileReader reader;
    private final EventRestorer restorer;

    public EventStoreLoader(DatamartStore datamart) {

        this.reader = new EventFileReader();

        this.restorer = new EventRestorer(
                new WeatherRepository(datamart),
                new ProductRepository(datamart)
        );
    }

    public void loadAll() {

        System.out.println("Reconstruyendo Datamart desde EventStore...");

        List<EventLine> events = reader.readAll();

        for (EventLine event : events) {
            restorer.restore(event.json(), event.topic());
        }

        System.out.println("Reconstrucción completada.");
    }
}
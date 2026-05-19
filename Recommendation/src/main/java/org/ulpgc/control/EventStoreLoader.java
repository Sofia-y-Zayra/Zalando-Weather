package org.ulpgc.control;



import org.ulpgc.control.persistence.DatamartStore;

import java.util.List;
import java.util.Map;

public class EventStoreLoader {

    private final EventFileReader reader;

    private final EventHandler handler;

    public EventStoreLoader(
            DatamartStore datamart
    ) {

        this.reader =
                new EventFileReader();

        this.handler =
                new EventHandler(datamart);
    }

    public void loadAll() {

        System.out.println(
                "Reconstruyendo Datamart desde EventStore..."
        );

        Map<String, List<String>> events =
                reader.readAll();

        for (String topic : events.keySet()) {

            List<String> jsonEvents =
                    events.get(topic);

            for (String json : jsonEvents) {

                handler.handle(
                        json,
                        topic
                );
            }
        }

        System.out.println(
                "Reconstrucción completada."
        );
    }
}
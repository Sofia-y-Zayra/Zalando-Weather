package org.ulpgc.control;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.ulpgc.service.DatamartUpdater;
import org.ulpgc.persistence.DatamartStore;

import javax.jms.Message;
import javax.jms.TextMessage;

public class EventProcessor {

    private final DatamartUpdater updater;
    private final Gson gson = new Gson();

    public EventProcessor(DatamartStore datamart) {
        this.updater = new DatamartUpdater(datamart);
    }

    public void process(Message message, String topic) {

        try {
            if (message instanceof TextMessage tm) {

                String json = tm.getText();

                JsonObject obj = gson.fromJson(json, JsonObject.class);

                updater.update(obj, topic);

                System.out.println("Evento procesado: " + topic);
            }

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
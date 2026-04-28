package org.ulpgc.control;

import org.ulpgc.model.DatamartUpdater;
import org.ulpgc.model.EventWriter;
import org.ulpgc.model.DatamartStore;

import javax.jms.Message;
import javax.jms.TextMessage;

// EventProcessor.java
public class EventProcessor {

    private final DatamartUpdater updater;

    public EventProcessor(DatamartStore datamart) {
        this.updater = new DatamartUpdater(datamart);
    }

    public void process(Message message, String topic) {
        try {
            if (message instanceof TextMessage textMessage) {

                String json = textMessage.getText();

                EventWriter.write(topic, json);
                updater.update(json, topic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
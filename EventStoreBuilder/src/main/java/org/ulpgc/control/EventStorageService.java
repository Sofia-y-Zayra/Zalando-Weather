package org.ulpgc.control;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class EventStorageService {

    private final EventFileManager fileManager =
            new EventFileManager();

    public void save(
            String topic,
            String json
    ) {

        try {

            JsonObject obj =
                    JsonParser
                            .parseString(json)
                            .getAsJsonObject();

            String ss =
                    obj.get("ss").getAsString();

            String ts =
                    obj.get("ts").getAsString();

            String date =
                    ts.substring(0, 10)
                            .replace("-", "");

            fileManager.appendEvent(
                    topic,
                    ss,
                    date,
                    json
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

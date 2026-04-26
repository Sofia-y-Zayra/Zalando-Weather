package org.ulpgc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventWriter {
    public static void write(String topic, String json) {

        try {
            Gson gson = new Gson();

            JsonObject obj =
                    gson.fromJson(json, JsonObject.class);

            String ss = obj.get("ss").getAsString();

            String date =
                    LocalDate.now()
                            .format(DateTimeFormatter.BASIC_ISO_DATE);

            String path =
                    "eventstore/"
                            + topic + "/"
                            + ss;

            File folder = new File(path);
            folder.mkdirs();

            File file =
                    new File(folder, date + ".events");

            FileWriter writer =
                    new FileWriter(file, true);

            writer.write(json + "\n");

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

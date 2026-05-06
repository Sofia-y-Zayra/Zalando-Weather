package org.ulpgc.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;

public class EventWriter {
    public static void write(String topic, String json) {
        try {
            JsonObject obj = new Gson().fromJson(json, JsonObject.class);

 
            String ss = obj.get("ss").getAsString();


            String ts = obj.get("ts").getAsString();
            String date = ts.substring(0, 10).replace("-", "");


            String path = "eventstore/" + topic + "/" + ss;

            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs(); // Crea todas las carpetas si no existen
            }

            File file = new File(folder, date + ".events");


            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(json + "\n");
            }

        } catch (Exception e) {
            System.err.println("Error al escribir el evento: " + e.getMessage());
        }
    }
}
package org.ulpgc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.FileWriter;

public class EventWriter {
    public static void write(String topic, String json) {
        try {
            JsonObject obj = new Gson().fromJson(json, JsonObject.class);

            // Sacamos el identificador de la fuente (ej: "weather-feeder-canarias" o el de Zalando)
            String ss = obj.get("ss").getAsString();

            // Sacamos la fecha del campo 'ts' del evento para el nombre del archivo
            // Si el ts es "2026-04-27T15:30:00Z", esto saca "20260427"
            String ts = obj.get("ts").getAsString();
            String date = ts.substring(0, 10).replace("-", "");

            // Construimos la ruta: eventstore/{topic}/{ss}/
            String path = "eventstore/" + topic + "/" + ss;

            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs(); // Crea todas las carpetas si no existen
            }

            File file = new File(folder, date + ".events");

            // Escribimos el JSON al final del archivo (append = true) y añadimos salto de línea
            try (FileWriter writer = new FileWriter(file, true)) {
                writer.write(json + "\n");
            }

        } catch (Exception e) {
            System.err.println("Error al escribir el evento: " + e.getMessage());
        }
    }
}
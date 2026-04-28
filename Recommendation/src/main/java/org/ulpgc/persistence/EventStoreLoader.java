package org.ulpgc.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

public class EventStoreLoader {
    private final DatamartStore datamart;
    private final Gson gson = new Gson();

    public EventStoreLoader(DatamartStore datamart) {
        this.datamart = datamart;
    }

    public void loadAll() {
        File root = new File("eventstore");
        if (!root.exists()) return;
        System.out.println("Iniciando reconstrucción del Datamart desde histórico...");
        processFolder(root);
    }

    private void processFolder(File folder) {
        File[] files = folder.listFiles();
        if (files == null) return;

        for (File file : files) {
            if (file.isDirectory()) {
                processFolder(file);
            } else if (file.getName().endsWith(".events")) {
                loadFile(file);
            }
        }
    }

    private void loadFile(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String topic = file.getParentFile().getParentFile().getName(); // Obtiene 'Weather' o 'Product'
            while ((line = br.readLine()) != null) {
                updateDatamartFromLine(line, topic);
            }
        } catch (Exception e) {
            System.err.println("Error leyendo histórico: " + file.getPath());
        }
    }

    private void updateDatamartFromLine(String json, String topic) {
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        String ts = obj.has("ts") ? obj.get("ts").getAsString() : "";
        if (topic.equalsIgnoreCase("Weather")) {
            datamart.updateWeather(obj.get("city").getAsString(), obj.get("temp").getAsDouble(), obj.get("condition").getAsString(), ts);
        } else {
            datamart.upsertProduct(obj.get("id").getAsString(), obj.get("name").getAsString(), obj.get("price").getAsDouble(), obj.get("category").getAsString(), ts);
        }
    }
}
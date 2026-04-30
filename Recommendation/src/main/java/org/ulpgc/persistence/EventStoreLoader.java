package org.ulpgc.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.ulpgc.model.DatamartStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.ulpgc.model.WeatherRepository;
import org.ulpgc.model.ProductRepository;
import org.ulpgc.dacd.model.Product;


public class EventStoreLoader {

    private final Gson gson = new Gson();
    private final WeatherRepository weatherRepo;
    private final ProductRepository productRepo;

    public EventStoreLoader(DatamartStore datamart) {
        this.weatherRepo = new WeatherRepository(datamart);
        this.productRepo = new ProductRepository(datamart);
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
            String topic = file.getParentFile().getParentFile().getName();

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

            if (!obj.has("data")) return;

            JsonObject data = obj.getAsJsonObject("data");

            weatherRepo.save(
                    data.get("city").getAsString(),
                    data.get("temperature").getAsDouble(),
                    data.get("description").getAsString(),
                    ts
            );

        } else if (topic.equalsIgnoreCase("Product")) {

            if (!obj.has("payload")) return;

            JsonObject pObj = obj.getAsJsonObject("payload");

            Product p = new Product();

            p.setName(pObj.get("name").getAsString());
            p.setPrice(pObj.get("price").getAsDouble());
            p.setCategory(pObj.get("category").getAsString());

            p.setBrand(pObj.has("brand") ? pObj.get("brand").getAsString() : "");
            p.setColor(pObj.has("color") ? pObj.get("color").getAsString() : "");
            p.setImageUrl(pObj.has("imageUrl") ? pObj.get("imageUrl").getAsString() : "");

            productRepo.save(p, ts);
        }
    }
}
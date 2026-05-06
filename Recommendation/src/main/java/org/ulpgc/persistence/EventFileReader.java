package org.ulpgc.persistence;

import org.ulpgc.model.EventLine;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EventFileReader {

    public List<EventLine> readAll() {

        List<EventLine> events = new ArrayList<>();

        File root = new File("eventstore");

        if (!root.exists()) {
            return events;
        }

        processFolder(root, events);

        return events;
    }

    private void processFolder(File folder, List<EventLine> events) {

        File[] files = folder.listFiles();

        if (files == null) return;

        for (File file : files) {

            if (file.isDirectory()) {

                processFolder(file, events);

            } else if (file.getName().endsWith(".events")) {

                readFile(file, events);
            }
        }
    }

    private void readFile(File file, List<EventLine> events) {

        try (BufferedReader br =
                     new BufferedReader(new FileReader(file))) {

            String topic =
                    file.getParentFile().getParentFile().getName();

            String line;

            while ((line = br.readLine()) != null) {

                events.add(new EventLine(topic, line));
            }

        } catch (Exception e) {

            System.err.println("Error leyendo: " + file.getPath());
        }
    }
}

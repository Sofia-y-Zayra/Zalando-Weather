package org.ulpgc.model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventFileReader {

    private static final String ROOT =
            "eventstore";

    public Map<String, List<String>> readAll() {

        Map<String, List<String>> events =
                new HashMap<>();

        File root =
                new File(ROOT);

        if (!root.exists()) {

            System.out.println(
                    "No existe eventstore/"
            );

            return events;
        }

        File[] topicDirs =
                root.listFiles(File::isDirectory);

        if (topicDirs == null) {
            return events;
        }

        for (File topicDir : topicDirs) {

            String topic =
                    topicDir.getName();

            events.putIfAbsent(
                    topic,
                    new ArrayList<>()
            );

            File[] sourceDirs =
                    topicDir.listFiles(File::isDirectory);

            if (sourceDirs == null) {
                continue;
            }

            for (File sourceDir : sourceDirs) {

                File[] eventFiles =
                        sourceDir.listFiles(
                                (dir, name) ->
                                        name.endsWith(".events")
                        );

                if (eventFiles == null) {
                    continue;
                }

                for (File file : eventFiles) {

                    readFile(
                            file,
                            topic,
                            events
                    );
                }
            }
        }

        return events;
    }

    private void readFile(
            File file,
            String topic,
            Map<String, List<String>> events
    ) {

        try (
                BufferedReader reader =
                        new BufferedReader(
                                new FileReader(file)
                        )
        ) {

            String line;

            while ((line = reader.readLine()) != null) {

                if (!line.isBlank()) {

                    events.get(topic)
                            .add(line);
                }
            }

            System.out.println(
                    "Archivo leído: "
                            + file.getPath()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}
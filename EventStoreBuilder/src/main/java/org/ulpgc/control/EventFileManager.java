package org.ulpgc.control;

import java.io.File;
import java.io.FileWriter;

public class EventFileManager {

    public void appendEvent(
            String topic,
            String ss,
            String date,
            String json
    ) {

        try {

            String directoryPath =
                    "eventstore/"
                            + topic
                            + "/"
                            + ss;

            File directory =
                    new File(directoryPath);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String filePath =
                    directoryPath
                            + "/"
                            + date
                            + ".events";

            FileWriter writer =
                    new FileWriter(
                            filePath,
                            true
                    );

            writer.write(json + "\n");

            writer.close();

            System.out.println(
                    "Evento guardado en: "
                            + filePath
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

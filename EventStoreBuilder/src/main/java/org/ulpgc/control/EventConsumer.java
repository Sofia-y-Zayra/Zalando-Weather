package org.ulpgc.control;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class EventConsumer {

    private static final String URL =
            "tcp://localhost:61616";

    public void consume(String topicName) {

        try {

            ConnectionFactory factory =
                    new ActiveMQConnectionFactory(URL);

            Connection connection =
                    factory.createConnection();

            connection.setClientID(
                    "event-store-builder"
                            + topicName
            );

            connection.start();

            Session session =
                    connection.createSession(
                            false,
                            Session.AUTO_ACKNOWLEDGE
                    );

            Topic topic =
                    session.createTopic(topicName);

            MessageConsumer consumer =
                    session.createDurableSubscriber(
                            topic,
                            "subscriber-" + topicName
                    );

            EventStorageService storageService =
                    new EventStorageService();

            consumer.setMessageListener(message -> {

                try {

                    TextMessage textMessage =
                            (TextMessage) message;

                    String json =
                            textMessage.getText();

                    System.out.println(
                            "\nEVENTO RECIBIDO:"
                    );

                    System.out.println(
                            "[EVENT RECEIVED] " + topicName
                    );

                    storageService.save(
                            topicName,
                            json
                    );

                } catch (Exception e) {

                    e.printStackTrace();
                }
            });

            System.out.println(
                    "Escuchando topic: "
                            + topicName
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}

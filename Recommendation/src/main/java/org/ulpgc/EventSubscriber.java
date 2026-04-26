package org.ulpgc;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventSubscriber {
    private static final String URL = "tcp://localhost:61616";

    public void start() {
        try {
            ConnectionFactory factory =
                    new ActiveMQConnectionFactory(URL);

            Connection connection =
                    factory.createConnection();

            connection.start();

            Session session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic productTopic =
                    session.createTopic("Product");

            Topic weatherTopic =
                    session.createTopic("Weather");

            MessageConsumer productConsumer =
                    session.createConsumer(productTopic);

            MessageConsumer weatherConsumer =
                    session.createConsumer(weatherTopic);

            productConsumer.setMessageListener(message -> {
                process(message, "Product");
            });

            weatherConsumer.setMessageListener(message -> {
                process(message, "Weather");
            });

            System.out.println("Escuchando eventos...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Message message, String topic) {
        try {
            if (message instanceof TextMessage textMessage) {

                String json = textMessage.getText();

                EventWriter.write(topic, json);

                System.out.println("Evento guardado en " + topic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

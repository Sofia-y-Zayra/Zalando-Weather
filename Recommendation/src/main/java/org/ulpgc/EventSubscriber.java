package org.ulpgc;


import jakarta.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Message;


public class EventSubscriber {
    private static final String URL = "tcp://localhost:61616";

    public void start() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(URL);
            Connection connection = factory.createConnection();


            connection.setClientID("EventStoreBuilder_Global");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


            Topic productTopic = session.createTopic("Product");
            Topic weatherTopic = session.createTopic("Weather");


            MessageConsumer productConsumer = session.createDurableSubscriber(productTopic, "Sub_Product");
            MessageConsumer weatherConsumer = session.createDurableSubscriber(weatherTopic, "Sub_Weather");


            productConsumer.setMessageListener(message -> process((Message) message, "Product"));
            weatherConsumer.setMessageListener(message -> process((Message) message, "Weather"));

            System.out.println("Escuchando eventos de Product y Weather...");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Message message, String topic) {
        try {
            if (message instanceof TextMessage textMessage) {
                String json = textMessage.getText();
                // Llamamos al escritor pasándole el topic correspondiente
                EventWriter.write(topic, json);
                System.out.println("Evento de " + topic + " guardado correctamente.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
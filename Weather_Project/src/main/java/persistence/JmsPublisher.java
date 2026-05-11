package persistence;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class JmsPublisher {
    private final String url = "tcp://localhost:61616";

    public void publish(String topicName, String jsonMessage) {
        try {

            ConnectionFactory factory = new ActiveMQConnectionFactory(url);
            Connection connection = factory.createConnection();
            connection.start();


            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);


            Destination destination = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(destination);


            TextMessage message = session.createTextMessage(jsonMessage);


            producer.send(message);
            System.out.println("Mensaje enviado al Broker: " + jsonMessage);


            connection.close();
        } catch (JMSException e) {
            System.err.println("Error enviando al Broker: " + e.getMessage());
        }
    }
}
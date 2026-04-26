package persistence;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class JmsPublisher {
    private final String url = "tcp://localhost:61616";

    public void publish(String topicName, String jsonMessage) {
        try {
            // 1. Crear conexión
            ConnectionFactory factory = new ActiveMQConnectionFactory(url);
            Connection connection = factory.createConnection();
            connection.start();

            // 2. Crear sesión
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 3. Crear el Topic (Weather)
            Destination destination = session.createTopic(topicName);
            MessageProducer producer = session.createProducer(destination);

            // 4. Crear el mensaje de texto
            TextMessage message = session.createTextMessage(jsonMessage);

            // 5. Enviar
            producer.send(message);
            System.out.println("Mensaje enviado al Broker: " + jsonMessage);

            // 6. Cerrar recursos
            connection.close();
        } catch (JMSException e) {
            System.err.println("Error enviando al Broker: " + e.getMessage());
        }
    }
}
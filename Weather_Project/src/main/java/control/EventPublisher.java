package control;


import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class EventPublisher {

    private static final String URL =
            "tcp://localhost:61616";

    public void publish(
            String topicName,
            Object eventObj
    ) {

        try {

            ConnectionFactory factory =
                    new ActiveMQConnectionFactory(URL);

            Connection connection =
                    factory.createConnection();

            connection.start();

            Session session =
                    connection.createSession(
                            false,
                            Session.AUTO_ACKNOWLEDGE
                    );

            Topic topic =
                    session.createTopic(topicName);

            MessageProducer producer =
                    session.createProducer(topic);

            Gson gson = new Gson();

            String json =
                    gson.toJson(eventObj);

            TextMessage message =
                    session.createTextMessage(json);

            producer.send(message);

            System.out.println(
                    "Evento enviado: " + json
            );

            producer.close();
            session.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package org.ulpgc.dacd.control;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

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
                    "[EVENT SENT] Topic: "
                            + topicName
            );

            producer.close();
            session.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

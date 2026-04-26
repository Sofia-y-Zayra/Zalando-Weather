package org.ulpgc.dacd.control;

import com.google.gson.Gson;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class EventPublisher {

    private static final String URL = "tcp://localhost:localhost:61616".replace("localhost:localhost","localhost");

    public void publish(String topicName, Object eventObj) throws Exception {

        ConnectionFactory factory =
                new ActiveMQConnectionFactory(URL);

        Connection connection =
                factory.createConnection();

        connection.start();

        Session session =
                connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topic =
                session.createTopic(topicName);

        MessageProducer producer =
                session.createProducer(topic);

        Gson gson = new Gson();
        String json = gson.toJson(eventObj);

        TextMessage message =
                session.createTextMessage(json);

        producer.send(message);

        producer.close();
        session.close();
        connection.close();

        System.out.println("Evento enviado");
    }
}
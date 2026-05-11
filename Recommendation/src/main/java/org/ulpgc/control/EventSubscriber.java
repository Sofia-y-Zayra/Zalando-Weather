package org.ulpgc.control;


import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.config.ActiveMQConfig;
import org.ulpgc.persistence.DatamartStore;

public class EventSubscriber {

    private final EventProcessor processor;

    public EventSubscriber(DatamartStore datamart) {

        this.processor =
                new EventProcessor(datamart);
    }

    public void start() {

        try {

            ConnectionFactory factory =
                    new ActiveMQConnectionFactory(
                            ActiveMQConfig.URL
                    );

            Connection connection =
                    factory.createConnection();

            connection.setClientID(
                    ActiveMQConfig.CLIENT_ID
            );

            connection.start();

            Session session =
                    connection.createSession(
                            false,
                            Session.AUTO_ACKNOWLEDGE
                    );

            subscribe(session);

            System.out.println(
                    "Suscriptor en tiempo real funcionando..."
            );

        } catch (Exception e) {

            reconnect();
        }
    }

    private void subscribe(Session session)
            throws JMSException {

        Topic productTopic =
                session.createTopic(
                        ActiveMQConfig.TOPIC_PRODUCT
                );

        Topic weatherTopic =
                session.createTopic(
                        ActiveMQConfig.TOPIC_WEATHER
                );

        MessageConsumer productConsumer =
                session.createDurableSubscriber(
                        productTopic,
                        ActiveMQConfig.SUB_PRODUCT
                );

        MessageConsumer weatherConsumer =
                session.createDurableSubscriber(
                        weatherTopic,
                        ActiveMQConfig.SUB_WEATHER
                );

        productConsumer.setMessageListener(
                message ->
                        processor.process(
                                message,
                                ActiveMQConfig.TOPIC_PRODUCT
                        )
        );

        weatherConsumer.setMessageListener(
                message ->
                        processor.process(
                                message,
                                ActiveMQConfig.TOPIC_WEATHER
                        )
        );
    }

    private void reconnect() {

        System.out.println(
                "Error de conexión con ActiveMQ"
        );

        System.out.println(
                "Reintentando en 5 segundos..."
        );

        try {

            Thread.sleep(
                    ActiveMQConfig.RETRY_TIME_MS
            );

            start();

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}
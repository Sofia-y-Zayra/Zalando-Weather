package org.ulpgc.control;


import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.model.DatamartStore;

public class EventSubscriber {

    private static final String URL = "tcp://localhost:61616";

    private static final String TOPIC_PRODUCT = "Product";
    private static final String TOPIC_WEATHER = "Weather";

    private static final String CLIENT_ID = "BusinessUnit_Subscriber_Unique";

    private static final String SUB_PRODUCT = "Sub_Prod_BU";
    private static final String SUB_WEATHER = "Sub_Weat_BU";

    private static final int RETRY_TIME_MS = 5000;

    private final EventProcessor processor;

    public EventSubscriber(DatamartStore datamart) {
        this.processor = new EventProcessor(datamart);
    }

    public void start() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(URL);
            Connection connection = factory.createConnection();

            connection.setClientID(CLIENT_ID);
            connection.start();

            Session session =
                    connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            subscribe(session);

            System.out.println("Suscriptor en tiempo real funcionando...");

        } catch (Exception e) {
            reconnect();
        }
    }

    private void subscribe(Session session) throws JMSException {

        Topic productTopic = session.createTopic(TOPIC_PRODUCT);
        Topic weatherTopic = session.createTopic(TOPIC_WEATHER);

        MessageConsumer productConsumer =
                session.createDurableSubscriber(productTopic, SUB_PRODUCT);

        MessageConsumer weatherConsumer =
                session.createDurableSubscriber(weatherTopic, SUB_WEATHER);

        productConsumer.setMessageListener(
                message -> processor.process(message, TOPIC_PRODUCT)
        );

        weatherConsumer.setMessageListener(
                message -> processor.process(message, TOPIC_WEATHER)
        );
    }

    private void reconnect() {
        System.out.println("Error de conexión con ActiveMQ");
        System.out.println("Reintentando en 5 segundos...");

        try {
            Thread.sleep(RETRY_TIME_MS);
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
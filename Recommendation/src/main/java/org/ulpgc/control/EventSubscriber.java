package org.ulpgc.control;


import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.control.persistence.DatamartStore;

public class EventSubscriber {

    private final EventHandler handler;

    public EventSubscriber(DatamartStore datamart) {

        this.handler =
                new EventHandler(datamart);
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

            e.printStackTrace();

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


        productConsumer.setMessageListener(message -> {

            try {

                if (message instanceof TextMessage tm) {

                    String json =
                            tm.getText();

                    handler.handle(
                            json,
                            ActiveMQConfig.TOPIC_PRODUCT
                    );

                    System.out.println(
                            "Producto procesado"
                    );
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        });


        weatherConsumer.setMessageListener(message -> {

            try {

                if (message instanceof TextMessage tm) {

                    String json =
                            tm.getText();

                    handler.handle(
                            json,
                            ActiveMQConfig.TOPIC_WEATHER
                    );

                    System.out.println(
                            "Weather procesado"
                    );
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        });
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
package org.ulpgc;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.ulpgc.persistence.DatamartStore;

public class EventSubscriber {
    private static final String URL = "tcp://localhost:61616";
    private final DatamartStore datamart;
    private final Gson gson = new Gson();


    public EventSubscriber(DatamartStore datamart) {
        this.datamart = datamart;
    }

    public void start() {
        try {
            ConnectionFactory factory = new ActiveMQConnectionFactory(URL);
            Connection connection = factory.createConnection();
            connection.setClientID("BusinessUnit_Subscriber_Unique");
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Topic productTopic = session.createTopic("Product");
            Topic weatherTopic = session.createTopic("Weather");

            MessageConsumer prodSub = session.createDurableSubscriber(productTopic, "Sub_Prod_BU");
            MessageConsumer weatSub = session.createDurableSubscriber(weatherTopic, "Sub_Weat_BU");

            prodSub.setMessageListener(m -> process((Message) m, "Product"));
            weatSub.setMessageListener(m -> process((Message) m, "Weather"));

            System.out.println("Suscriptor en tiempo real funcionando...");
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void process(Message message, String topic) {
        try {
            if (message instanceof TextMessage tm) {
                String json = tm.getText();
                EventWriter.write(topic, json); // Persistencia Sprint 2
                updateDatamart(json, topic);    // Explotación Sprint 3
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void updateDatamart(String json, String topic) {
        JsonObject obj = gson.fromJson(json, JsonObject.class);
        String ts = obj.has("ts") ? obj.get("ts").getAsString() : "unknown";

        if (topic.equals("Weather")) {
            datamart.updateWeather(obj.get("city").getAsString(), obj.get("temp").getAsDouble(),
                    obj.get("condition").getAsString(), ts);
        } else {
            datamart.upsertProduct(obj.get("id").getAsString(), obj.get("name").getAsString(),
                    obj.get("price").getAsDouble(), obj.get("category").getAsString(), ts);
        }
    }
}
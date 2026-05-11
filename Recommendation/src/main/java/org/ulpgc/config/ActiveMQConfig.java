package org.ulpgc.config;

public class ActiveMQConfig {

    public static final String URL =
            "tcp://localhost:61616";

    public static final String TOPIC_PRODUCT =
            "Product";

    public static final String TOPIC_WEATHER =
            "Weather";

    public static final String CLIENT_ID =
            "BusinessUnit_Subscriber_Unique";

    public static final String SUB_PRODUCT =
            "Sub_Prod_BU";

    public static final String SUB_WEATHER =
            "Sub_Weat_BU";

    public static final int RETRY_TIME_MS =
            5000;

    private ActiveMQConfig() {
    }
}

package org.ulpgc;

import org.ulpgc.control.EventConsumer;

public class Main {

    public static void main(String[] args) {

        EventConsumer consumer =
                new EventConsumer();

        consumer.consume("Product");

        consumer.consume("Weather");

        System.out.println(
                "EventStoreBuilder iniciado"
        );
    }
}
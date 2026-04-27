package org.ulpgc;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO EVENT STORE BUILDER ===");
        EventSubscriber subscriber = new EventSubscriber();
        subscriber.start();
    }
}
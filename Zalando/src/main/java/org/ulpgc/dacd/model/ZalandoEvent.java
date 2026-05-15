package org.ulpgc.dacd.model;

public class ZalandoEvent {

    private String eventId;
    private String ts;
    private String ss;
    private String eventType;
    private Product payload;

    public ZalandoEvent(
            String eventId,
            String ts,
            String ss,
            String eventType,
            Product payload
    ) {
        this.eventId = eventId;
        this.ts = ts;
        this.ss = ss;
        this.eventType = eventType;
        this.payload = payload;
    }

    public String getEventId() {
        return eventId;
    }

    public String getTs() {
        return ts;
    }

    public String getSs() {
        return ss;
    }

    public String getEventType() {
        return eventType;
    }

    public Product getPayload() {
        return payload;
    }
}

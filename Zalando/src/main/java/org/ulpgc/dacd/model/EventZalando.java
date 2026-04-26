package org.ulpgc.dacd.model;

public class EventZalando {

    private String ts;
    private String ss;
    private Object payload;

    public EventZalando(String ts, String ss, Object payload) {
        this.ts = ts;
        this.ss = ss;
        this.payload = payload;
    }

    public String getTs() { return ts; }
    public String getSs() { return ss; }
    public Object getPayload() { return payload; }
}

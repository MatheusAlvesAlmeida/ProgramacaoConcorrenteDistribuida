package io.confluent.examples.clients.cloud.model;

public class DataRecord {

    String datetime;

    public DataRecord() {
    }

    public DataRecord(String datetime) {
        this.datetime = datetime;
    }

    public String getDatetime() {
        return this.datetime;
    }

    public String toString() {
        return new com.google.gson.Gson().toJson(this);
    }

}

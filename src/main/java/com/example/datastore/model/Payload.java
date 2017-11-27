package com.example.datastore.model;

import java.util.concurrent.ThreadLocalRandom;

public class Payload {

    public static String randomString() {
        return Long.toHexString(Double.doubleToLongBits(ThreadLocalRandom.current().nextDouble()));
    }

    String value1;
    String value2;
    String value3;
    String value4;
    String value5;
    String value6;
    String value7;
    String value8;
    String value9;

    public Payload() {
        this.value1 = randomString();
        this.value2 = randomString();
        this.value3 = randomString();
        this.value4 = randomString();
        this.value5 = randomString();
        this.value6 = randomString();
        this.value7 = randomString();
        this.value8 = randomString();
        this.value9 = randomString();
    }

}

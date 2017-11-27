package com.example.datastore.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Entity
@Cache
public class DTO {

    @Id
    long id;
    List<Payload> payloads;

    public DTO() {
    }

    public DTO(long id) {
        this.id = id;
        this.payloads = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            payloads.add(new Payload(true));
        }
    }

    public DTO(long id, boolean generate) {
        this.id = id;
        if (!generate) return;
        this.payloads = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            payloads.add(new Payload(generate));
        }
    }

    public long getId() {
        return id;
    }

    public List<Payload> getPayload() {
        return payloads;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}

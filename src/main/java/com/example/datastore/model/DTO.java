package com.example.datastore.model;

import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@Cache
public class DTO {

    @Id long id;
    String payload;

    public DTO() {
    }

    public DTO(long id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    public long getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return String.valueOf(getId());
    }
}

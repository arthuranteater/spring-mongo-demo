package com.example.mongodemo.models;
import org.springframework.data.mongodb.core.query.Query;

public class MongoQuery {

    String status;
    User user;

    public MongoQuery(String id, User user) {
        this.user = user;
        if(user != null) {
            this.status = id + " found";
        } else {
            this.status = id + " not found";
        }
    }

    public String getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

}

package com.example.mongodemo.models;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepositoryTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoOperations mongoOperations;

    public List customFindAll() {
        return mongoTemplate.findAll(User.class);
    }

    public User customCreate(User user) {
        return mongoTemplate.save(user);
    }

    public MongoQuery customFindOne(String userId) {
        Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
        return new MongoQuery(userId, mongoTemplate.findOne(query, User.class));
    }

    public UpdateResult customUpdate(String userId, User user) {
        //create update
        Update update = new Update();
        //create query
        Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
        //find existing user
        User eUser = mongoTemplate.findOne(query, User.class);
        //get existing user settings to compare
        Map<String, String> eSettings = eUser.getUserSettings();
        //get incoming data
        String name = user.getName();
        Map<String, String> settings = user.getUserSettings();
        //check for no name
        if (name != null) {
            update.set("name", name);
        }
        //check for no settings
        if (!settings.isEmpty()) {
            //combine settings
            eSettings.putAll(settings);
            update.set("userSettings", eSettings);
        }
        return mongoTemplate.upsert(query, update, User.class);
    }

    public DeleteResult customDeleteById(String userId) {
        Query query = new Query().addCriteria(Criteria.where("userId").is(userId));
        return mongoTemplate.remove(query, User.class);
    }

}

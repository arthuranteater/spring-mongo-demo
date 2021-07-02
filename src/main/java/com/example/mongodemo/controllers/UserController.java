package com.example.mongodemo.controllers;
import com.example.mongodemo.models.MongoQuery;
import com.example.mongodemo.models.User;
import com.example.mongodemo.models.UserRepository;
import com.example.mongodemo.models.UserRepositoryTemplate;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(value = "/")
public class UserController {

    private final UserRepository userRepository;

    private final UserRepositoryTemplate userRepositoryTemplate;

    public UserController(UserRepository userRepository, UserRepositoryTemplate userRepositoryTemplate) {
        this.userRepository = userRepository;
        this.userRepositoryTemplate = userRepositoryTemplate;
    }

    //Getting All Users

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        System.out.println("logging all users");
        return userRepositoryTemplate.customFindAll();
    }

    //Getting One User

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public MongoQuery getUser(@PathVariable String userId) {
        System.out.println("searching for id " + userId);
        //MongoTemplate
        return userRepositoryTemplate.customFindOne(userId);
        //MongoRepository
//        return userRepository.findById(userId).orElse(null);
    }

    //Getting One User by Name

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public List<User> getUserByName(@PathVariable String name) {
        System.out.println("searching for name " + name);
        return userRepository.findAllByName(name);
    }

    //Adding a User

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUser(@RequestBody User user) {
        System.out.println("adding " + user);
        //MongoTemplate
        return userRepositoryTemplate.customCreate(user);
        //MongoRepository
//        return userRepository.save(user);
    }

    //Updating a User

    @RequestMapping(value = "/update/{userId}", method = RequestMethod.PUT)
    public UpdateResult updUser(@PathVariable String userId, @RequestBody User user) {
        //MongoTemplate
        return userRepositoryTemplate.customUpdate(userId, user);
        //MongoRepository
        //get existing user
//        User eUser = userRepository.findById(userId).orElse(null);
//        //check for null
//        if(eUser != null) {
//            //replace name value if not null, combine settings
//            //get incoming user data
//            String name = user.getName();
//            Map<String, String> settings = user.getUserSettings();
//            //compare incoming user data with existing user data
//            if (name != null) {
//                eUser.setName(name);
//            }
//            if (!settings.isEmpty()) {
//                //get existing user settings
//                Map<String, String> eSettings = eUser.getUserSettings();
//                //add the incoming user settings
//                eSettings.putAll(settings);
//                //set the existing to the combined settings
//                eUser.setUserSettings(eSettings);
//            }
//            System.out.println("updating user");
//            return userRepository.save(eUser);
//        }
//        System.out.println("creating new user");
//        return userRepository.save(user);
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public DeleteResult delUser(@PathVariable String userId) {
        //get existing user
//        User eUser = userRepository.findById(userId).orElse(null);
//        //check for null
//        if(eUser == null) {
//            return "couldn't find user";
//        }
//        userRepository.deleteById(userId);
//        return "deleted " + eUser;
        return userRepositoryTemplate.customDeleteById(userId);

    }



}

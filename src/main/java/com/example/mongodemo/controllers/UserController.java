package com.example.mongodemo.controllers;
import com.example.mongodemo.models.User;
import com.example.mongodemo.models.UserRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Getting All Users

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        System.out.println("logging all users");
        return userRepository.findAll();
    }

    //Getting One User

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId) {
        System.out.println("logging one users");
        return userRepository.findById(userId).orElse(null);
    }

    //Adding a User

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUser(@RequestBody User user) {
        System.out.println("adding one user");
        return userRepository.save(user);
    }

    //Updating a User

    @RequestMapping(value = "/update/{userId}", method = RequestMethod.POST)
    public User updUser(@PathVariable String userId, @RequestBody User user) {
        //get existing user
        User eUser = userRepository.findById(userId).orElse(null);
        //check for null
        if(eUser != null) {

            //replace name value if not null, combine settings
            //get incoming user data
            String name = user.getName();
            Map<String, String> settings = user.getUserSettings();
            //compare incoming user data with existing user data
            if (name != null) {
                eUser.setName(name);
            }
            if (!settings.isEmpty()) {
                //get existing user settings
                Map<String, String> eSettings = eUser.getUserSettings();
                //add the incoming user settings
                eSettings.putAll(settings);
                //set the existing to the combined settings
                eUser.setUserSettings(eSettings);
            }
            System.out.println("updating user");
            return userRepository.save(eUser);
        }
        System.out.println("creating new user");
        return userRepository.save(user);
    }

    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.GET)
    public String delUser(@PathVariable String userId) {
        //get existing user
        User eUser = userRepository.findById(userId).orElse(null);
        //check for null
        if(eUser == null) {
            return "couldn't find user";
        }
        userRepository.deleteById(userId);
        return "deleted " + eUser;

    }

}

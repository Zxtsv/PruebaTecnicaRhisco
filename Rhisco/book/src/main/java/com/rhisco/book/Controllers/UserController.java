package com.rhisco.book.Controllers;

import java.util.HashMap;
import com.rhisco.book.Models.User;
import com.rhisco.book.Services.UserService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findUser/{name}/{last_name}")
    public Long findUser(@PathVariable("name") String name, @PathVariable("last_name") String last_name) {
        try{
            return userService.findUser(name, last_name).getId();
        }
        catch(Exception e){
            return -1L;
        }
    }

    @GetMapping("getLastId")
    public Long getLastId() {
        return userService.getLastId();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/addUser")
    public HashMap<String,String> addUser(@RequestBody User user) {
        HashMap<String,String> response = new HashMap<>();
        if(userService.addUser(user)) {
            response.put("message","User already exists");
            response.put("status","fail");
            return response;
        }
        else {
            response.put("message","User added successfully");
            response.put("status","success");
            return response;
        }
    }

}

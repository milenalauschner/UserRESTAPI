package com.myapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    // 1) Simple GET just to confirm it's working
    @GetMapping("/users")
    public String getUser() {
        return "Hello GET users is working!";
    }

    // 2) POST /users
    // Receives JSON like: { "name": "Alice", "age": 30 }
    @PostMapping("/users")
    public UserDTO createUser(@RequestBody UserDTO user) {
        // Here you would normally save to a database.
        // For now, just echo the user back:
        System.out.println("Received user: " + user.getName() + ", id: " + user.getId());
        return user;
    }

    // 3) PATCH /users/{id}
    // Receives partial JSON like: { "name": "New Name" } or { "id": 40 }
    @PatchMapping("/users/{id}")
    public UserDTO updateUser(
            @PathVariable Long id,
            @RequestBody UserDTO update

            //Spring takes that JSON and converts it into a UserDTO instance and assigns it to the parameter named update.
    )
    {
        // pretend you loaded this from a DB
        UserDTO userFromDb = new UserDTO();
        userFromDb.setId(id);
        userFromDb.setName("Alice"); // sample data "from" database

        // apply patch only for fields that came in
        if (update.getName() != null) {
            userFromDb.setName(update.getName());
        }
        if (update.getId() != null && !update.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Body id must match path id");
        }

        return userFromDb;
    }
}
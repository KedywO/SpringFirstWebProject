package com.hhproject.web.controller;


import com.hhproject.web.model.User;
import com.hhproject.web.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class UserController {

    final private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUser(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser());
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping (path = "{id}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable(name = "id") long idUP){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(idUP));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable(name = "id") long id) {
        userService.update(id,user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable("id") long id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }
}

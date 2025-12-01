package com.example.todo_app.controller;

import com.example.todo_app.dto.UserDTO;
import com.example.todo_app.entity.Users;
import com.example.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("get-user-by-email")
    public UserDTO getUserByEmail(@RequestParam(required = false) String email) {
        System.out.println("in get user by email");
        return userService.findUserByEmail(email);
    }

    @PostMapping("create-user")
    public ResponseEntity<?> createUser(@RequestBody Users user) {
        System.out.println("in create-user");
        System.out.println(user.toString());
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception ex) {
            System.out.println("duplicate user found");
            Map<String, String> result= new HashMap<>();
            result.put("error", ex.getMessage());
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }
}

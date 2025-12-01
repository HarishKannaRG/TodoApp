package com.example.todo_app.service;

import com.example.todo_app.constants.Constants;
import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.dto.UserDTO;
import com.example.todo_app.entity.Users;
import com.example.todo_app.exception.DuplicateUserException;
import com.example.todo_app.exception.UserNotFoundException;
import com.example.todo_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    //@Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO findUserByEmail(String email) {
        System.out.println("find user by email called");
        if(userRepository.findByEmail(email).isEmpty()) {
            System.out.println("user not found");
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        System.out.println("user found");
        return buildUserDTO(userRepository.findByEmail(email).get());
    }

    public void saveUser(Users user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateUserException("User already exists with the given email");
        }
        System.out.println("password:"+user.getPassword());
//        String encodedPassword = "{bcrypt}"+new BCryptPasswordEncoder().encode(user.getPassword());
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public UserDTO buildUserDTO(Users user) {
//        System.out.println("build userdte:"+user.toString());
        List<TodoDTO> todoDTOS = new ArrayList<>();
        if(!user.getTodos().isEmpty()) {
            todoDTOS = user.getTodos().stream()
                    .map(todo -> TodoDTO.builder()
                            .id(todo.getId())
                            .title(todo.getTitle())
                            .description(todo.getDescription())
                            .createdDate(todo.getCreatedDate())
                            .dueDate(todo.getDueDate())
                            .status(todo.getStatus())
                            .userId(todo.getUser().getId())
                            .build())
                    .toList();
        }
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .todos(todoDTOS)
                .name(user.getName())
                .build();
    }
}

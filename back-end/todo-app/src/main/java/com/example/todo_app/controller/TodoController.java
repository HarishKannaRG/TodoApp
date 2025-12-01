package com.example.todo_app.controller;

import com.example.todo_app.constants.Constants;
import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.dto.UserDTO;
import com.example.todo_app.entity.ToDo;
import com.example.todo_app.entity.Users;
import com.example.todo_app.exception.UserNotFoundException;
import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.service.TodoService;
import com.example.todo_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @Autowired
    private TodoRepository todoRepository;

//    @GetMapping("/get-todos-of-user")
//    public List<ToDo> getTodosOfUser(@RequestParam Long userId) {
//        return todoService.getTodosOfUser(userId);
//    }

    @PostMapping("/add-todo")
    public ResponseEntity<TodoDTO> addTodo(@RequestBody TodoDTO toDo, Authentication authentication) {
        if(authentication == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        UserDTO user = userService.findUserByEmail(authentication.getName());
        toDo.setUserId(user.getId());
        ToDo todorec = todoService.addTodo(toDo);
        TodoDTO todoDTO = TodoDTO.todoDTOBuilder(todorec);
        return new ResponseEntity<>(todoDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update-todo")
    public ResponseEntity<TodoDTO> updateTodo(@RequestBody TodoDTO todoDTO, Authentication authentication) {
        if(authentication == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        UserDTO user = userService.findUserByEmail(authentication.getName());
        todoDTO.setUserId(user.getId());
        TodoDTO updatedTodo = todoService.updateTodo(todoDTO);
        if (updatedTodo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
    }

    @GetMapping("/get-todos")
    public ResponseEntity<List<ToDo>> getTodos(Authentication authentication) {
        List<TodoDTO> todos = new ArrayList<>();
        System.out.println("username:"+authentication.getName());
//        return todoService.getTodos(authentication.getName());
        return ResponseEntity.ok(todoService.getTodos(authentication.getName()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable Long id, Authentication authentication) {
        System.out.println("delete todo called");
        if(authentication == null) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        try {
            String userName = authentication.getName();
            todoService.deleteTodo(id, userName);
            System.out.println("delete success");
            return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            // Respond with appropriate HTTP status
            String msg = e.getMessage();
            if ("User not authenticated".equals(msg)) {
                return new ResponseEntity<>(msg, HttpStatus.UNAUTHORIZED);
            }
            if ("Todo not found".equals(msg)) {
                return new ResponseEntity<>(msg, HttpStatus.NOT_FOUND);
            }
            if ("Not authorized to delete this todo".equals(msg)) {
                return new ResponseEntity<>(msg, HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>("Error: " + msg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

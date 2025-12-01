package com.example.todo_app.service;

import com.example.todo_app.constants.Constants;
import com.example.todo_app.dto.TodoDTO;
import com.example.todo_app.entity.ToDo;
import com.example.todo_app.entity.Users;
import com.example.todo_app.exception.TodoNotFoundException;
import com.example.todo_app.exception.UserNotFoundException;
import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private UserRepository userRepository;
//    public List<ToDo> getTodosOfUser(Long ownerId) {
//        List<ToDo> toDos =todoRepository.findByUserId(ownerId);
//        return toDos;
//    }

    public ToDo addTodo(TodoDTO todo) {
        Users user = userRepository.findById(todo.getUserId()).get();
        if (todo.getCreatedDate() == null) {
            todo.setCreatedDate(LocalDate.now());
        }
        System.out.println("todo to save:"+todo);
        ToDo toDoRec = ToDo.builder()
                        .user(user)
                        .title(todo.getTitle())
                        .description(todo.getDescription())
                        .dueDate(todo.getDueDate())
                        .createdDate(todo.getCreatedDate())
                        .status(todo.getStatus())
                        .category(todo.getCategory())
                        .createdDate(LocalDate.now())
                        .build();
        toDoRec.setUser(user);
        todoRepository.save(toDoRec);
        return toDoRec;
    }

    public TodoDTO updateTodo(TodoDTO todoDTO) {
        System.out.println("in update todo");
        Users user = userRepository.findById(todoDTO.getUserId()).get();
        ToDo todo = todoRepository.findById(todoDTO.getId()).orElseThrow(() -> new TodoNotFoundException("The Todo you are trying to update does not exist"));
        System.out.println("got todo:"+todo);
        todo = ToDo.builder()
                .id(todo.getId())
                .title(todoDTO.getTitle())
                .description(todoDTO.getDescription())
                .dueDate(todoDTO.getDueDate())
                .category(todoDTO.getCategory())
                .user(user)
                .status(todoDTO.getStatus())
                .build();
        System.out.println("todo to update:"+todo);
        todoRepository.save(todo);
        return TodoDTO.todoDTOBuilder(todo);
    }

    public List<ToDo> getTodos(String email) {
        System.out.println("get todos called");
        Optional<Users> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()) {
            throw new UserNotFoundException(Constants.USER_NOT_FOUND);
        }
        Long userId = userOptional.get().getId();
        return todoRepository.findByUserId(userId);
    }

    public void deleteTodo(Long todoId, String currentUsername) {
        // Get authenticated user
        Users currentUser = userRepository.findByEmail(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        // Look up the todo
        ToDo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        // Ownership check
        if (!todo.getUser().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Not authorized to delete this todo");
        }
        todoRepository.deleteById(todoId);
    }

}

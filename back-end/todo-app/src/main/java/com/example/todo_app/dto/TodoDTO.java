package com.example.todo_app.dto;
import com.example.todo_app.entity.ToDo;
import com.example.todo_app.repository.TodoRepository;
import com.example.todo_app.repository.UserRepository;
import com.example.todo_app.service.TodoService;
import com.example.todo_app.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TodoDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private String status;
    private Long userId;
    private String category;

    @Autowired
    @JsonIgnore
    private TodoService todoService;
    @Autowired
    @JsonIgnore
    private UserService userService;
    @Autowired
    @JsonIgnore
    private TodoRepository todoRepository;
    @Autowired
    @JsonIgnore
    private UserRepository userRepository;

    public static TodoDTO todoDTOBuilder(ToDo toDo) {
        return TodoDTO.builder()
                .id(toDo.getId())
                .title(toDo.getTitle())
                .description(toDo.getDescription())
                .createdDate(toDo.getCreatedDate())
                .dueDate(toDo.getDueDate())
                .status(toDo.getStatus())
                .userId(toDo.getUser().getId())
                .category(toDo.getCategory())
                .build();
    }
}
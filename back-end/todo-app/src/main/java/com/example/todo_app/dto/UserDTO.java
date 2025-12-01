package com.example.todo_app.dto;

import com.example.todo_app.entity.ToDo;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
    List<TodoDTO> todos;
}

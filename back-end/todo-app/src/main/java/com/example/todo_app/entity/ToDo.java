package com.example.todo_app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@Data
@Table(name = "todo")
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate createdDate;
    private LocalDate dueDate;
    private String status;
    private String category;
    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    @JsonIgnore
    @ToString.Exclude
    private Users user;
}
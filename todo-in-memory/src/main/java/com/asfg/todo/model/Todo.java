package com.asfg.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Todo {

    private Long id;
    @NonNull
    private String title;
    private String description;
    private boolean completed;



}

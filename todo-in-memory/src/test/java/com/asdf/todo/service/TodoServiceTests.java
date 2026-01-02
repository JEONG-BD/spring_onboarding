package com.asdf.todo.service;


import com.asdf.todo.model.Todo;
import com.asdf.todo.repository.TodoInMemoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TodoServiceTests {

    @Autowired
    private TodoService todoService;

    @BeforeEach
    void setUp(){
        todoService = new TodoService(new TodoInMemoryRepository());
        todoService.save(new Todo(null, "Test Todo 1" , "description1", false));
        todoService.save(new Todo(null, "Test Todo 2" , "description2", true));
    }

    @Test
    void testFindAll(){
        List<Todo> todos = todoService.findAll();
        Assertions.assertThat(todos).hasSize(2);
    }

    @Test
    void testSaveTodo(){
        Todo todo = new Todo(null, "new Todo", "new Description", false);
        todoService.save(todo);
        Assertions.assertThat(todoService.findAll()).hasSize(3);
    }

    @Test
    void testUpdateTodo(){
        Todo updatedTodo = new Todo(1L, "Updated Todo", "Updated Description", true);
        todoService.update(1L, updatedTodo);
        Todo todo = todoService.findById(1L);
        Assertions.assertThat(todo.getTitle()).isEqualTo("Updated Todo");
        Assertions.assertThat(todo.getDescription()).isEqualTo("Updated Description");
        Assertions.assertThat(todo.isCompleted()).isTrue();
    }

    @Test
    void testDeleteTodo(){
        todoService.delete(1L);
        Assertions.assertThat(todoService.findAll()).hasSize(1);
        Assertions.assertThat(todoService.findById(1L)).isNull();
    }

}

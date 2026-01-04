package com.asdf.todo.service;


import com.asdf.todo.dto.TodoRequestDto;
import com.asdf.todo.dto.TodoResponseDto;
import com.asdf.todo.repository.TodoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@Testcontainers
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TodoServiceTests {

    @Container
    public static MySQLContainer<?> mysqlContainer =
            new MySQLContainer<>("mysql:8.0.32")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
    }

    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoRepository todoRepository;

    private Long todo1Id;
    private Long todo2Id;

    @BeforeEach
    void setUp(){
        todoRepository.deleteAll();
        todoService = new TodoService(todoRepository);
        todo1Id = todoService.save(new TodoRequestDto("Test Todo 1", "description1", false)).getId();
        todo2Id = todoService.save(new TodoRequestDto("Test Todo 2" , "description2", true)).getId();
    }

    @Test
    void testFindAll(){
        List<TodoResponseDto> todos = todoService.findAll();
        Assertions.assertThat(todos).hasSize(2);
    }

    @Test
    void testSaveTodo(){
        TodoRequestDto todo = new TodoRequestDto("new Todo", "new Description", false);
        todoService.save(todo);
        Assertions.assertThat(todoService.findAll()).hasSize(3);
    }

    @Test
    void testUpdateTodo(){
        TodoRequestDto updatedTodo = new TodoRequestDto("Updated Todo", "Updated Description", true);
        todoService.update(todo1Id, updatedTodo);
        TodoResponseDto todo = todoService.findById(todo1Id);
        Assertions.assertThat(todo.getTitle()).isEqualTo("Updated Todo");
        Assertions.assertThat(todo.getDescription()).isEqualTo("Updated Description");
        Assertions.assertThat(todo.isCompleted()).isTrue();
    }

    @Test
    void testDeleteTodo(){
        todoService.delete(todo1Id);
        Assertions.assertThat(todoService.findAll()).hasSize(1);
        Assertions.assertThat(todoService.findById(todo1Id)).isNull();
    }

}

package com.asfg.todo.service;

import com.asfg.todo.model.Todo;
import com.asfg.todo.repository.TodoInMemoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private final TodoInMemoryRepository todoRepository;

    public TodoService(TodoInMemoryRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> findAll(){
        return todoRepository.findAll();
    }

    public Todo findById(Long id){
        return todoRepository.findById(id);
    }

    public Todo save(Todo todo){
        return todoRepository.save(todo);
    }

    public Todo update(Long id,Todo todo){
        todo.setId(id);
        return todoRepository.save(todo);
    }

    public void delete(Long id){
        todoRepository.deleteById(id);
    }
}

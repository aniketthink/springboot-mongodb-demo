package com.aniketkolte.springbootmongodb.controller;

import com.aniketkolte.springbootmongodb.model.TodoDTO;
import com.aniketkolte.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepo;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todo = todoRepo.findAll();
        if (todo.size() > 0) return new ResponseEntity<List<TodoDTO>>(todo, HttpStatus.OK);
        return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todo) {
        try {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
            return new ResponseEntity<TodoDTO>(todo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if (todoOptional.isPresent()) return new ResponseEntity<TodoDTO>(todoOptional.get(), HttpStatus.OK);
        return new ResponseEntity<>("Todo not found with id:" + id, HttpStatus.NOT_FOUND);
    }

    @PutMapping("/todos/{id}")
    public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody TodoDTO todo) {
        Optional<TodoDTO> todoOptional = todoRepo.findById(id);
        if (todoOptional.isPresent()) {
            TodoDTO todoToSave = todoOptional.get();
            todoToSave.setCompleted(todo.isCompleted());
            todoToSave.setTodo(todo.getTodo() != null ? todo.getTodo() : todoToSave.getTodo());
            todoToSave.setDescription(todo.getDescription() != null ? todo.getDescription() : todoToSave.getDescription());
            todoToSave.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToSave);
            return new ResponseEntity<TodoDTO>(todoToSave, HttpStatus.OK);
        }
        return new ResponseEntity<>("Todo not found with id:" + id, HttpStatus.NOT_FOUND);
    }

}

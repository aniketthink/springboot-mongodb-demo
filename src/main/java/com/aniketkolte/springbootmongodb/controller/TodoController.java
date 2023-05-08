package com.aniketkolte.springbootmongodb.controller;

import com.aniketkolte.springbootmongodb.model.TodoDTO;
import com.aniketkolte.springbootmongodb.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {

    @Autowired
    private TodoRepository todoRepo;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todo = todoRepo.findAll();
        if (todo.size() > 0)
            return new ResponseEntity<List<TodoDTO>>(todo, HttpStatus.OK);
        return new ResponseEntity<>("No todos available", HttpStatus.NOT_FOUND);
    }
}

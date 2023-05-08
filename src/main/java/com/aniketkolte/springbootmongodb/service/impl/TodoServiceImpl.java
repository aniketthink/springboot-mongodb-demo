package com.aniketkolte.springbootmongodb.service.impl;

import com.aniketkolte.springbootmongodb.exception.TodoCollectionException;
import com.aniketkolte.springbootmongodb.model.TodoDTO;
import com.aniketkolte.springbootmongodb.repository.TodoRepository;
import com.aniketkolte.springbootmongodb.service.TodoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepo;

    @Override
    public void createTodo(TodoDTO todo) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoOptional = todoRepo.findByTodo(todo.getTodo());
        if (todoOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        } else {
            todo.setCreatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todo);
        }
    }

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos = todoRepo.findAll();
        if (todos.size() > 0) return todos;
        return new ArrayList<TodoDTO>();
    }

    @Override
    public TodoDTO getSingleTodo(String id) throws TodoCollectionException {
        Optional<TodoDTO> optionalTodo = todoRepo.findById(id);
        if (!optionalTodo.isPresent()) throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        else return optionalTodo.get();
    }

    @Override
    public void updateTodo(String id, TodoDTO todo) throws TodoCollectionException {
        Optional<TodoDTO> todoWithId = todoRepo.findById(id);
        Optional<TodoDTO> todoWithSameName = todoRepo.findByTodo(todo.getTodo());

        if (todoWithId.isPresent()) {
            if (todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
                throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
            }
            TodoDTO todoToUpdate = todoWithId.get();
            todoToUpdate.setTodo(todo.getTodo());
            todoToUpdate.setDescription(todo.getDescription());
            todoToUpdate.setCompleted(todo.isCompleted());
            todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
            todoRepo.save(todoToUpdate);
        } else {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        }
    }
}

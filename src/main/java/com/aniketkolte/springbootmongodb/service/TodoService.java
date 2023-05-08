package com.aniketkolte.springbootmongodb.service;

import com.aniketkolte.springbootmongodb.exception.TodoCollectionException;
import com.aniketkolte.springbootmongodb.model.TodoDTO;
import jakarta.validation.ConstraintViolationException;

public interface TodoService {

    public void createTodo(TodoDTO todo) throws TodoCollectionException, ConstraintViolationException;
}

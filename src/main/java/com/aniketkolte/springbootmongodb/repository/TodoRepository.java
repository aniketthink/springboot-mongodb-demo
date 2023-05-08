package com.aniketkolte.springbootmongodb.repository;

import com.aniketkolte.springbootmongodb.model.TodoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoDTO,String> {
}

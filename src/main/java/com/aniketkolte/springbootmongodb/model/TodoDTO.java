package com.aniketkolte.springbootmongodb.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "todos")
public class TodoDTO {

    @Id
    private String id;

    private  String todo;

    private String description;

    private boolean completed;

    private Date createdAt;

    private Date updatedAt;
}

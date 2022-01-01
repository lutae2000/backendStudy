package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public String testService(){
        TodoEntity entity = TodoEntity.builder().title("my first todo item").build();
        todoRepository.save(entity);

        TodoEntity savedEntity = todoRepository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }

    public List<TodoEntity> create(final TodoEntity entity){
        //Validation
        validate(entity);

        todoRepository.save(entity);
        log.info("Entity ID: {} is saved", entity.getId());
        return todoRepository.findByUserId(entity.getUserId());
    }

    public void validate(final TodoEntity entity){
        if(entity == null){
            log.warn("Entity can't not be null");
            throw new RuntimeException("Entity can't not be null");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<TodoEntity> delete(final TodoEntity entity){
        //저장할게 유효한지 확인
        validate(entity);

        try{
            //엔티티 삭제
            todoRepository.delete(entity);
        } catch (Exception e){
            //예외 발생시 ID, exception 로깅
            log.error("error deleting entity", entity.getId());
            //컨트롤러로 Exception을 보냄
            throw new RuntimeException("error deleting error");
        }

        return retrieve(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userID){
        return todoRepository.findByUserId(userID);
    }

    public List<TodoEntity> update(final TodoEntity entity){
        // 업데이트 하려는 정보 찾기
        validate(entity);

        //JDK8 이상부터 생긴 Optional로 null 체크
        final Optional<TodoEntity> originalData = todoRepository.findById(entity.getId());

        //가져온 TodoList를 업데이트 한다(람다 형식으로 구현)
        originalData.ifPresent(todoList -> {
            todoList.setTitle(entity.getTitle());
            todoList.setDone(entity.isDone());
            todoRepository.save(todoList);
        });

        return retrieve(entity.getUserId());
    }
}
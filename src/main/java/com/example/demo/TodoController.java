package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        String str = todoService.testService();
        List<String> list = new ArrayList<>();
        list.add("str");
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
            String tempUserId = "temp-user";
            List<TodoEntity> entities = todoService.retrieve(tempUserId);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO){
        try{
            String tempUserID = "temp-user";
            TodoEntity entity = TodoDTO.toEntity(todoDTO);
            entity.setId(null);
            entity.setUserId(tempUserID);
            List<TodoEntity> entities = todoService.create(entity);

            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String err = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(err).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO todoDTO){
        String tempUserID = "temp-user";

        //TodoEntity로 변환
        TodoEntity entity = TodoDTO.toEntity(todoDTO);

        entity.setUserId(tempUserID);

        //entity 객체로 조건절 실행하여 업데이트
        List<TodoEntity> todoEntities = todoService.update(entity);

        //자바 스트림으로 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
        List<TodoDTO> dtos = todoEntities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        //ResponseDTO 리턴
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody TodoDTO todoDTO){
        try{
            String tempUserID = "temp-user";

            //TodoEntity로 변환
            TodoEntity entity = TodoDTO.toEntity(todoDTO);
            entity.setUserId(tempUserID);

            //entity 객체로 조건절 실행하여 삭제
            List<TodoEntity> entities = todoService.delete(entity);

            //자바 스트림으로 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //변환된 TodoDTO 리스트를 이용해 ResponseDTO 초기화
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //ResponseDTO 리턴
            return ResponseEntity.ok().body(response);
        } catch (Exception e){
            String err = e.getMessage();

            ResponseDTO<TodoDTO> response  = ResponseDTO.<TodoDTO>builder().error(err).build();

            return ResponseEntity.badRequest().body(response);
        }
    }


}

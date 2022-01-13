package com.example.demo.dto;

import com.example.demo.model.TodoEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoDTO {
    private String id;
    private String title;
    private boolean done;

    public TodoDTO(final TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.done = todoEntity.isDone();
    }

    public static TodoEntity toEntity(final TodoDTO todoEntity) {
        return TodoEntity.builder()
                .id(todoEntity.getId())
                .title(todoEntity.getTitle())
                .done(todoEntity.isDone())
                .build();
    }
}

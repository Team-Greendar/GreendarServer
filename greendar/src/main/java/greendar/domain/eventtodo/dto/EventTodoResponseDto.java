package greendar.domain.eventtodo.dto;

import greendar.domain.eventtodo.model.EventTodo;
import greendar.domain.eventtodoitem.model.EventTodoItem;
import java.time.LocalDate;
import lombok.Getter;


@Getter
public class  EventTodoResponseDto{
    final private Long eventTodoItemId ;
    final private String task;
    final private LocalDate date;
    final private String imageUrl;
    final private Boolean complete;

    public  EventTodoResponseDto(EventTodo eventTodo) {
        this.task = eventTodo.getEventTodoItem().getTask();
        this.date = eventTodo.getEventTodoItem().getDate();
        this.eventTodoItemId =eventTodo.getEventTodoItem().getId();
        this.imageUrl = eventTodo.getTodoImage().getTodoImageUrl();
        this.complete = eventTodo.getComplete();
    }

    public  EventTodoResponseDto(EventTodoItem eventTodoItem) {
        this.eventTodoItemId =eventTodoItem.getId();
        this.task = eventTodoItem.getTask();
        this.date = eventTodoItem.getDate();
        this.imageUrl = "EMPTY";
        this.complete = false;
    }

}
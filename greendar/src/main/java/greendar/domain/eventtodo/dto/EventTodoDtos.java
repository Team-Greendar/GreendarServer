package greendar.domain.eventtodo.dto;

import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodoitem.domain.EventTodoItem;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventTodoDtos {
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class  EventTodoResponse{
        private Long eventTodoItemId ;
        private String task;
        private LocalDate date;
        private String imageUrl;
        private Boolean complete;
        public  EventTodoResponse(EventTodo eventTodo) {
            this.task = eventTodo.getEventTodoItem().getTask();
            this.date = eventTodo.getEventTodoItem().getDate();
            this.eventTodoItemId =eventTodo.getEventTodoItem().getId();
            this.imageUrl = eventTodo.getImageUrl();
            this.complete = eventTodo.getComplete();
        }
        public  EventTodoResponse(EventTodoItem eventTodoItem) {
            this.eventTodoItemId =eventTodoItem.getId();
            this.task = eventTodoItem.getTask();
            this.date = eventTodoItem.getDate();
            this.imageUrl = "EMPTY";
            this.complete = false;
        }

    }
}

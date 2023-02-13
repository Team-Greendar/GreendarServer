package greendar.domain.eventtodo.dto;

import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodoitem.domain.EventTodoItem;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventTodoDtos {
    @Data
    @NoArgsConstructor
    public static class EventTodoImageUpdateRequestDto
    {
        private String imageUrl;
        private Long eventTodoItemId;
    }
    @Data
    @NoArgsConstructor
    public static class EventTodoCompleteUpdateRequestDto
    {
        private Boolean complete;
        private Long eventTodoItemId;
    }
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class  EventTodoResponseDto{
        private Long eventTodoItemId ;
        private String task;
        private LocalDate date;
        private String imageUrl;
        private Boolean complete;
        public  EventTodoResponseDto(EventTodo eventTodo) {
            this.task = eventTodo.getEventTodoItem().getTask();
            this.date = eventTodo.getEventTodoItem().getDate();
            this.eventTodoItemId =eventTodo.getEventTodoItem().getId();
            this.imageUrl = eventTodo.getImageUrl();
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
}

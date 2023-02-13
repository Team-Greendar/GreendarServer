package greendar.domain.eventtodo.dto;

import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventTodoDtos {
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class  EventTodoResponse{
        private Long event_todo_id ;
        private String task;
        private LocalDate date;
        private String imageUrl;
        private Boolean complete;

        public  EventTodoResponse(Long id , String task, LocalDate date, String imageUrl, Boolean complete) {
            this.event_todo_id =id;
            this.task = task;
            this.date = date;
            this.imageUrl = imageUrl;
            this.complete = complete;
        }
    }
}

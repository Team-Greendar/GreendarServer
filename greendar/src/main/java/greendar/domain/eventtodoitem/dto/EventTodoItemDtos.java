package greendar.domain.eventtodoitem.dto;

import greendar.domain.eventtodoitem.model.EventTodoItem;
import java.time.LocalDate;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventTodoItemDtos {

    @Data
    @NoArgsConstructor
    public static class EventTodoPostItemRequestDto
    {   @NotEmpty
        String task;
        @NotNull
        LocalDate date;
    }
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class EventTodoItemResponseDto{

        private Long event_todo_item_id ;
        private String task;
        private LocalDate date;

        public EventTodoItemResponseDto(EventTodoItem eventTodoItem){
            this(eventTodoItem.getId(),eventTodoItem.getTask(),eventTodoItem.getDate());
        }
        public EventTodoItemResponseDto(Long id , String task, LocalDate date) {
            this.event_todo_item_id =id;
            this.task = task;
            this.date = date;
        }
    }
}

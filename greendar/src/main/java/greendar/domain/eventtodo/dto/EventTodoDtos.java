package greendar.domain.eventtodo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

public class EventTodoDtos {
    @Data
    @NoArgsConstructor
    public static class EventTodoCompleteUpdateRequestDto
    {
        private Boolean complete;
        private Long eventTodoItemId;
    }
}

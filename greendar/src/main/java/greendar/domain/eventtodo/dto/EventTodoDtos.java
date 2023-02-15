package greendar.domain.eventtodo.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EventTodoDtos {
    @Data
    @NoArgsConstructor
    public static class EventTodoCompleteUpdateRequestDto
    {   @NotNull
        private Long eventTodoItemId;
        @NotNull
        private Boolean complete;
    }
}

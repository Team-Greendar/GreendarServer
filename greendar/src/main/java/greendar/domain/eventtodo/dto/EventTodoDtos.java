package greendar.domain.eventtodo.dto;

import java.time.LocalDate;
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

    @Data
    public static class MonthlyAchievementRatio {
        private LocalDate date;
        private double ratio;

        public MonthlyAchievementRatio(LocalDate date, double ratio) {
            this.date = date;
            this.ratio = ratio * 100;
        }
    }
}

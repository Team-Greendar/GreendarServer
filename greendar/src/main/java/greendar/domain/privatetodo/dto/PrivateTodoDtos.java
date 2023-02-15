package greendar.domain.privatetodo.dto;

import greendar.domain.eventtodo.dto.EventTodoResponseDto;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.io.File;
import java.time.LocalDate;
import java.util.Map.Entry;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PrivateTodoDtos {
    @Data
    @NoArgsConstructor
    public static class PrivateTodoPostRequestDto
    {   @NotBlank
        private String task;
        @NotNull
        private LocalDate date;
    }
    @Data
    @NoArgsConstructor
    public static class PrivateTodoImagePutRequestDto
    {   @NotNull
        private Long private_todo_id;
        @NotNull
        File file;
    }
    @Data
    @NoArgsConstructor
    public static class PrivateTodoCompletePutRequestDto
    {   @NotNull
        private Long private_todo_id ;
        @NotNull
        private Boolean complete;
    }
    @Data
    @NoArgsConstructor
    public static class PrivateTodoTaskPutRequestDto
    {   @NotNull
        private Long private_todo_id ;
        @NotBlank
        private String task;
    }
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PrivateTodoResponse{
        private Long private_todo_id ;
        private String task;
        private LocalDate date;
        private String imageUrl;
        private Boolean complete;
        private String name;
        public PrivateTodoResponse(PrivateTodo privateTodo){
            this(privateTodo.getId(),privateTodo.getTask(),privateTodo.getDate(),privateTodo.getImageUrl(),privateTodo.getComplete(),privateTodo.getMember().getName());
        }
        public PrivateTodoResponse(Long id , String task, LocalDate date, String imageUrl, Boolean complete,String name) {
            this.name = name;
            this.private_todo_id =id;
            this.task = task;
            this.date = date;
            this.imageUrl = imageUrl;
            this.complete = complete;
        }
    }

    @Data
    public static class DailyAchievement{
        private LocalDate date;
        private int done;
        public DailyAchievement(PrivateTodo privateTodo){
            this.date = privateTodo.getDate();
            if(privateTodo.getComplete())  this.done = 1;
            if(!privateTodo.getComplete()) this.done = 0 ;
        }
        public DailyAchievement(EventTodoResponseDto eventTodoResponseDto) {
            this.date=eventTodoResponseDto.getDate();
            if(eventTodoResponseDto.getComplete())  this.done = 1;
            if(!eventTodoResponseDto.getComplete()) this.done = 0 ;
        }
    }

    @Data
    public static class DailyAchievementRatio{
        private LocalDate date;
        private float ratio;
        public DailyAchievementRatio(Entry<LocalDate,Float> result){
            this.date = result.getKey();
            this.ratio = result.getValue()*100;
        }
    }

}

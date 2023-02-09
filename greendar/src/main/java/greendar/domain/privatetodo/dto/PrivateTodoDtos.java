package greendar.domain.privatetodo.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.io.File;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PrivateTodoDtos {
    @Data
    @NoArgsConstructor
    public static class PrivateTodoPostRequestDto
    {
        String task;
        LocalDate date;

    }
    @Data
    @NoArgsConstructor
    public static class PrivateTodoImagePutRequestDto
    {
        File file;
    }
    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class PrivateTodoResponse{
        private Long private_todo_id ;
        private String task;
        private LocalDate date;
        private String imageUrl;
        private Boolean complete;
        private String memberName;
        public PrivateTodoResponse(PrivateTodo privateTodo){
            this(privateTodo.getId(),privateTodo.getTask(),privateTodo.getDate(),privateTodo.getImageUrl(),privateTodo.getComplete(),privateTodo.getMember().getName());
        }
        public PrivateTodoResponse(Long id , String task, LocalDate date, String imageUrl, Boolean complete,String memberName) {
            this.memberName = memberName;
            this.private_todo_id =id;
            this.task = task;
            this.date = date;
            this.imageUrl = imageUrl;
            this.complete = complete;
        }
    }

}

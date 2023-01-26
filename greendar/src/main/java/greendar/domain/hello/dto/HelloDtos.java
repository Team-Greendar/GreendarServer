package greendar.domain.hello.dto;

import greendar.domain.hello.domain.Hello;
import lombok.AllArgsConstructor;
import lombok.Data;

public class HelloDtos {
    @Data
    @AllArgsConstructor
    public static class HelloResultResponse<T> {
        private String message;
        private int status;
        private T data;
    }
    @Data
    public static class HelloDto {
        private long id;
        private String name;
        public HelloDto(Hello hello){
            this.id = hello.getId();
            this.name = hello.getName();
        }
    }
    @Data
    @AllArgsConstructor
    public static class HelloPostRequest{
        private String name;
    }
}

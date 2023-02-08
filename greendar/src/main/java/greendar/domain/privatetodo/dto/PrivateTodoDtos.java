package greendar.domain.privatetodo.dto;

import java.io.File;
import java.time.LocalDate;
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
}

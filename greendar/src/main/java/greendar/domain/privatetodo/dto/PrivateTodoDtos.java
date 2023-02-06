package greendar.domain.privatetodo.dto;

import java.rmi.registry.LocateRegistry;
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
}

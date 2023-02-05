package greendar.domain.privatetodo.dao;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DailyAchievementRateDao {

    private LocalDate date;
    int rate;
}

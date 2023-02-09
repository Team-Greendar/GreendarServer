package greendar.domain.privatetodo.dao;

import java.time.LocalDate;
import lombok.Data;


public class DailyAchievementRateDao {

    @Data
    public static class DailyAchievement{
        private LocalDate date;
        private int done;
        public DailyAchievement(LocalDate date,Boolean complete){
            this.date = date;
            if(complete)  this.done = 1;
            if(!complete) this.done = 0 ;
        }
    }

    @Data
    public static class DailyAchievementRatio{
        private LocalDate date;
        private float ratio;

        public DailyAchievementRatio(LocalDate date,float ratio){
            this.date = date;
            this.ratio = ratio;
        }
    }

}

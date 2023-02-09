package greendar.domain.privatetodo.dao;

import greendar.domain.privatetodo.domain.PrivateTodo;
import java.time.LocalDate;
import java.util.Map.Entry;
import java.util.TreeMap;
import lombok.Data;


public class DailyAchievementRateDao {

    @Data
    public static class DailyAchievement{
        private LocalDate date;
        private int done;
        public DailyAchievement(PrivateTodo privateTodo){
            this.date = privateTodo.getDate();
            if(privateTodo.getComplete())  this.done = 1;
            if(!privateTodo.getComplete()) this.done = 0 ;
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
        public DailyAchievementRatio(Entry<LocalDate,Float> result){
            this.date = result.getKey();
            this.ratio = result.getValue()*100;
        }
    }

}

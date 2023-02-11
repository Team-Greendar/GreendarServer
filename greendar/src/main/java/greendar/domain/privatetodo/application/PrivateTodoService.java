package greendar.domain.privatetodo.application;

import greendar.domain.member.domain.Member;

import greendar.domain.privatetodo.dao.DailyAchievementRateDao.DailyAchievement;
import greendar.domain.privatetodo.dao.DailyAchievementRateDao.DailyAchievementRatio;
import greendar.domain.privatetodo.dao.PrivateTodoRepository;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class PrivateTodoService {

    private final PrivateTodoRepository privateTodoRepository;
    @Transactional
    public PrivateTodo saveTodo(Member member , String task, LocalDate date) {
        return privateTodoRepository.saveTodo(member,task,date);
    }
    public List<PrivateTodo> getAllPrivateTodoByMember(Member member) {
        return privateTodoRepository.findAllPrivateTodoByMember(member);
    }
    public List<PrivateTodo> getAllPrivateTodoByOneDay(LocalDate day,Member member) {
        return privateTodoRepository.findAllByDay(day,member);
    }
    public List<PrivateTodo> getAllPrivateTodoByOneMonth(LocalDate date,Member member) {
        return privateTodoRepository.findAllByMonth(date,member);
    }
    public TreeMap<LocalDate, Float> getRatioByDailyInMonth(LocalDate date, Member member) {
        List<DailyAchievement> dailyAchievements = privateTodoRepository.countRatioByDailyInMonth( date, member);
        TreeMap<LocalDate, Float> dailyRatio= new TreeMap<>();
        for(DailyAchievement daily:dailyAchievements ) {
            LocalDate dateCheck = daily.getDate();
            if(dailyRatio.containsKey(dateCheck)){
                dailyRatio.put(dateCheck,(dailyRatio.get(dateCheck) + daily.getDone())*0.5f);
            }
            else {
                dailyRatio.put(dateCheck,daily.getDone()*1.0f);
            }
        }

        return dailyRatio;
    }
    @Transactional
    public PrivateTodo updatePrivateTodoImageUrl(Long private_todo_id,String imageUrl) {
        return privateTodoRepository.updatePrivateTodoImageUrl(private_todo_id,imageUrl);
    }
    @Transactional
    public PrivateTodo updatePrivateTodoTask(Long private_todo_id,String task) {
        return privateTodoRepository.updatePrivateTodoTask(private_todo_id,task);
    }
    @Transactional
    public PrivateTodo updatePrivateTodoComplete(Long private_todo_id,Boolean complete) {
        return privateTodoRepository.updatePrivateTodoComplete(private_todo_id,complete);
    }

}

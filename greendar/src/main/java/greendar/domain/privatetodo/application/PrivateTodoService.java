package greendar.domain.privatetodo.application;

import greendar.domain.member.domain.Member;

import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievement;
import greendar.domain.privatetodo.dao.PrivateTodoRepository;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
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
        return calculateRatio(dailyAchievements) ;
    }
    public TreeMap<LocalDate, Float> calculateRatio(List<DailyAchievement> dailyAchievements){
        TreeMap<LocalDate, Float> dailyRatio= new TreeMap<>();
        int count = 1 ;
        for(DailyAchievement daily:dailyAchievements ) {
            LocalDate dateCheck = daily.getDate();
            if(dailyRatio.containsKey(dateCheck)){
                float tmp = ((dailyRatio.get(dateCheck) + daily.getDone()))*count ;
                count+=1;
                dailyRatio.put(dateCheck,(tmp/count));
            }
            else {
                count = 1 ;
                dailyRatio.put(dateCheck,daily.getDone()*0.0f);
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

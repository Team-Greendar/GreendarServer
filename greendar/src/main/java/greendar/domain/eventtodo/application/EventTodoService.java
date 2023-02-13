package greendar.domain.eventtodo.application;

import greendar.domain.eventtodo.dao.EventTodoRepository;
import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodo.dto.EventTodoDtos.EventTodoResponse;
import greendar.domain.eventtodoitem.dao.EventTodoItemRepository;
import greendar.domain.eventtodoitem.domain.EventTodoItem;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class EventTodoService {
    private final MemberRepository memberRepository;
    private final EventTodoRepository eventTodoRepository;
    private final EventTodoItemRepository eventTodoItemRepository;
    private final PrivateTodoService privateTodoService;

    public TreeMap<LocalDate, Float> getRatioByDailyInMonth(LocalDate date, Member member) {
        List<EventTodoItem> eventTodoItems =  eventTodoItemRepository.findAllByMonth(date);
        List<DailyAchievement> dailyAchievementsEventTodo = eventTodoRepository.countRatioByDailyInMonth(date,member);
        List<DailyAchievement> dailyAchievements = new ArrayList<>();

        for(EventTodoItem eventTodoItem:eventTodoItems){
            LocalDate checkDate = eventTodoItem.getDate();
            if(dailyAchievementsEventTodo.contains(checkDate)){
                dailyAchievements.add(new DailyAchievement(checkDate,dailyAchievementsEventTodo.get(0).getDone()));
                dailyAchievementsEventTodo.remove(0);
            }else{
                dailyAchievements.add(new DailyAchievement(checkDate,0));
            }
        }

        return  privateTodoService.calculateRatio(dailyAchievements);
    }

    @Transactional
    public void saveEventTodo(Boolean complete ,String imageUrl,EventTodoItem eventTodoItem,Member member) {

        // imgaeurl
        eventTodoRepository.save(complete,imageUrl,eventTodoItem,member);

        //both

    }

    public void getAllEventTodoByOneDay(LocalDate date , Member member) {
        List<EventTodoItem> eventTodoItems = eventTodoItemRepository.findAllByDay(date);
        List<EventTodo> eventTodos = eventTodoRepository.findAllByDay(date,member);
        List<EventTodoResponse> eventTodoResponses = new ArrayList<>();
        for(EventTodoItem eventTodoItem : eventTodoItems) {
        }

//        return eventTodoResponses;
    }

}
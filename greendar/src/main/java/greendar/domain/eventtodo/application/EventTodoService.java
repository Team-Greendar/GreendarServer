package greendar.domain.eventtodo.application;

import greendar.domain.eventtodo.dao.EventTodoRepository;
import greendar.domain.eventtodo.model.EventTodo;
import greendar.domain.eventtodo.dto.EventTodoResponseDto;
import greendar.domain.eventtodoitem.dao.EventTodoItemRepository;
import greendar.domain.eventtodoitem.model.EventTodoItem;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.model.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
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

    @Transactional
    public EventTodo updateEventTodo(Boolean complete , String imageUrl, Long eventTodoItemId,String token) {
        Member member = memberRepository.fineOneByToken(token);
        EventTodoItem eventTodoItem = eventTodoItemRepository.findOneById(eventTodoItemId);
        EventTodo eventTodo = eventTodoRepository.findOneByEventTodoItemIdandMemberId(eventTodoItem.getId(),member.getId())
                .orElseThrow(() -> new EntityNotFoundException("EventTodo not found w" ));

        // 생성
        if(eventTodo==null) {
            if(complete == null)
            {
                return eventTodoRepository.save(false,imageUrl,eventTodoItem,member);
            }
            else {
                return eventTodoRepository.save(true,"EMPTY",eventTodoItem,member);
            }
        }else{
            if(complete == null)
            {
                return eventTodo.updateImage(imageUrl);
//                return eventTodoRepository.updateEventTodoImageUrl(eventTodo.getId(),imageUrl);
            }
            else {
                return eventTodo.updateComplete(complete);
            }
        }

    }
    public TreeMap<LocalDate, Float> getDailyRatioByMonth(LocalDate date, Member member) {
        List<DailyAchievement> dailyAchievements = getEventTodoDailyAchievements(date, member);
        return  privateTodoService.calculateRatio(dailyAchievements);
    }
    public double getMonthlyRatio(LocalDate date, Member member) {
        List<DailyAchievement> dailyAchievements = getEventTodoDailyAchievements(date, member);
        return  privateTodoService.calculateMonthlyRatio(dailyAchievements);
    }
    public List<DailyAchievement> getEventTodoDailyAchievements(LocalDate date, Member member){
        List<EventTodoItem> eventTodoItems =  eventTodoItemRepository.findAllByMonth(date);
        List<EventTodoResponseDto> eventTodos = eventTodoRepository.findAllByMonth(date,member);
        List<EventTodoResponseDto> eventTodoResponses = getEventTodoResponsesByCompare(eventTodoItems,eventTodos);
        return  eventTodoResponses.stream()
                .map(DailyAchievement::new)
                .collect(Collectors.toList());
    }


    public List<EventTodoResponseDto> getAllEventTodoByOneDay(LocalDate date , Member member) {

        List<EventTodoItem> eventTodoItems = eventTodoItemRepository.findAllByDay(date);
        List<EventTodoResponseDto> eventTodos = eventTodoRepository.findAllByDay(date,member);

        return  getEventTodoResponsesByCompare(eventTodoItems,eventTodos);
    }
    public List<EventTodoResponseDto> getAllEventTodoByOneMonth(LocalDate date , Member member) {

        List<EventTodoItem> eventTodoItems = eventTodoItemRepository.findAllByMonth(date);
        List<EventTodoResponseDto> eventTodos = eventTodoRepository.findAllByMonth(date,member);

        return  getEventTodoResponsesByCompare(eventTodoItems,eventTodos);
    }
    public List<EventTodoResponseDto> getEventTodoResponsesByCompare(List<EventTodoItem> eventTodoItems,List<EventTodoResponseDto> eventTodos){

        Map<Long,EventTodoResponseDto> eventTodoMap = new TreeMap<>();
        for(EventTodoResponseDto eventTodoItem : eventTodos){
            eventTodoMap.put(eventTodoItem.getEventTodoItemId(),eventTodoItem);
        }

        List<EventTodoResponseDto> eventTodoResponses = new ArrayList<>();

        for(EventTodoItem eventTodoItem : eventTodoItems){
            Long checkKey = eventTodoItem.getId() ;
            if(eventTodoMap.containsKey(checkKey)) {
                eventTodoResponses.add(eventTodoMap.get(checkKey));
            }
            else {
                eventTodoResponses.add(new EventTodoResponseDto(eventTodoItem));
            }
        }
        return eventTodoResponses;
    }
}
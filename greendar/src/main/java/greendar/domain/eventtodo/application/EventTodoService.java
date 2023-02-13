package greendar.domain.eventtodo.application;

import greendar.domain.eventtodo.dao.EventTodoRepository;
import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodo.dto.EventTodoDtos.EventTodoResponseDto;
import greendar.domain.eventtodoitem.dao.EventTodoItemRepository;
import greendar.domain.eventtodoitem.domain.EventTodoItem;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
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
    public EventTodo saveEventTodo(Boolean complete , String imageUrl, EventTodoItem eventTodoItem, Member member) {

        // imgaeurl

        eventTodoRepository.save(complete,imageUrl,eventTodoItem,member);

        //both

    }
    public TreeMap<LocalDate, Float> getRatioByDailyInMonth(LocalDate date, Member member) {

        List<EventTodoItem> eventTodoItems =  eventTodoItemRepository.findAllByMonth(date);
        List<EventTodoResponseDto> eventTodos = eventTodoRepository.findAllByMonth(date,member);

        List<EventTodoResponseDto> eventTodoResponses = getEventTodoResponsesByCompare(eventTodoItems,eventTodos);

        List<DailyAchievement> dailyAchievements = eventTodoResponses.stream()
                .map(DailyAchievement::new)
                .collect(Collectors.toList());
        return  privateTodoService.calculateRatio(dailyAchievements);
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
package greendar.domain.eventtodoitem.application;

import greendar.domain.eventtodoitem.dao.EventTodoItemRepository;
import greendar.domain.eventtodoitem.domain.EventTodoItem;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class EventTodoItemService {

    private final EventTodoItemRepository eventTodoItemRepository;

    @Transactional
    public EventTodoItem saveTodo(String task, LocalDate date) {
        return eventTodoItemRepository.save(task,date);
    }

    @Transactional
    public EventTodoItem saveTodoAddWeek(String task, LocalDate date) {
        for(int i = 0 ; i<7;i++ ) {
            eventTodoItemRepository.save(task, date.plusDays(i));
        }
        return eventTodoItemRepository.save(task,date);
    }

    public List<EventTodoItem> findAll() {
        return eventTodoItemRepository.findAll();
    }




}

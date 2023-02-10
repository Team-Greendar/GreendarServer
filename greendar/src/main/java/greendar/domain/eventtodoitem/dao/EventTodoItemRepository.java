package greendar.domain.eventtodoitem.dao;

import greendar.domain.eventtodoitem.domain.EventTodoItem;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventTodoItemRepository {

    @PersistenceContext
    private final EntityManager em;

    public EventTodoItem save(String task , LocalDate date) {
        EventTodoItem eventTodoItem = new EventTodoItem();
        eventTodoItem.setDate(date);
        eventTodoItem.setTask(task);
        em.persist(eventTodoItem);
        return eventTodoItem;
    }

    public List<EventTodoItem> findAll() {
        return  em.createQuery("select p from EventTodoItem p", EventTodoItem.class)
                .getResultList();
    }

    public void delete(Long id){
        EventTodoItem eventTodoItem = em.find(EventTodoItem.class,id);
        em.remove(eventTodoItem);
    }

}

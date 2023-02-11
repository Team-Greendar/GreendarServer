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
    public List<EventTodoItem> findAllByDay(LocalDate date) {
        return  em.createQuery("select p from EventTodoItem p "+
                        "where p.date =:oneDay"
                        , EventTodoItem.class)
                .setParameter("oneDay",date)
                .getResultList();
    }
}

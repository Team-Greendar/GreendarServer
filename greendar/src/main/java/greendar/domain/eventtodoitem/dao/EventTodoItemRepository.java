package greendar.domain.eventtodoitem.dao;

import greendar.domain.eventtodoitem.model.EventTodoItem;
import java.time.LocalDate;
import java.time.YearMonth;
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
        EventTodoItem eventTodoItem = EventTodoItem.of(task,date);
        em.persist(eventTodoItem);
        return eventTodoItem;
    }

    public List<EventTodoItem> findAll() {
        return  em.createQuery("select p from EventTodoItem p", EventTodoItem.class)
                .getResultList();
    }
    public EventTodoItem findOneById(Long id){
        return em.find(EventTodoItem.class,id);
    }
    public void delete(Long id){
        EventTodoItem eventTodoItem = em.find(EventTodoItem.class,id);
        em.remove(eventTodoItem);
    }
    public List<EventTodoItem> findAllByDay(LocalDate date) {
        return  em.createQuery("select p from EventTodoItem p " +
                "where p.date =:oneDay " +
                "order by p.date desc "
                , EventTodoItem.class)
                .setParameter("oneDay",date)
                .getResultList();
    }
    public List<EventTodoItem> findAllByMonth(LocalDate date) {
        YearMonth month = YearMonth.from(date);
        LocalDate start = month.atDay(1);
        LocalDate end = month.atEndOfMonth();
        return em.createQuery("select e from EventTodoItem e " +
                                        "where e.date between  :startDate and :endDate " +
                                        "order by e.date desc"
                        ,EventTodoItem.class)
                .setParameter("startDate",start)
                .setParameter("endDate",end)
                .getResultList();
    }
}

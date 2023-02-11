package greendar.domain.eventtodo.dao;

import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodoitem.domain.EventTodoItem;
import greendar.domain.member.domain.Member;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EventTodoRepository {
    @PersistenceContext
    private final EntityManager em;

    public void save(Boolean complete, String imageUrl, EventTodoItem eventTodoItem, Member member) {
        EventTodo eventTodo = new EventTodo();
        eventTodo.setComplete(complete);
        eventTodo.setImageUrl(imageUrl);
        eventTodo.setEventTodoItem(eventTodoItem);
        eventTodo.setMember(member);
        em.persist(eventTodo);
    }

    public EventTodo updateEventTodoComplete(Long eventTodoId, Boolean complete) {
        EventTodo eventTodo = em.find(EventTodo.class, eventTodoId);
        eventTodo.setComplete(complete);
        em.merge(eventTodo);
        return eventTodo;
    }

    public EventTodo updateEventTodoImageUrl(Long eventTodoId, String imageUrl) {
        EventTodo eventTodo = em.find(EventTodo.class, eventTodoId);
        eventTodo.setImageUrl(imageUrl);
        em.merge(eventTodo);
        return eventTodo;
    }

    public EventTodo findOneByEventTodoItemIdMemberId(Long memberId, Long eventTodoItemId) {
        try {
            return em.createQuery("select e from EventTodo  e " +
                                    "join fetch e.member m " +
                                    "join fetch e.eventTodoItem i " +
                                    "where m.id =:memberId and i.id =:eventTodoItemId"
                            , EventTodo.class)
                    .setParameter("memberId", memberId)
                    .setParameter("eventTodoItemId", eventTodoItemId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<EventTodo> findAllEventTodoByMember(Long memberId) {
        return em.createQuery("select e from EventTodo  e " +
                                "join fetch e.member m " +
                                " where m.id = :memberId " +
                                "order by e.eventTodoItem.date"
                        , EventTodo.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<EventTodo> findAllByMonth(LocalDate date, Member member) {

            YearMonth month = YearMonth.from(date);
            LocalDate start = month.atDay(1);
            LocalDate end = month.atEndOfMonth();
            return em.createQuery("select e from EventTodo e " +
                    "where e.member.id =:memberId and e.eventTodoItem.date between :startDate and :endDate " +
                    "order by  e.eventTodoItem.date",EventTodo.class)
                    .setParameter("startDate",start)
                    .setParameter("endDate",end)
                    .setParameter("memberId",member.getId())
                    .getResultList();
            //

    }




}

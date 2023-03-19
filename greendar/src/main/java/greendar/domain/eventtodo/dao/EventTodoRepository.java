package greendar.domain.eventtodo.dao;

import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodo.domain.TodoImage;
import greendar.domain.eventtodo.dto.EventTodoResponseDto;
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

    public EventTodo save(Boolean complete, String imageUrl, EventTodoItem eventTodoItem, Member member) {
        EventTodo eventTodo = EventTodo.of(new TodoImage(imageUrl),complete,eventTodoItem,member);
        em.persist(eventTodo);
        return eventTodo;
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
                                    "where e.member.id =:memberId and e.eventTodoItem.id =:eventTodoItemId"
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
                                " where e.member.id = :memberId " +
                                "order by e.eventTodoItem.date desc"
                        , EventTodo.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    public List<EventTodoResponseDto> findAllByDay(LocalDate day, Member member){
        return em.createQuery("select  new greendar.domain.eventtodo.dto.EventTodoResponseDto(e) "+
                    "from EventTodo e " +
                    "where e.eventTodoItem.date = :oneDay and e.member.id = :memberId " +
                    "order by e.eventTodoItem.date desc"
                    , EventTodoResponseDto.class)
                    .setParameter("oneDay",day)
                    .setParameter("memberId",member.getId())
                    .getResultList();
    }


    public List<EventTodoResponseDto> findAllByMonth(LocalDate date, Member member) {
            YearMonth month = YearMonth.from(date);
            LocalDate start = month.atDay(1);
            LocalDate end = month.atEndOfMonth();
            return em.createQuery("select new greendar.domain.eventtodo.dto.EventTodoResponseDto(e) " +
                        "from EventTodo e " +
                        "where e.member.id=:memberId and e.eventTodoItem.date between :startDate and :endDate " +
                        "order by  e.eventTodoItem.date desc",EventTodoResponseDto.class)
                        .setParameter("startDate",start)
                        .setParameter("endDate",end)
                        .setParameter("memberId",member.getId())
                        .getResultList();
    }

}

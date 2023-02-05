package greendar.domain.privatetodo.dao;

import greendar.domain.member.Member;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PrivateTodoRepository {

    @PersistenceContext
    private final EntityManager em;

    public PrivateTodo saveTodo(Member member , String task, LocalDate date) {
        PrivateTodo privateTodo = new PrivateTodo();
        privateTodo.setMember(member);
        privateTodo.setTask(task);
        privateTodo.setDate(date);
        privateTodo.setComplete(false);
        privateTodo.setImageUrl("DEFAULT");
        em.persist(privateTodo);
        return privateTodo;
    }
    //목록 불러오기
    public List<PrivateTodo> findAll()
    {
        return em.createQuery("select u from PrivateTodo u",PrivateTodo.class)
                .getResultList();
    }

    public List<PrivateTodo> findAllByDay(LocalDate day)
    {
        return em.createQuery("select p from PrivateTodo p" +
                        " where p.date = :oneDay",PrivateTodo.class)
                .setParameter("oneDay",day)
                .getResultList();
    }
    public List<PrivateTodo> findAllByMonth(LocalDate date)
    {   YearMonth month = YearMonth.from(date);
        LocalDate start = month.atDay(1);
        LocalDate end   = month.atEndOfMonth();
        List<PrivateTodo> monthlyData = em.createQuery("select p "
                                + "from PrivateTodo p "
                                +"where p.date between :startDate and :endDate"
                        ,PrivateTodo.class)
                .setParameter("startDate",start)
                .setParameter("endDate",end)
                .getResultList();
        return monthlyData;
    }

    public List<DailyAchievementRateDao> getRatioByDailyInMonth(LocalDate date)
    {   YearMonth month = YearMonth.from(date);
        LocalDate start = month.atDay(1);
        LocalDate end   = month.atEndOfMonth();
        return em.createQuery("select p.date As date" +
                                ", avg(p.complete)*100 As rate "
                                +" from PrivateTodo p"
                                +" where p.date between :startDate and :endDate"
                                +" group by p.date"
                                ,DailyAchievementRateDao.class)
                .setParameter("startDate",start)
                .setParameter("endDate",end)
                .getResultList();
    }

    public PrivateTodo updatePrivateTodoImageUrl(Long private_todo_id,String imageUrl) {
        PrivateTodo privateTodo = em.find(PrivateTodo.class,private_todo_id);
        privateTodo.setImageUrl(imageUrl);
        em.merge(privateTodo);
        return privateTodo;
    }

    public PrivateTodo updatePrivateTodoTask(Long private_todo_id,String task) {
        PrivateTodo privateTodo = em.find(PrivateTodo.class,private_todo_id);
        privateTodo.setTask(task);
        em.merge(privateTodo);
        return privateTodo;
    }

    public PrivateTodo updatePrivateTodoComplete(Long private_todo_id,Boolean complete) {
        PrivateTodo privateTodo = em.find(PrivateTodo.class,private_todo_id);
        privateTodo.setComplete(complete);
        em.merge(privateTodo);
        return privateTodo;
    }


}

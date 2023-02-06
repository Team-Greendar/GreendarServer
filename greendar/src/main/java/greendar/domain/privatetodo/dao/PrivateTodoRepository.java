package greendar.domain.privatetodo.dao;

import greendar.domain.member.domain.Member;
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
    public List<PrivateTodo> findAllPrivateTodoByMember(Member member)
    {
        return em.createQuery("select p from PrivateTodo p "+
                        "join fetch p.member m " +
                        "where m.id = :member_id " +
                        " order by p.date desc"
                        ,PrivateTodo.class)
                .setParameter("member_id",member.getId())
                .getResultList();
    }

    public List<PrivateTodo> findAllByDay(LocalDate day,Member member)
    {
        return em.createQuery("select p from PrivateTodo p " +
                        "join fetch p.member m " +
                        "where p.date = :oneDay and m.id = :member_id",PrivateTodo.class)
                .setParameter("oneDay",day)
                .setParameter("member_id",member.getId())
                .getResultList();
    }
    public List<PrivateTodo> findAllByMonth(LocalDate date,Member member)
    {   YearMonth month = YearMonth.from(date);
        LocalDate start = month.atDay(1);
        LocalDate end   = month.atEndOfMonth();
        return em.createQuery("select p from PrivateTodo p " +
                                "join fetch p.member m " +
                                "where m.id = :member_id and p.date between :startDate and :endDate"
                        ,PrivateTodo.class)
                .setParameter("startDate",start)
                .setParameter("endDate",end)
                .setParameter("member_id",member.getId())
                .getResultList();
    }

    public List<DailyAchievementRateDao> countRatioByDailyInMonth(LocalDate date,Member member)
    {   YearMonth month = YearMonth.from(date);
        LocalDate start = month.atDay(1);
        LocalDate end   = month.atEndOfMonth();
        return em.createQuery("select p.date As date, " +
                                "avg(p.complete)*100 As rate " +
                                "from PrivateTodo p " +
                                "join fetch p.member m " +
                                "where m.id = :member_id and p.date between :startDate and :endDate " +
                                "group by p.date"
                                ,DailyAchievementRateDao.class)
                .setParameter("startDate",start)
                .setParameter("endDate",end)
                .setParameter("member_id",member.getId())
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
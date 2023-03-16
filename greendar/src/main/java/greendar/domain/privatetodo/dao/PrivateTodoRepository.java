package greendar.domain.privatetodo.dao;

import greendar.domain.member.domain.Member;

import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievement;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;
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
        PrivateTodo privateTodo = PrivateTodo.of(task,date,"EMPTY",false,member);
        em.persist(privateTodo);
        return privateTodo;
    }
    public void deleteTodo(Long id){
        em.remove(em.find(PrivateTodo.class,id));
    }
    //목록 불러오기
    public List<PrivateTodo> findAllPrivateTodoByMember(Member member)
    {
        return em.createQuery("select p from PrivateTodo p "+
                                "where p.member.id = :memberId " +
                                " order by p.date desc"
                        ,PrivateTodo.class)
                .setParameter("memberId",member.getId())
                .getResultList();
    }

    public List<PrivateTodo> findAllByDay(LocalDate day,Member member)
    {
        return em.createQuery("select p from PrivateTodo p " +
                        "where p.date = :oneDay and p.member.id = :memberId",PrivateTodo.class)
                .setParameter("oneDay",day)
                .setParameter("memberId",member.getId())
                .getResultList();
    }
    public List<PrivateTodo> findAllByMonth(LocalDate date,Member member)
    {   YearMonth month = YearMonth.from(date);
        LocalDate start = month.atDay(1);
        LocalDate end   = month.atEndOfMonth();
        return em.createQuery("select p from PrivateTodo p " +
                                "where p.member.id = :memberId and p.date between :startDate and :endDate"
                        ,PrivateTodo.class)
                .setParameter("startDate",start)
                .setParameter("endDate",end)
                .setParameter("memberId",member.getId())
                .getResultList();
    }

    public List<DailyAchievement> countRatioByDailyInMonth(LocalDate date, Member member)
    {       List<PrivateTodo> find = findAllByMonth(date,member);
        return find.stream().map(DailyAchievement::new).collect(Collectors.toList());
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

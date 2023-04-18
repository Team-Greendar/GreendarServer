package greendar.domain.eventtodo.dao;

import greendar.domain.eventtodo.model.EventTodo;
import greendar.domain.eventtodo.dto.EventTodoResponseDto;
import greendar.domain.eventtodoitem.model.EventTodoItem;
import greendar.domain.member.model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTodoRepository extends JpaRepository<EventTodo,Long> {

    Optional<EventTodo> findByEventTodoItemIdAndMemberId(Long eventTodoItemId, Long memberId);

    List<EventTodo> findAllEventTodoByMember(Long memberId);

    @Query("select  new greendar.domain.eventtodo.dto.EventTodoResponseDto(e) "+
            "from EventTodo e " +
            "where e.eventTodoItem.date = :oneDay and e.member.id = :memberId " +
            "order by e.eventTodoItem.date desc")
    List<EventTodoResponseDto> findAllByDay(LocalDate day, Member member);

    @Query("select new greendar.domain.eventtodo.dto.EventTodoResponseDto(e) " +
            "from EventTodo e " +
            "where e.member.id=:memberId and e.eventTodoItem.date between :startDate and :endDate " +
            "order by  e.eventTodoItem.date desc")
    List<EventTodoResponseDto> findAllByMonth(LocalDate date, Member member);

}

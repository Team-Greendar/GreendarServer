package greendar.domain.eventtodo.dao;

import greendar.domain.eventtodo.model.EventTodo;
import greendar.domain.eventtodo.dto.EventTodoResponseDto;
import greendar.domain.eventtodoitem.model.EventTodoItem;
import greendar.domain.member.model.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTodoRepository extends JpaRepository<EventTodo,Long> {

    Optional<EventTodo> findOneByEventTodoItemIdandMemberId(Long eventTodoItemId, Long memberId);

    List<EventTodo> findAllEventTodoByMember(Long memberId);

    List<EventTodoResponseDto> findAllByDay(LocalDate day, Member member);
    List<EventTodoResponseDto> findAllByMonth(LocalDate date, Member member);

}

package greendar.domain.eventtodo.application;

import static org.junit.jupiter.api.Assertions.*;

import greendar.domain.eventtodo.dao.EventTodoRepository;
import greendar.domain.eventtodo.model.EventTodo;
import greendar.domain.eventtodo.model.TodoImage;
import greendar.domain.eventtodoitem.dao.EventTodoItemRepository;
import greendar.domain.eventtodoitem.model.EventTodoItem;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.model.Member;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback
class EventTodoServiceTest {

    @Autowired
    private EventTodoService eventTodoService;

    @Autowired
    private EventTodoRepository eventTodoRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EventTodoItemRepository eventTodoItemRepository;
    private EventTodoItem eventTodoItem;

    @Test
    void updateEventTodoTest() {
        Member member = Member.of("testName","pass","email@naver.com","image","testMessage","testToken");
        memberRepository.saveMember(member.getName(), member.getPassword(),member.getEmail(),member.getImageUrl(),member.getImageUrl(),member.getToken());

        LocalDate testDate = LocalDate.parse("2022-12-04");
        eventTodoItem = EventTodoItem.of("testTask",testDate);
        eventTodoItemRepository.save(eventTodoItem.getTask(),eventTodoItem.getDate());

        EventTodo eventTodo = EventTodo.of(new TodoImage("Test Image URL"), false, eventTodoItem, member);
        eventTodoRepository.save(eventTodo);

        // Call the method being tested
        EventTodo updatedEventTodo = eventTodoService.updateEventTodo(true, "Updated Image URL", eventTodoItem.getId(), member.getToken());

        // Verify the result
        assertNotNull(updatedEventTodo);
        assertTrue(updatedEventTodo.isComplete());
        assertEquals("Updated Image URL", updatedEventTodo.getTodoImage().getTodoImageUrl());
    }

    @Test
    void getDailyRatioByMonth() {

    }

    @Test
    void getMonthlyRatio() {
    }

    @Test
    void getEventTodoDailyAchievements() {
    }

    @Test
    void getAllEventTodoByOneDay() {
    }

    @Test
    void getAllEventTodoByOneMonth() {
    }

    @Test
    void getEventTodoResponsesByCompare() {
    }
}
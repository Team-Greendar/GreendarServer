package greendar.domain.privatetodo.model;

import static org.junit.jupiter.api.Assertions.*;

import greendar.domain.eventtodo.model.TodoImage;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.model.Member;
import greendar.domain.privatetodo.model.PrivateTodo;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class PrivateTodoTest {


    private final TodoImage todoImage = new TodoImage("https://example.com/image.jpg");
    private final String task = "Task 1";
    private final LocalDate date = LocalDate.now();
    private final Boolean complete = false;
    private Member member;

    @Autowired
    private MemberRepository memberRepository ;

    @BeforeEach
    public void setUp() {
        this.member  = memberRepository.saveMember("test", "test1234", "test@example.com", null, null, null);
    }


    @Test
    public void testConstructor() {
        PrivateTodo privateTodo = new PrivateTodo();
        assertNotNull(privateTodo);
    }

    @Test
    public void testBuilder() {
        PrivateTodo privateTodo = PrivateTodo.builder()
                .task(task)
                .date(date)
                .todoImage(todoImage)
                .complete(complete)
                .member(member)
                .build();
        assertNotNull(privateTodo);
        assertEquals(task, privateTodo.getTask());
        assertEquals(date, privateTodo.getDate());
        assertEquals(todoImage, privateTodo.getTodoImage());
        assertEquals(complete, privateTodo.getComplete());
        assertEquals(member, privateTodo.getMember());
    }

    @Test
    public void testStaticFactoryMethod() {
        PrivateTodo privateTodo = PrivateTodo.of(task, date, todoImage, complete, member);
        assertNotNull(privateTodo);
        assertEquals(task, privateTodo.getTask());
        assertEquals(date, privateTodo.getDate());
        assertEquals(todoImage, privateTodo.getTodoImage());
        assertEquals(complete, privateTodo.getComplete());
        assertEquals(member, privateTodo.getMember());
    }

    @Test
    public void testSetImageUrl() {
        PrivateTodo privateTodo = PrivateTodo.of(task, date, null, complete, member);
        String imageUrl = "https://example.com/image.jpg";
        privateTodo.setImageUrl(imageUrl);
        assertEquals(imageUrl, privateTodo.getTodoImage().getTodoImageUrl());
    }

    @Test
    public void testSetTask() {
        PrivateTodo privateTodo = PrivateTodo.of(task, date, todoImage, complete, member);
        String newTask = "Task 2";
        privateTodo.setTask(newTask);
        assertEquals(newTask, privateTodo.getTask());
    }

    @Test
    public void testSetComplete() {
        PrivateTodo privateTodo = PrivateTodo.of(task, date, todoImage, complete, member);
        Boolean newComplete = true;
        privateTodo.setComplete(newComplete);
        assertEquals(newComplete, privateTodo.getComplete());
    }
}
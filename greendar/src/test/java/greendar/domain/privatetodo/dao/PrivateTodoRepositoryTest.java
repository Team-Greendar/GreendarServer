package greendar.domain.privatetodo.dao;

import static org.junit.jupiter.api.Assertions.*;

import greendar.domain.member.model.Member;
import greendar.domain.privatetodo.model.PrivateTodo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class PrivateTodoRepositoryTest {

    @Autowired
    private PrivateTodoRepository privateTodoRepository;
    @BeforeEach
    public void setUp(){
        Member member = new Member("email@example.com", "password");
        member = memberRepository.save(member);

        // Create a new PrivateTodo object and s
        PrivateTodo privateTodo = PrivateTodo.builder().task
    }
    @Test
    void saveTodo() {
    }

    @Test
    void findAllPrivateTodoByMember() {
    }

    @Test
    void findAllByDay() {
    }

    @Test
    void findAllByMonth() {
    }

    @Test
    void countRatioByDailyInMonth() {
    }

    @Test
    void updatePrivateTodoImageUrl() {
    }

    @Test
    @DirtiesContext
    void updatePrivateTodoTaskTest_checkInDB() {
    }

    @Test
    void updatePrivateTodoComplete() {
    }
}
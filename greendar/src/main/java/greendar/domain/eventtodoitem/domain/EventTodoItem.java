package greendar.domain.eventtodoitem.domain;

import greendar.domain.model.BaseTimeEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventTodoItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_todo_item_id")
    private Long id;
    private String task;
    private LocalDate date;

    private EventTodoItem(String task,LocalDate date) {
        this.task = task;
        this.date = date;
    }
    public static EventTodoItem of(String task,LocalDate date) {
        return new EventTodoItem(task,date);
    }
}

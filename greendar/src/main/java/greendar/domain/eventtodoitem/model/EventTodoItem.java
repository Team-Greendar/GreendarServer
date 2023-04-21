package greendar.domain.eventtodoitem.model;

import greendar.domain.eventtodo.model.EventTodo;
import greendar.global.common.model.BaseTimeEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

    @OneToMany(mappedBy = "eventTodoItem")
    List<EventTodo> eventTodos = new ArrayList<>();


    private EventTodoItem(String task,LocalDate date) {
        this.task = task;
        this.date = date;
    }
    public static EventTodoItem of(String task,LocalDate date) {
        return new EventTodoItem(task,date);
    }
}

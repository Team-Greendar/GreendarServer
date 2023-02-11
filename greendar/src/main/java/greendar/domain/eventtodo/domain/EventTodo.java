package greendar.domain.eventtodo.domain;

import static javax.persistence.FetchType.LAZY;

import greendar.domain.eventtodoitem.domain.EventTodoItem;
import greendar.domain.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EventTodo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_todo_id")
    private Long id;
    private String imageUrl;
    private Boolean complete;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="event_todo_item_id")
    private EventTodoItem eventTodoItem;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;


}

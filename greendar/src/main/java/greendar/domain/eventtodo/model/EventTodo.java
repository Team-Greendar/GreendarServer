package greendar.domain.eventtodo.model;

import static javax.persistence.FetchType.LAZY;

import greendar.domain.eventtodoitem.model.EventTodoItem;
import greendar.domain.member.model.Member;
import greendar.global.common.model.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class EventTodo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="event_todo_id")
    private Long id;

    @Embedded
    private TodoImage todoImage;
    private Boolean complete;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="event_todo_item_id")
    private EventTodoItem eventTodoItem;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    @Builder
    private EventTodo(TodoImage todoImage,Boolean complete,EventTodoItem eventTodoItem,Member member){
        this.todoImage = todoImage;
        this.complete = complete;
        this.eventTodoItem = eventTodoItem;
        this.member = member;
    }
    public static EventTodo of(TodoImage todoImage,Boolean complete,EventTodoItem eventTodoItem,Member member){
        return  EventTodo.builder()
                .todoImage(todoImage)
                .complete(complete)
                .eventTodoItem(eventTodoItem)
                .member(member)
                .build();
    }
    public void updateImage(String imageUrl) {
        this.todoImage =new TodoImage(imageUrl);
    }
    public void updateComplete(Boolean complete ) {
        this.complete = complete;
    }
}

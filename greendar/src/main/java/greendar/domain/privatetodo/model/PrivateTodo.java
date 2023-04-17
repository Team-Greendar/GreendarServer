package greendar.domain.privatetodo.model;

import static javax.persistence.FetchType.LAZY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import greendar.domain.eventtodo.model.TodoImage;
import greendar.domain.member.model.Member;
import greendar.global.common.model.BaseTimeEntity;
import java.time.LocalDate;
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
public class PrivateTodo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_todo_id") //pk
    private Long id ;

    private String task;
    private LocalDate date;

    @Embedded
    private TodoImage todoImage;
    private Boolean complete;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    private PrivateTodo(String task,LocalDate date,TodoImage todoImage,Boolean complete,Member member)
    {
        this.task=task;
        this.date=date;
        this.todoImage = todoImage;
        this.complete = complete;
        this.member = member;
    }
    public static PrivateTodo of(String task,LocalDate date,TodoImage todoImage,Boolean complete , Member member){
        return PrivateTodo.builder()
                .todoImage(todoImage)
                .complete(complete)
                .date(date)
                .task(task)
                .member(member)
                .build();
    }
    public void setImageUrl(String imageUrl) {
        this.todoImage = new TodoImage(imageUrl);
    }
    public void setTask(String task) {
        this.task =task;
    }
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}

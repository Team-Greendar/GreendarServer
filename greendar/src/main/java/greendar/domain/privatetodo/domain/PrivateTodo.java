package greendar.domain.privatetodo.domain;

import static javax.persistence.FetchType.LAZY;
import com.fasterxml.jackson.annotation.JsonIgnore;
import greendar.domain.member.domain.Member;
import greendar.global.common.model.BaseTimeEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
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

    @JsonIgnore
    private String imageUrl;
    private Boolean complete;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private PrivateTodo(String task,LocalDate date,String imageUrl,Boolean complete,Member member)
    {
        this.task=task;
        this.date=date;
        this.imageUrl = imageUrl;
        this.complete = complete;
        this.member = member;
    }
    public static PrivateTodo of(String task,LocalDate date,String imageUrl,Boolean complete , Member member){
        return new PrivateTodo(task,date,imageUrl,complete,member);
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setTask(String task) {
        this.task =task;
    }
    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}

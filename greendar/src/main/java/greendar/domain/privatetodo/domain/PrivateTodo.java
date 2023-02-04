package greendar.domain.privatetodo.domain;

import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PrivateTodo {

    @Id
    @GeneratedValue
    @Column(name = "private_todo_id") //pk
    private Long id ;
    private String task;
    private Date date;
    @JsonIgnore
    private String imageUrl;
    private Boolean complete;

//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "member_id")
//    private Member member;


}

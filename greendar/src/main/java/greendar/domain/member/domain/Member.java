package greendar.domain.member.domain;

import greendar.domain.privatetodo.domain.PrivateTodo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Getter;


@Getter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id") //pk
    private Long id ;
    private String name ;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PrivateTodo> privateToDoList = new ArrayList<>();
}

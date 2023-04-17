package greendar.domain.member.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import greendar.domain.eventtodo.model.EventTodo;
import greendar.global.common.model.BaseTimeEntity;
import greendar.domain.privatetodo.model.PrivateTodo;

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
import lombok.Setter;


@Setter
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", length = 64) //pk
    private Long id;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<PrivateTodo> privateToDoList = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<EventTodo> eventToDoList = new ArrayList<>();

    @Column(name = "name", length = 100)
    private String name;

    @JsonIgnore
    @Column(name = "password", length = 128)
    private String password;

    @Column(name = "email", length = 512)
    private String email;
    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(name = "status_message", length = 512)
    private String statusMessage;

    @Column(name = "token", length = 512)
    private String token;

    private Member(String name,
                   String password,
                   String email,
                   String imageUrl,
                   String message,
                   String token)
    {
        this.name=name;
        this.password = password;
        this.email=email;
        this.imageUrl = imageUrl;
        this.statusMessage = message;
        this.token = token;
    }

    public static Member of(String name,String password, String email, String imageUrl, String message, String token){
        return new Member(name,password, email, imageUrl, message, token);
    }
}

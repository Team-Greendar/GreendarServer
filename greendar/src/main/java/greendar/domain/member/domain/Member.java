package greendar.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import greendar.domain.auth.oauth.domain.ProviderType;
import greendar.domain.auth.oauth.domain.RoleType;
import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.privatetodo.domain.PrivateTodo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;


@Getter
@Entity
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

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

//    @Column(name = "EMAIL_VERIFIED_YN", length = 1)
//    @NotNull
//    @Size(min = 1, max = 1)
//    private String emailVerifiedYn;

    @Column(name = "imageUrl", length = 512)
    private String imageUrl;

    @Column(name = "statusMessage", length = 512)
    private String statusMessage;

    @Column(name = "token", length = 512)
    private String token;

    @Column(name = "PROVIDER_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ProviderType providerType;

    @Column(name = "ROLE_TYPE", length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleType roleType;
//
//    @Column(name = "CREATED_AT")
//    @NotNull
//    private LocalDateTime createdAt;
//
//    @Column(name = "MODIFIED_AT")
//    private LocalDateTime modifiedAt;

    private Member(String name,
                   String password,
                   String email,
                   String imageUrl,
                   String message,
                   @NotNull ProviderType providerType,
                   @NotNull RoleType roleType)
    {
        this.name=name;
        this.password = password;
        this.email=email;
        this.imageUrl = imageUrl;
        this.statusMessage = message;
        this.providerType = providerType;
        this.roleType = roleType;
//        this.createdAt= createdAt;
//        this.modifiedAt = modifiedAt;
    }

    public static Member of(String name,String password, String email, String imageUrl, String message, ProviderType providerType, RoleType roleType){
        return new Member(name,password, email, imageUrl, message, providerType, roleType);
    }
}

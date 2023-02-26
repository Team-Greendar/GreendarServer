package greendar.domain.member.dao;

import greendar.domain.auth.login.domain.user.UserRefreshToken;
import greendar.domain.member.domain.Member;
import greendar.domain.member.domain.MemberRefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@RequiredArgsConstructor
public class MemberRefreshTokenRepository {
    @PersistenceContext
    private final EntityManager em;

    public MemberRefreshToken findOneByMemberEmail(String email){
        return em.createQuery("select m from MemberRefreshToken m "+
                                "where m.email = :email"
                        , MemberRefreshToken.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public MemberRefreshToken findOneByMemberRefreshToken(String refreshToken){
        return em.createQuery("select m from MemberRefreshToken m "+
                                "where m.refreshToken = :refreshToken"
                        , MemberRefreshToken.class)
                .setParameter("refreshToken", refreshToken)
                .getSingleResult();
    }

    public void saveAndFlush(MemberRefreshToken refreshToken){
        em.merge(refreshToken);
        em.flush();
    }
}

package greendar.domain.member.dao;

import greendar.domain.member.domain.Member;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public Member saveMember(String name,String password, String email, String imageUrl, String message, String token) {
        Member member = Member.of(name,password, email, imageUrl, message, token);
        em.persist(member);
        return member;
    }

    public Member updateMemberImageUrl(String token, String imageUrl) {
        Member member = em.find(Member.class, token);
        member.setImageUrl(imageUrl);
        em.merge(member);
        return member;
    }

    public Member updateMemberProfile(String token, String name, String message) {
        Member member = em.find(Member.class, token);
        member.setName(name);
        member.setMessage(message);
        em.merge(member);
        return member;
    }

    public Member updateMemberEmail(String token, String email){
        Member member = em.find(Member.class, token);
        member.setEmail(email);
        em.merge(member);
        return member;
    }

    public Member saveFirebaseToken(String token, String firebaseToken){
        Member member = em.find(Member.class, token);
        member.setToken(firebaseToken);
        em.merge(member);
        return member;
    }

    public Member fineOneByToken(String token){
        return em.find(Member.class, token);
    }

    public List<Member> findOneByEmail(String userEmail) {
        return null;
    }

    public Member findOne(Long memberId) {
        return em.find(Member.class, memberId);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}

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

    public Member saveMember(String name,String password, String email, String imageUrl, String message) {
        Member member = Member.of(name,password, email, imageUrl, message);
        em.persist(member);
        return member;
    }

    public Member updateMemberImageUrl(Long memberId, String imageUrl) {
        Member member = em.find(Member.class, memberId);
        member.setImageUrl(imageUrl);
        em.merge(member);
        return member;
    }

    public Member updateMemberProfile(Long memberId, String name, String message) {
        Member member = em.find(Member.class, memberId);
        member.setName(name);
        member.setMessage(message);
        em.merge(member);
        return member;
    }

    public Member updateMemberEmail(Long memberId, String email){
        Member member = em.find(Member.class, memberId);
        member.setEmail(email);
        em.merge(member);
        return member;
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

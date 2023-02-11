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

    public void post(String name) {
        Member member = new Member();
        member.setName(name);
        em.persist(member);
    }

    public List<Member> findOneByEmail(String userEmail) {
        return null;
    }

    public Member findOne(Long id) {
        return em.find(Member.class,id);
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
    }
}

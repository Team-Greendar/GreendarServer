package greendar.domain.member.dao;

import greendar.domain.member.model.Member;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class MemberRepository {

    @PersistenceContext
    private final EntityManager em;

    public Optional<Member> saveMember(String name, String password, String email, String imageUrl, String message, String token) {
        try {
            validateDuplicateEmail(email);
            Member member = Member.of(name, password, email, imageUrl, message, token);
            em.persist(member);
            return Optional.of(member);
        } catch (IllegalStateException e) {
            return Optional.empty();
        }
    }
    public Optional<Member> findOneByEmail(String userEmail) {
        Member member = em.createQuery("select m from Member m " +
                                "where m.email = :email"
                        , Member.class)
                .setParameter("email", userEmail)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(member);
    }
    private void validateDuplicateEmail(String email){
        findOneByEmail(email).ifPresent( m -> {
            throw new IllegalStateException("이미 존재하는 이메일 입니다.");
        });
    }
    public void deleteMember(String inputToken) {
        Member member = fineOneByToken(inputToken).get();
        em.remove(member);
    }

    public Member updateMemberImageUrl(String inputToken, String imageUrl) {
        Member member = fineOneByToken(inputToken).get();
        member.setImageUrl(imageUrl);
        em.merge(member);
        return member;
    }

    public Member updateMemberProfile(String inputToken, String name, String message) {
        Member member = fineOneByToken(inputToken).get();
        member.setName(name);
        member.setStatusMessage(message);
        em.merge(member);
        return member;
    }

    public Member updateMemberEmail(String inputToken, String email) {
        Member member = fineOneByToken(inputToken).get();
        member.setEmail(email);
        em.merge(member);
        return member;
    }

    /**
     * 파이어베이스 UID가 오류가 있다는 전제 하에 만들어진 함수,
     * 그래서 memberId 로 member를 불러오고, UID를 토큰으로 저장
     *
     * @param memberId
     * @param firebaseToken
     * @return
     */
    public Member saveFirebaseToken(Long memberId, String firebaseToken) {
        Member member = em.find(Member.class, memberId);
        member.setToken(firebaseToken);
        em.merge(member);
        return member;
    }

    /**
     * 예외처리 해줘야 함
     *
     * @param inputToken 파이어베이스 UID
     * @return 멤버 객체
     */
    public Optional<Member> fineOneByToken(String inputToken) {
        return em.createQuery("select m from Member m " +
                                "where m.token = :firebaseToken"
                        , Member.class)
                .setParameter("firebaseToken", inputToken)
                .getResultList()
                .stream()
                .findFirst();
    }

    public boolean isMemberTokenExists(String token) {
        List<Member> memberList = em.createQuery("select m from Member m " +
                                "where m.token = :firebaseToken"
                        , Member.class)
                .setParameter("firebaseToken", token)
                .getResultList();
        return !memberList.isEmpty();
    }

    public boolean isMemberNameExists(String name) {
        List<Member> memberList = em.createQuery("select m from Member m " +
                                "where m.name = :inputName"
                        , Member.class)
                .setParameter("inputName", name)
                .getResultList();
        return !memberList.isEmpty();
    }
    public Optional<Member> findOne(Long memberId) {
        return Optional.ofNullable(em.find(Member.class, memberId));
    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}

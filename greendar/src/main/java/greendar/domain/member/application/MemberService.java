package greendar.domain.member.application;

import greendar.domain.hello.domain.Hello;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.domain.Member;
import java.util.List;
import javax.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {
    @PersistenceContext
    private MemberRepository memberRepository;
    @Transactional
    public boolean post(String name){
        memberRepository.post(name);
        return true;
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

}

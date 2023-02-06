package greendar.domain.member.application;

import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public Member findOne() {
        return null;
    }

}

package greendar.domain.member.dao;

import greendar.domain.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    public List<Member> findOneByEmail(String userEmail) {
        return null;
    }
}

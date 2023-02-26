package greendar.domain.auth.oauth.application;



import greendar.domain.auth.oauth.domain.MemberPrincipal;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findOneByEmail(username);
        if (member == null) {
            throw new UsernameNotFoundException("Can not find username.");
        }
        return MemberPrincipal.create(member);
    }
}
package greendar.domain.member.application;

import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.model.Member;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Member saveMember(String name, String password, String email, String imageUrl, String message, String token) {
        return memberRepository.saveMember(name, password, email, imageUrl, message, token)
                .orElseThrow(() -> new RuntimeException("Member not saved!"));
    }

    @Transactional
    public void deleteMember(String token) {
        memberRepository.deleteMember(token);
    }

    @Transactional
    public Member updateProfile(String token, String name, String message) {
        return memberRepository.updateMemberProfile(token, name, message);
    }

    @Transactional
    public Member updateImageUrl(String token, String imageUrl) {
        return memberRepository.updateMemberImageUrl(token, imageUrl);
    }

    @Transactional
    public Member updateEmail(String token, String email) {
        return memberRepository.updateMemberEmail(token, email);
    }

    public Member findOneByToken(String token) {
        return memberRepository.fineOneByToken(token);
    }

    public boolean isNameRedundant(String name) {
        return memberRepository.isMemberNameExists(name);
    }

    public boolean isTokenExists(String token) {
        return memberRepository.isMemberTokenExists(token);
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

}

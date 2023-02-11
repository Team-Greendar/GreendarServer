package greendar.domain.member.application;

import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.domain.Member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public Member saveMember(String name,String password, String email, String imageUrl, String message) {
        return memberRepository.saveMember(name,password, email, imageUrl, message);
    }

    @Transactional
    public Member updateProfile(Long memberId, String name, String message) {
        return memberRepository.updateMemberProfile(memberId, name, message);
    }

    @Transactional
    public Member updateImageUrl(Long memberId, String imageUrl) {
        return memberRepository.updateMemberImageUrl(memberId, imageUrl);
    }

    @Transactional
    public Member updateEmail(Long memberId, String email) {
        return memberRepository.updateMemberEmail(memberId, email);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

}

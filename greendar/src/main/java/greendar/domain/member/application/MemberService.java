package greendar.domain.member.application;

import greendar.domain.auth.oauth.domain.ProviderType;
import greendar.domain.auth.oauth.domain.RoleType;
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
    public Member saveMember(String name, String password, String email, String imageUrl, String message, ProviderType providertype, RoleType roleType) {
        return memberRepository.saveMember(name, password, email, imageUrl, message, providertype, roleType);
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

    public Member getMember(String email){
        return memberRepository.findOneByEmail(email);
    }



    public boolean isNameRedundant(String name){
        return memberRepository.isMemberNameExists(name);
    }

    public boolean isTokenExists(String token){
        return memberRepository.isMemberTokenExists(token);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

}

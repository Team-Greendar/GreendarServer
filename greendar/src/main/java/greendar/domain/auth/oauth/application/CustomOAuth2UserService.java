package greendar.domain.auth.oauth.application;

import greendar.domain.auth.oauth.domain.MemberPrincipal;
import greendar.domain.auth.oauth.domain.ProviderType;
import greendar.domain.auth.oauth.domain.RoleType;
import greendar.domain.auth.oauth.exeption.OAuthProviderMissMatchException;
import greendar.domain.auth.oauth.info.OAuth2UserInfo;
import greendar.domain.auth.oauth.info.OAuth2UserInfoFactory;
import greendar.domain.member.dao.MemberRepository;
import greendar.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);

        try {
            return this.process(userRequest, user);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
        ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());
        Member savedMember = memberRepository.findOneByEmail(userInfo.getId());

        if (savedMember != null) {
            if (providerType != savedMember.getProviderType()) {
                throw new OAuthProviderMissMatchException(
                        "Looks like you're signed up with " + providerType +
                                " account. Please use your " + savedMember.getProviderType() + " account to login."
                );
            }
            updateMember(savedMember, userInfo);
        } else {
            savedMember = createMember(userInfo, providerType);
        }

        return MemberPrincipal.create(savedMember, user.getAttributes());
    }

    private Member createMember(OAuth2UserInfo userInfo, ProviderType providerType) {

        Member member = Member.of(
                userInfo.getName(),
                userInfo.getPassword(),
                userInfo.getEmail(),
                userInfo.getImageUrl(),
                userInfo.getMessage(),
                providerType,
                RoleType.USER
        );

        return memberRepository.saveAndFlush(member);
    }

    private Member updateMember(Member member, OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !member.getName().equals(userInfo.getName())) {
            member.setName(userInfo.getName());
        }

        if (userInfo.getImageUrl() != null && !member.getImageUrl().equals(userInfo.getImageUrl())) {
            member.setImageUrl(userInfo.getImageUrl());
        }

        return member;
    }
}

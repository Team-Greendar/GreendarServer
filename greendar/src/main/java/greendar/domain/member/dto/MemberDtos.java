package greendar.domain.member.dto;


import greendar.domain.member.model.Member;
import lombok.*;

import javax.validation.constraints.NotNull;


public class MemberDtos {

    @Data
    @NotNull
    @NoArgsConstructor
    public static class MemberPostRequestDto {
        String name;
        String password;
        String email;
        String imageUrl;
        String message;
        String firebaseToken;
    }

    @Data
    @NotNull
    @NoArgsConstructor
    public static class MemberEmailGetRequestDto {
        private String email;
    }


    @Data
    @NotNull
    @NoArgsConstructor
    public static class MemberGetRequestDto {
        private Long id;
    }


    @Data
    @NotNull
    @NoArgsConstructor
    public static class MemberProfilePutRequestDto {
        private Long id;
        String name;
        String message;
    }


    @Data
    @NotNull
    @NoArgsConstructor
    public static class MemberEmailPasswordPutRequestDto {
        private Long id;
        String email;
    }


    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberResponse {
        private Long id;
        private String name;
        private String password;
        private String email;
        private String imageUrl;
        private String message;
        private String token;

        public MemberResponse(Member member) {
            this(member.getId(), member.getName(), member.getPassword(), member.getEmail(), member.getImageUrl(), member.getStatusMessage(), member.getToken());
        }

        public MemberResponse(Long memberId, String name, String password, String email, String imageUrl, String message, String token) {
            this.id = memberId;
            this.name = name;
            this.password = password;
            this.email = email;
            this.imageUrl = imageUrl;
            this.message = message;
            this.token = token;
        }

    }


}

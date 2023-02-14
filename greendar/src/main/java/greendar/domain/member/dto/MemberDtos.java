package greendar.domain.member.dto;


import greendar.domain.member.domain.Member;
import lombok.*;



public class MemberDtos {

    @Data
    @NoArgsConstructor
    public static class MemberPostRequestDto
    {
        String name;
        String password;
        String email;
        String imageUrl;
        String message;
        String firebaseToken;
    }

    @Data
    @NoArgsConstructor
    public static class MemberEmailGetRequestDto
    {
        private String email;
    }



    @Data
    @NoArgsConstructor
    public static class MemberGetRequestDto
    {
        private Long id;
    }



    @Data
    @NoArgsConstructor
    public static class MemberProfilePutRequestDto
    {
        private Long id;
        String name;
        String message;
    }


    @Data
    @NoArgsConstructor
    public static class MemberEmailPasswordPutRequestDto
    {
        private Long id;
        String email;
    }


    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberResponse{
        private Long id;
        private String name;
        private String password;
        private String email;
        private String imageUrl;
        private String message;
        private String token;

        public MemberResponse(Member member){
            this(member.getId(), member.getName(),member.getPassword(),member.getEmail(),member.getImageUrl(),member.getStatusMessage(), member.getToken());
        }
        public MemberResponse(Long memberId, String name, String password, String email, String imageUrl,String message, String token) {
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

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
        private Long memberId;
    }



    @Data
    @NoArgsConstructor
    public static class MemberProfilePutRequestDto
    {
        private Long memberId;
        String name;
        String message;
    }


    @Data
    @NoArgsConstructor
    public static class MemberEmailPasswordPutRequestDto
    {
        private Long memberId;
        String email;
    }


    @Data
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberResponse{
        private Long memberId;
        private String name;
        private String password;
        private String email;
        private String imageUrl;
        private String message;

        public MemberResponse(Member member){
            this(member.getMemberId(), member.getName(),member.getPassword(),member.getEmail(),member.getImageUrl(),member.getMessage());
        }
        public MemberResponse(Long memberId, String name, String password, String email, String imageUrl,String message) {
            this.memberId = memberId;
            this.name = name;
            this.password = password;
            this.email = email;
            this.imageUrl = imageUrl;
            this.message = message;
        }

    }



}

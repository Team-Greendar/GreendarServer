package greendar.domain.member.api;

import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;


import greendar.domain.member.dto.MemberDtos.MemberPostRequestDto;
import greendar.domain.member.dto.MemberDtos.MemberProfilePutRequestDto;
import greendar.domain.member.dto.MemberDtos.MemberEmailPasswordPutRequestDto;
import greendar.domain.member.dto.MemberDtos.MemberResponse;
import greendar.global.common.ApiResponse;
import greendar.infra.gcp.storage.application.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;
    private final FileService fileService;

    @GetMapping(value = "/list",produces = "application/json;charset=UTF-8")
    public ApiResponse getMembers() {
        List<Member> findMembers = memberService.getAll();
        List<MemberResponse> response = findMembers.stream().map(MemberResponse::new).collect(Collectors.toList());
        return ApiResponse.success(response);
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse postMember(@RequestBody MemberPostRequestDto request) {
        if(memberService.isNameRedundant(request.getName())){
            return ApiResponse.redundantName(false);
        }
        Member savedMember = memberService.saveMember(request.getName(), request.getPassword(), request.getEmail(), "EMPTY", "HELLO", request.getFirebaseToken());
        return ApiResponse.success(new MemberResponse(savedMember));
    }

    @PostMapping(value = "/validity",produces = "application/json;charset=UTF-8")
    public ApiResponse checkMemberVaild(@RequestHeader("Authorization") String firebaseToken) {
        if(memberService.isTokenExists(firebaseToken)){
            return ApiResponse.validToken(true);
        }
        return ApiResponse.invaildToken(false);
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse getMember(@RequestHeader("Authorization") String firebaseToken) {
        Member member = memberService.findOneByToken(firebaseToken);
        return ApiResponse.success(member);
    }

    @PutMapping(value = "/profile/name-message", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberProfile(@RequestHeader("Authorization") String firebaseToken,
                                        @RequestBody MemberProfilePutRequestDto request) {
        Member updatedProfile = memberService.updateProfile(firebaseToken, request.getName(), request.getMessage());
        return ApiResponse.success(updatedProfile);
    }

    @PutMapping(value = "/profile/email", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberEmailPassword(@RequestHeader("Authorization") String firebaseToken,
                                              @RequestBody MemberEmailPasswordPutRequestDto request) {
        Member updatedEmail = memberService.updateEmail(firebaseToken, request.getEmail());
        return ApiResponse.success(updatedEmail);
    }

    @PutMapping(value = "/profile/image", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberImage(@RequestHeader("Authorization") String firebaseToken,
                                      @RequestParam("file") MultipartFile file) {
        String imageUrl = fileService.uploadFile(file).getFileUrl();
        Member result = memberService.updateImageUrl(firebaseToken, imageUrl);
        return ApiResponse.success(result);
    }

}

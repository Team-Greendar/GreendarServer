package greendar.domain.member.api;

import com.google.protobuf.Api;
import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;

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
        Member savedMember = memberService.saveMember(request.getName(), request.getPassword(), request.getEmail(), request.getImageUrl(), request.getMessage());
        return ApiResponse.success(new MemberResponse(savedMember));
    }

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse getMember(@RequestHeader("Authorization") Long memberId) {
        Member member = memberService.findOne(memberId);
        return ApiResponse.success(member);
    }

    @PutMapping(value = "/profile/name-message", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberProfile(@RequestBody MemberProfilePutRequestDto request) {
        Member updatedProfile = memberService.updateProfile(request.getId(), request.getName(), request.getMessage());
        return ApiResponse.success(updatedProfile);
    }

    @PutMapping(value = "/profile/email", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberEmailPassword(@RequestBody MemberEmailPasswordPutRequestDto request) {
        Member updatedEmail = memberService.updateEmail(request.getId(), request.getEmail());
        return ApiResponse.success(updatedEmail);
    }

    @PutMapping(value = "/profile/image", produces = "application/json;charset=UTF-8")
    public ApiResponse putMemberImage(@RequestParam("pid") Long memberId,
                                      @RequestParam("file") MultipartFile file) {
        String imageUrl = fileService.uploadFile(file).getFileUrl();
        Member result = memberService.updateImageUrl(memberId, imageUrl);
        return ApiResponse.success(result);
    }

}

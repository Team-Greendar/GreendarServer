//package greendar.domain.member.api;
//import greendar.domain.member.application.MemberService;
//import greendar.domain.member.domain.Member;
//import java.util.List;
//import java.util.stream.Collectors;
//import javax.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class MemberApi {
//
//    private final MemberService memberService;
//
//    @GetMapping(value = "/member", produces = "application/json;charset=UTF-8")
//    public List<MemberDto> getMembers(){
//        List<Member> findHellos =  memberService.getAll();
//        List<MemberDto> helloDtos = findHellos.stream().map(member->new MemberDto(member)).collect(Collectors.toList());
//        return helloDtos;
//    }
//
//    @PostMapping(value="/member" , produces = "application/json;charset=UTF-8")
//    public boolean postMember(@Valid @RequestBody HelloDtos.HelloPostRequest request){
//        return  memberService.post(request.getName());
//    }
//    @GetMapping(value = "/api/member/{id}", produces = "application/json;charset=UTF-8")
//    public MemberDto getHello(@PathVariable("id") Long id){
//        Member result = memberService.findOne(id);
//        return new MemberDto(result);
//    }
//
//
//
//}

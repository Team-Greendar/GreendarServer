package greendar.domain.privatetodo.api;

import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.domain.PrivateTodo;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoRequestDto;
import greendar.global.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrivateTodoApi {

    private final MemberService memberService;
    private final PrivateTodoService privateTodoService;

    @PostMapping(value = "/private/todo")
    public ApiResponse addPrivateTodo(@RequestHeader("Authorization") String member_token,
                                      @RequestBody PrivateTodoRequestDto request){

        Member member = memberService.findOne();

        PrivateTodo privateTodo = privateTodoService.saveTodo(member,request.getTask(),request.getDate());

        return  ApiResponse.success("",privateTodo);
    }




}

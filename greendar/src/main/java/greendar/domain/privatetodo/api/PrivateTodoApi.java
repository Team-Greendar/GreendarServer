package greendar.domain.privatetodo.api;

import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.dao.DailyAchievementRateDao;
import greendar.domain.privatetodo.domain.PrivateTodo;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoPostRequestDto;
import greendar.global.common.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PrivateTodoApi {

    private final MemberService memberService;
    private final PrivateTodoService privateTodoService;

    @GetMapping(value = "/private/todo")
    public ApiResponse getPrivateTodoByMember(@RequestHeader("Authorization") Long member_token) {
        Member member = memberService.findOne(member_token);
        List<PrivateTodo> result = privateTodoService.getAllPrivateTodoByMember(member);
        return ApiResponse.success("ok",result);
    }

    @PostMapping(value = "/private/todo")
    public ApiResponse addPrivateTodo(@RequestHeader("Authorization") Long member_token,
                                      @RequestBody PrivateTodoPostRequestDto request) {

        Member member = memberService.findOne(member_token);

        PrivateTodo privateTodo = privateTodoService.saveTodo(member,request.getTask(),request.getDate());

        return  ApiResponse.success("ok",privateTodo);
    }
    @PutMapping(value = "/private/todo/image")
    public ApiResponse setPrivateTodoImageUrl(@RequestHeader("Authorization") Long member_token,
                                      @RequestBody PrivateTodoPostRequestDto request) {

        Member member = memberService.findOne(member_token);

        PrivateTodo privateTodo = privateTodoService.saveTodo(member,request.getTask(),request.getDate());

        return  ApiResponse.success("ok",privateTodo);
    }

    // need Refactoring
    @GetMapping(value = "/private/todo/{date}")
    public ApiResponse getPrivateTodoByDate(@RequestHeader("Authorization") Long member_token,
                                            @PathVariable LocalDate date) {
        Member member = memberService.findOne(member_token);
        List<PrivateTodo> result =  privateTodoService.getAllPrivateTodoByOneDay(date,member);
        return ApiResponse.success("ok",result);
    }

    @GetMapping(value = "/private/todo/monthly/{date}")
    public ApiResponse getPrivateTodoByMonthlyDate(@RequestHeader("Authorization") Long member_token,
                                            @PathVariable LocalDate date) {
        Member member = memberService.findOne(member_token);
        List<PrivateTodo> result =  privateTodoService.getAllPrivateTodoByOneMonth(date,member);
        return ApiResponse.success("ok",result);
    }

    @GetMapping(value = "/private/todo/monthly/ratio/{date}")
    public ApiResponse getPrivateTodoRatioByDate(@RequestHeader("Authorization") Long member_token,
                                            @PathVariable LocalDate date) {
        Member member = memberService.findOne(member_token);
        List<DailyAchievementRateDao> result =  privateTodoService.getRatioByDailyInMonth(date,member);
        return ApiResponse.success("ok",result);
    }

}

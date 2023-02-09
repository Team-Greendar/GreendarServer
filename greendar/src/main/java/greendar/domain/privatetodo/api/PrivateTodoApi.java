package greendar.domain.privatetodo.api;

import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.dao.DailyAchievementRateDao;
import greendar.domain.privatetodo.domain.PrivateTodo;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoImagePutRequestDto;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoPostRequestDto;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoResponse;
import greendar.global.common.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
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
        List<PrivateTodo> privateTodos= privateTodoService.getAllPrivateTodoByMember(member);
        List<PrivateTodoResponse> collect = privateTodos.stream()
                .map(r->new PrivateTodoResponse(r))
                .collect(Collectors.toList());
        return ApiResponse.success(collect);
    }

    @PostMapping(value = "/private/todo")
    public ApiResponse addPrivateTodo(@RequestHeader("Authorization") Long member_token,
                                      @RequestBody PrivateTodoPostRequestDto request) {

        Member member = memberService.findOne(member_token);

        PrivateTodo privateTodo = privateTodoService.saveTodo(member,request.getTask(),request.getDate());

        return  ApiResponse.success( new PrivateTodoResponse(privateTodo));
    }
    //get privateTodo Id and  set image

//    public ApiResponse setPrivateTodoImageUrl(@RequestHeader("Authorization") Long member_token,
//                                      @RequestBody PrivateTodoImagePutRequestDto request) {
//        Member member = memberService.findOne(member_token);
//        PrivateTodo result = privateTodoService.saveTodo(member,request.getTask(),request.getDate());
//        return  ApiResponse.success(result);
//    }

    @GetMapping(value = "/private/todo/{date}")
    public ApiResponse getPrivateTodoByDate(@RequestHeader("Authorization") Long member_token,
                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate date) {
        Member member = memberService.findOne(member_token);
        List<PrivateTodo> result =  privateTodoService.getAllPrivateTodoByOneDay(date,member);
        List<PrivateTodoResponse> collect = result.stream()
                .map(r->new PrivateTodoResponse(r))
                .collect(Collectors.toList());
        if(collect.isEmpty()) return ApiResponse.success("None");
        return ApiResponse.success(collect);
    }

    @GetMapping(value = "/private/todo/monthly/{date}")
    public ApiResponse getPrivateTodoByMonthlyDate(@RequestHeader("Authorization") Long member_token,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        System.out.println(date);
        Member member = memberService.findOne(member_token);
        List<PrivateTodo> result =  privateTodoService.getAllPrivateTodoByOneMonth(date,member);
        System.out.println(result);
        List<PrivateTodoResponse> collect = result.stream()
                .map(r->new PrivateTodoResponse(r))
                .collect(Collectors.toList());
        return ApiResponse.success(collect);
    }

    @GetMapping(value = "/private/todo/monthly/ratio/{date}")
    public ApiResponse getPrivateTodoRatioByDate(@RequestHeader("Authorization") Long member_token,
                                                 @PathVariable LocalDate date) {
        Member member = memberService.findOne(member_token);
        List<DailyAchievementRateDao> result =  privateTodoService.getRatioByDailyInMonth(date,member);
        return ApiResponse.success(result);
    }

}

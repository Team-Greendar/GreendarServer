package greendar.domain.eventtodo.api;

import greendar.domain.eventtodo.application.EventTodoService;
import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.global.common.ApiResponse;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController("/event/todo")
@RequiredArgsConstructor
public class EventTodoApi {
    private final MemberService memberService;
    private final EventTodoService eventTodoService;

    @GetMapping(value = "/{date}")
    public ApiResponse getPrivateTodoByDate(@RequestHeader("Authorization") String firebaseToken,
                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        return ApiResponse.success(eventTodoService.getAllEventTodoByOneDay(date,member));
    }
    @GetMapping(value = "/monthly/{date}")
    public ApiResponse getEventTodoByMonthlyDate(@RequestHeader("Authorization") String firebaseToken,
                                                 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        return ApiResponse.success(eventTodoService.getAllEventTodoByOneMonth(date,member));
    }
    @GetMapping(value = "/monthly/ratio/{date}")
    public ApiResponse getEventTodoRatioByDate(@RequestHeader("Authorization") String firebaseToken,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        return ApiResponse.success(eventTodoService.getRatioByDailyInMonth(date,member));
    }

}
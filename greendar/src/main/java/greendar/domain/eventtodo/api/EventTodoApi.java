package greendar.domain.eventtodo.api;

import greendar.domain.eventtodo.application.EventTodoService;
import greendar.domain.eventtodo.dto.EventTodoDtos.EventTodoCompleteUpdateRequestDto;
import greendar.domain.eventtodo.dto.EventTodoDtos.EventTodoImageUpdateRequestDto;
import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievementRatio;
import greendar.global.common.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event/todo")
@RequiredArgsConstructor
public class EventTodoApi {
    private final MemberService memberService;
    private final EventTodoService eventTodoService;

    @PutMapping(value = "/image")
    public ApiResponse updateEventTodoImageByItemId(@RequestHeader("Authorization") String firebaseToken,
                                              @RequestBody EventTodoImageUpdateRequestDto request) {
        return  ApiResponse.success(eventTodoService.updateEventTodo(null, request.getImageUrl(),
                request.getEventTodoItemId(), firebaseToken));
    }
    @PutMapping(value = "/complete")
    public ApiResponse updateEventTodoCompleteByItemId(@RequestHeader("Authorization") String firebaseToken,
                                                    @RequestBody EventTodoCompleteUpdateRequestDto request) {
        return  ApiResponse.success(eventTodoService.updateEventTodo(request.getComplete(),null, request.getEventTodoItemId(),firebaseToken));
    }

    @GetMapping(value = "/{date}")
    public ApiResponse getEventTodoByDate(@RequestHeader("Authorization") String firebaseToken,
                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        return ApiResponse.success(eventTodoService.getAllEventTodoByOneDay(date,member));
    }
    @GetMapping(value = "/monthly/{date}")
    public ApiResponse getEventTodoByMonthlyDate(@RequestHeader("Authorization") String firebaseToken,
                                                 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        if(member == null) return ApiResponse.fail("NoMember");
        return ApiResponse.success(eventTodoService.getAllEventTodoByOneMonth(date,member));
    }
    @GetMapping(value = "/monthly/ratio/{date}")
    public ApiResponse getEventTodoRatioByDate(@RequestHeader("Authorization") String firebaseToken,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        Member member = memberService.findOneByToken(firebaseToken);

        TreeMap<LocalDate, Float> result =eventTodoService.getRatioByDailyInMonth(date,member);
        System.out.println("!?--!?");
        System.out.println(result);
        System.out.println("!?--!?");
        List<DailyAchievementRatio> dailyAchievementRatios = result.entrySet()
                        .stream()
                        .map(DailyAchievementRatio::new)
                        .collect(Collectors.toList());
        return ApiResponse.success(dailyAchievementRatios);
    }

}
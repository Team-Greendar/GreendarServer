package greendar.domain.eventtodo.api;

import greendar.domain.eventtodo.application.EventTodoService;
import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodo.dto.EventTodoDtos.EventTodoCompleteUpdateRequestDto;
import greendar.domain.eventtodo.dto.EventTodoDtos.MonthlyAchievementRatio;
import greendar.domain.eventtodo.dto.EventTodoResponseDto;
import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.domain.PrivateTodo;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievementRatio;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoResponse;
import greendar.global.common.ApiResponse;
import greendar.infra.gcp.storage.application.FileService;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/event/todo")
@RequiredArgsConstructor
public class EventTodoApi {
    private final MemberService memberService;
    private final EventTodoService eventTodoService;
    private final FileService fileService;
    @DeleteMapping(value = "/image")
    public ApiResponse setTempoaryEventTodoImageUrl(@RequestHeader("Authorization") String firebaseToken,
                                                    @RequestParam("eventTodoItemId") Long eventTodoItemId) {
        memberService.findOneByToken(firebaseToken);
        EventTodo eventTodo =eventTodoService.updateEventTodo(null,"EMPTY",eventTodoItemId,firebaseToken);
        if(eventTodo == null) return  ApiResponse.fail(false);
        return ApiResponse.success(true);
    }
    @PutMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse updateEventTodoImageUrlByItemId(@RequestHeader("Authorization") String firebaseToken,
                                                       @RequestParam("eventTodoItemId") Long eventTodoItemId,
                                                       @RequestParam("file") MultipartFile file) {
        memberService.findOneByToken(firebaseToken);
        String imageUrl = fileService.uploadFile(file).getFileUrl();
        EventTodoResponseDto result = new EventTodoResponseDto(eventTodoService.updateEventTodo(null, imageUrl,
                eventTodoItemId, firebaseToken));
        return  ApiResponse.success(result);
    }
    @PutMapping(value = "/complete")
    public ApiResponse updateEventTodoCompleteByItemId(@RequestHeader("Authorization") String firebaseToken,
                                                    @RequestBody EventTodoCompleteUpdateRequestDto request) {
        EventTodoResponseDto result = new EventTodoResponseDto(eventTodoService.updateEventTodo(request.getComplete(),null, request.getEventTodoItemId(),firebaseToken));
        return  ApiResponse.success(result);
    }

    @GetMapping(value = "/{date}")
    public ApiResponse getEventTodoByDate(@RequestHeader("Authorization") String firebaseToken,
                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        if(member == null) return ApiResponse.fail("NoMember");
        return ApiResponse.success(eventTodoService.getAllEventTodoByOneDay(date,member));
    }
    @GetMapping(value = "/monthly/{date}")
    public ApiResponse getEventTodoByMonthlyDate(@RequestHeader("Authorization") String firebaseToken,
                                                 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        if(member == null) return ApiResponse.fail("NoMember");
        return ApiResponse.success(eventTodoService.getAllEventTodoByOneMonth(date,member));
    }
    @GetMapping(value = "/monthly/daily/ratio/{date}")
    public ApiResponse getEventTodoDailyRatioByDate(@RequestHeader("Authorization") String firebaseToken,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        Member member = memberService.findOneByToken(firebaseToken);
        TreeMap<LocalDate, Float> result =eventTodoService.getDailyRatioByMonth(date,member);
        List<DailyAchievementRatio> dailyAchievementRatios = result.entrySet()
                        .stream()
                        .map(DailyAchievementRatio::new)
                        .collect(Collectors.toList());
        return ApiResponse.success(dailyAchievementRatios);
    }
    @GetMapping(value = "/monthly/ratio/{date}")
    public ApiResponse getEventTodoRatioByDate(@RequestHeader("Authorization") String firebaseToken,
                                                    @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        Member member = memberService.findOneByToken(firebaseToken);
        double result = eventTodoService.getMonthlyRatio(date,member);
        return ApiResponse.success(new MonthlyAchievementRatio(date,Math.round(result*100)/100.0));
    }

}
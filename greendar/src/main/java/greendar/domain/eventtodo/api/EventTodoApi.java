package greendar.domain.eventtodo.api;

import greendar.domain.eventtodo.application.EventTodoService;
import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.eventtodo.dto.EventTodoDtos.EventTodoCompleteUpdateRequestDto;
import greendar.domain.eventtodo.dto.EventTodoResponseDto;
import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievementRatio;
import greendar.global.common.ApiResponse;
import greendar.infra.gcp.storage.application.FileService;
import java.time.LocalDate;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
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

    @PutMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse updateEventTodoImageUrlByItemId(@RequestHeader("Authorization") String firebaseToken,
                                                    @RequestParam("event_todo_id") Long event_todo_id,
                                                    @RequestParam("file") MultipartFile file) {
        Member member = memberService.findOneByToken(firebaseToken);
        String imageUrl = fileService.uploadFile(file).getFileUrl();
        EventTodoResponseDto result = new EventTodoResponseDto(eventTodoService.updateEventTodo(null, imageUrl,
                event_todo_id, firebaseToken));
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
    @GetMapping(value = "/monthly/ratio/{date}")
    public ApiResponse getEventTodoRatioByDate(@RequestHeader("Authorization") String firebaseToken,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        Member member = memberService.findOneByToken(firebaseToken);
        if(member == null) return ApiResponse.fail("NoMember");
        TreeMap<LocalDate, Float> result =eventTodoService.getRatioByDailyInMonth(date,member);
        List<DailyAchievementRatio> dailyAchievementRatios = result.entrySet()
                        .stream()
                        .map(DailyAchievementRatio::new)
                        .collect(Collectors.toList());
        return ApiResponse.success(dailyAchievementRatios);
    }

}
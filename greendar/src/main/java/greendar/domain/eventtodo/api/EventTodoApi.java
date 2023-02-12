package greendar.domain.eventtodo.api;

import greendar.domain.eventtodo.application.EventTodoService;
import greendar.domain.eventtodo.domain.EventTodo;
import greendar.domain.member.application.MemberService;
import greendar.global.common.ApiResponse;
import java.time.LocalDate;
import java.util.List;
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

    @GetMapping(value = "/private/todo/{date}")
    public ApiResponse getPrivateTodoByDate(@RequestHeader("Authorization") Long token,
                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
//        Member member = memberService.findOne(member_token);
        List<EventTodo> result =eventTodoService.getAllEventTodoByOneDay(date,member);

    }


}

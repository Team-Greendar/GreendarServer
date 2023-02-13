package greendar.domain.eventtodoitem.api;

import greendar.domain.eventtodoitem.application.EventTodoItemService;
import greendar.domain.eventtodoitem.domain.EventTodoItem;
import greendar.domain.eventtodoitem.dto.EventTodoItemDtos.EventTodoPostItemRequestDto;
import greendar.global.common.ApiResponse;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event/todo/item")
@RequiredArgsConstructor
public class EventTodoItemApi {

    private final EventTodoItemService eventTodoItemService;

    @GetMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse getEvenTodoItems(){
        List<EventTodoItem> eventTodoItems = eventTodoItemService.findAll();
        if(eventTodoItems.isEmpty()) return ApiResponse.error(404,"No Reuslt");
        return ApiResponse.success(eventTodoItems);
    }
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ApiResponse addEventTodo(@RequestBody EventTodoPostItemRequestDto request) {
        eventTodoItemService.saveTodo(request.getTask(),request.getDate());
        return ApiResponse.success("ok");
    }
    @GetMapping(value = "/monthly/{date}",produces = "application/json;charset=UTF-8")
    public ApiResponse getEventTodoItemByMonthlyDate(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date){
        return ApiResponse.success(eventTodoItemService.findAllByMonth(date));
    }

}

package greendar.domain.privatetodo.api;

import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.dao.DailyAchievementRateDao.DailyAchievementRatio;
import greendar.domain.privatetodo.domain.PrivateTodo;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoCompletePutRequestDto;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoPostRequestDto;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoResponse;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoTaskPutRequestDto;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PrivateTodoApi {

    private final MemberService memberService;
    private final PrivateTodoService privateTodoService;
    private final FileService fileService;

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
                                                 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOne(member_token);
        TreeMap<LocalDate, Float> result =  privateTodoService.getRatioByDailyInMonth(date,member);
        List<DailyAchievementRatio> dailyAchievementRatios = result.entrySet().stream().map(e->new DailyAchievementRatio(e)).collect(Collectors.toList());
        return ApiResponse.success(dailyAchievementRatios);
    }

    @PutMapping(value = "/private/todo/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse setPrivateTodoImageUrl(@RequestHeader("Authorization") Long member_token,
                                              @RequestParam("pid") Long private_todo_id,
                                              @RequestParam("file") MultipartFile file) {
        Member member = memberService.findOne(member_token);
        String imageUrl = fileService.uploadFile(file).getFileUrl();
        PrivateTodo result = privateTodoService.updatePrivateTodoImageUrl(private_todo_id,imageUrl);
        return  ApiResponse.success(new PrivateTodoResponse(result));
    }
    @PutMapping(value = "/private/todo/complete")
    public ApiResponse setPrivateTodoComplete(@RequestHeader("Authorization") Long member_token,
                                              @RequestBody PrivateTodoCompletePutRequestDto request) {
        Member member = memberService.findOne(member_token);
        PrivateTodo result = privateTodoService.updatePrivateTodoComplete(request.getPrivate_todo_id(),request.getComplete());
        return  ApiResponse.success(new PrivateTodoResponse(result));
    }

    @PutMapping(value = "/private/todo/task")
    public ApiResponse setPrivateTodoTask(@RequestHeader("Authorization") Long member_token,
                                          @RequestBody PrivateTodoTaskPutRequestDto request) {
        Member member = memberService.findOne(member_token);
        PrivateTodo result = privateTodoService.updatePrivateTodoTask(request.getPrivate_todo_id(),request.getTask());
        return  ApiResponse.success(new PrivateTodoResponse(result));
    }

}

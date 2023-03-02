package greendar.domain.privatetodo.api;

import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.DailyAchievementRatio;
import greendar.domain.privatetodo.domain.PrivateTodo;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoCompletePutRequestDto;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoPostRequestDto;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoResponse;
import greendar.domain.privatetodo.dto.PrivateTodoDtos.PrivateTodoTaskPutRequestDto;
import greendar.global.common.ApiResponse;
import greendar.infra.gcp.storage.application.FileService;
import java.time.LocalDate;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/private/todo")
@RequiredArgsConstructor
public class PrivateTodoApi {

    private final MemberService memberService;
    private final PrivateTodoService privateTodoService;
    private final FileService fileService;

    @GetMapping
    public ApiResponse getPrivateTodoByMember(@RequestHeader("Authorization") String firebaseToken) {
        Member member = memberService.findOneByToken(firebaseToken);
        List<PrivateTodo> privateTodos= privateTodoService.getAllPrivateTodoByMember(member);
        List<PrivateTodoResponse> collect = privateTodos.stream()
                .map(r->new PrivateTodoResponse(r))
                .collect(Collectors.toList());
        return ApiResponse.success(collect);
    }
    @DeleteMapping("/{private_todo_id}")
    public ApiResponse deletePrivateTodoByMember(@RequestHeader("Authorization") String firebaseToken, @PathVariable Long private_todo_id) {
        Member member = memberService.findOneByToken(firebaseToken);
        privateTodoService.deleteTodo(private_todo_id);
        return ApiResponse.success(true);
    }
    @PostMapping
    public ApiResponse addPrivateTodo(@RequestHeader("Authorization") String firebaseToken,
                                      @Valid @RequestBody PrivateTodoPostRequestDto request) {
        Member member = memberService.findOneByToken(firebaseToken);
        PrivateTodo privateTodo = privateTodoService.saveTodo(member,request.getTask(),request.getDate());
        return  ApiResponse.success( new PrivateTodoResponse(privateTodo));
    }

    @GetMapping(value = "/{date}")
    public ApiResponse getPrivateTodoByDate(@RequestHeader("Authorization") String firebaseToken,
                                            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        List<PrivateTodo> result =  privateTodoService.getAllPrivateTodoByOneDay(date,member);
        List<PrivateTodoResponse> collect = result.stream()
                .map(r->new PrivateTodoResponse(r))
                .collect(Collectors.toList());
        List<String> empty = new ArrayList<>();
        if(collect.isEmpty()) return ApiResponse.success(empty);
        return ApiResponse.success(collect);
    }

    @GetMapping(value = "/monthly/{date}")
    public ApiResponse getPrivateTodoByMonthlyDate(@RequestHeader("Authorization") String firebaseToken,
                                                   @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        List<PrivateTodo> result =  privateTodoService.getAllPrivateTodoByOneMonth(date,member);
        List<PrivateTodoResponse> collect = result.stream()
                .map(r->new PrivateTodoResponse(r))
                .collect(Collectors.toList());
        return ApiResponse.success(collect);
    }

    @GetMapping(value = "/monthly/ratio/{date}")
    public ApiResponse getPrivateTodoRatioByDate(@RequestHeader("Authorization") String firebaseToken,
                                                 @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        Member member = memberService.findOneByToken(firebaseToken);
        TreeMap<LocalDate, Float> result =  privateTodoService.getRatioByDailyInMonth(date,member);
        List<DailyAchievementRatio> dailyAchievementRatios = result.entrySet().stream().map(e->new DailyAchievementRatio(e)).collect(Collectors.toList());
        return ApiResponse.success(dailyAchievementRatios);
    }

    @PutMapping(value = "/image",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse updatePrivateTodoImageUrl(@RequestHeader("Authorization") String firebaseToken,
                                              @RequestParam("private_todo_id") Long private_todo_id,
                                              @RequestParam("file") MultipartFile file) {
        Member member = memberService.findOneByToken(firebaseToken);
//        List<InputFile> inputFiles = fileService.uploadFiles(files);
        String imageUrl = fileService.uploadFile(file).getFileUrl();
        PrivateTodo result = privateTodoService.updatePrivateTodoImageUrl(private_todo_id,imageUrl);
        return  ApiResponse.success(new PrivateTodoResponse(result));
    }

    @DeleteMapping (value = "/image")
    public ApiResponse tempoaryPrivateTodoImageUrl(@RequestHeader("Authorization") String firebaseToken,
                                                 @RequestParam("private_todo_id") Long private_todo_id) {
        Member member = memberService.findOneByToken(firebaseToken);
//        List<InputFile> inputFiles = fileService.uploadFiles(files);
        PrivateTodo result = privateTodoService.updatePrivateTodoImageUrl(private_todo_id,"EMPTY");
        return  ApiResponse.success(new PrivateTodoResponse(result));
    }

    @PutMapping(value = "/complete")
    public ApiResponse setPrivateTodoComplete(@RequestHeader("Authorization") String firebaseToken,
                                              @Valid @RequestBody PrivateTodoCompletePutRequestDto request) {
        Member member = memberService.findOneByToken(firebaseToken);
        if(member == null) return ApiResponse.fail("Wrong FireBaseToken");
        PrivateTodo result = privateTodoService.updatePrivateTodoComplete(request.getPrivate_todo_id(),request.getComplete());
        return  ApiResponse.success(new PrivateTodoResponse(result));
    }

    @PutMapping(value = "/task")
    public ApiResponse setPrivateTodoTask(@RequestHeader("Authorization") String firebaseToken,
                                          @Valid @RequestBody PrivateTodoTaskPutRequestDto request) {
        Member member = memberService.findOneByToken(firebaseToken);
        if(member == null) return ApiResponse.fail("Wrong FireBaseToken");
        PrivateTodo result = privateTodoService.updatePrivateTodoTask(request.getPrivate_todo_id(),request.getTask());
        return  ApiResponse.success(new PrivateTodoResponse(result));
    }

}

package greendar.domain.hello.api;

import greendar.domain.hello.application.HelloService;
import greendar.domain.hello.domain.Hello;
import greendar.domain.hello.dto.HelloDtos.HelloPostRequest;
import greendar.domain.hello.dto.HelloDtos.HelloDto;

import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloApi {

    private final HelloService helloService;

    @GetMapping(value = "/api/hello", produces = "application/json;charset=UTF-8")
    public List<HelloDto> getHellos(){
        List<Hello> findHellos =  helloService.getAll();
        List<HelloDto> helloDtos = findHellos.stream().map(hello->new HelloDto(hello)).collect(Collectors.toList());
        return helloDtos;
    }

    @PostMapping(value="/api/hello" , produces = "application/json;charset=UTF-8")
    public boolean postHello(@Valid @RequestBody HelloPostRequest request){
        return  helloService.postHello(request.getName());
    }
    @GetMapping(value = "/api/hello/{id}", produces = "application/json;charset=UTF-8")
    public HelloDto getHello(@PathVariable("id") Long id){
        Hello result = helloService.getNameById(id);
        return new HelloDto(result);
    }
}


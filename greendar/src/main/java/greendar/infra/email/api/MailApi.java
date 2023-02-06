package greendar.infra.email.api;

import greendar.infra.email.application.MailService;
import greendar.infra.email.dto.MailDtos.MailResultResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@EnableAsync
public class MailApi
{
    private final MailService mailService;
    @GetMapping(value="/member/email" , produces = "application/json;charset=UTF-8")
    public MailResultResponse mailPost(@Param("memberEmail") String memberEmail)
    {

        if(! mailService.findMemberByEmail(memberEmail)) {   Map<String, String> data = new HashMap<>();
            data.put("code","0");
            return new MailResultResponse("이미존재하는 이메일입니다",400,data);
        }

        String randomcode = mailService.generateRandomCode();
        Map<String, String> data = new HashMap<>();
        data.put("code",randomcode);
        mailService.sendEmail(memberEmail,randomcode);
        return new MailResultResponse("가입 가능한 이메일 입니다",200,data);
    }


}


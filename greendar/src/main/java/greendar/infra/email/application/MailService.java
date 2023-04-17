package greendar.infra.email.application;

import greendar.domain.member.dao.MemberRepository;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MailService
{
    private JavaMailSender javaMailSender;

    private final MemberRepository memberRepository;

    @Async
    public void sendEmail(String adddress,String code)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(adddress);
        message.setSubject("회원가입 인증 번호입니다");
        message.setText(code);
        javaMailSender.send(message);
    }

    public String generateRandomCode(){
        long seed = System.currentTimeMillis();
        Random rand = new Random(seed);
        return Integer.toString(rand.nextInt(888888)+111111);
    }
    public boolean findMemberByEmail(String userEmail){
        return memberRepository.findOneByEmail(userEmail).isPresent();
    }

}

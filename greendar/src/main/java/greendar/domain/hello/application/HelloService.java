package greendar.domain.hello.application;

import greendar.domain.hello.dao.HelloRepository;
import greendar.domain.hello.domain.Hello;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly=true)
@Service
@RequiredArgsConstructor
public class HelloService {
    private final HelloRepository helloRepository;

    public Hello getNameById(Long id) {
        return helloRepository.findOne(id);
    }

    public List<Hello> getAll() {
        return helloRepository.findAll();
    }
    @Transactional
    public boolean postHello(String name){
        try{
        helloRepository.post(name);
        return true;
        }catch (Exception e){
            return false;
        }
    }

}

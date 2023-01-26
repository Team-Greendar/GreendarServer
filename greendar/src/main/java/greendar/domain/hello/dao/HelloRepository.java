package greendar.domain.hello.dao;

import greendar.domain.hello.domain.Hello;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HelloRepository {
    @Autowired
    private final EntityManager em;

    public Hello findOne(Long id) {
        return em.find(Hello.class,id);
    }

    public List<Hello> findAll() {
        return em.createQuery("select h from Hello h",Hello.class)
                .getResultList();
    }
    public void post(String name) {
        Hello hello = new Hello();
        hello.setName(name);
        em.persist(hello);
    }

}

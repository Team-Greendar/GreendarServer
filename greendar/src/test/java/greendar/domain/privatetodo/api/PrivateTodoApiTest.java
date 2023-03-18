package greendar.domain.privatetodo.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import greendar.domain.member.application.MemberService;
import greendar.domain.member.domain.Member;
import greendar.domain.privatetodo.application.PrivateTodoService;
import greendar.domain.privatetodo.domain.PrivateTodo;
import java.net.http.HttpHeaders;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class PrivateTodoApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private PrivateTodoService privateTodoService;

    @Test
    public void testGetPrivateTodoByNewMember() throws Exception {
        memberService.saveMember("cho",null,null,null,null,"testCodeToken");

        MvcResult result = mockMvc.perform(get("/private/todo")
                .header("Authorization", "tokenCodeToken"))
        .andExpect(status().isOk())
        .andReturn();

//        MvcResult result = mockMvc.perform(get("/private/todo")
//                        .header("Authorization", "token"))
//                .andExpect(status().isOk())
//                .andReturn();
    }


}
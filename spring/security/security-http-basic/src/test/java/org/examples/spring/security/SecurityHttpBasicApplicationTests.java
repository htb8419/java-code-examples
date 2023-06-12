package org.examples.spring.security;

import org.examples.spring.security.api.UserController;
import org.hamcrest.text.IsEqualIgnoringCase;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

//@SpringBootTest
//@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@PropertySource({"classpath:application.yml"})
//@ContextConfiguration(classes = {ApplicationContextTest.class, PersistenceContextTest.class})
//@SetEnvironmentVariable(key = "spring_main_allow-bean-definition-overriding", value = "true")
@WebMvcTest(controllers = UserController.class)
public class SecurityHttpBasicApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityHttpBasicApplicationTests.class);
    @Autowired
    MockMvc mockMvc;

    @Test
    public void testUploadFile() throws Exception {
        //given
        String username = "demo";
        String password = "demo";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(username, password);
        //when
        this.mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/user"))
                        .headers(httpHeaders))
                //then
                .andDo(result -> LOGGER.info("server response >> {}  ", result.getResponse().getContentAsString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(IsEqualIgnoringCase.equalToIgnoringCase(username)));
    }

}

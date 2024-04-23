package org.examples.spring.security;

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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@ActiveProfiles("test")
@PropertySource({"classpath:application.yml"})
@WebMvcTest
public class HttpBasicSecurityTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpBasicSecurityTest.class);
    @Autowired
    MockMvc mockMvc;

    @Test
    public void succeedAuthentication() throws Exception {
        //given
        String username = "demo";
        String password = "demo";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(username, password);
        //when
        final ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/user"))
                        .headers(httpHeaders))
                .andDo(result -> LOGGER.info("server response >> {}  ", result.getResponse().getContentAsString()));
        //then
        resultActions
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(IsEqualIgnoringCase.equalToIgnoringCase(username)));
    }

    @Test
    public void failedAuthentication() throws Exception {
        //given
        String username = "demo";
        String password = "htyhgy";
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBasicAuth(username, password);
        //when
        final ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get(URI.create("/user"))
                        .headers(httpHeaders))
                .andDo(result -> LOGGER.info("server response >> {}  ", result.getResponse().getContentAsString()));
        //then
        resultActions.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

}

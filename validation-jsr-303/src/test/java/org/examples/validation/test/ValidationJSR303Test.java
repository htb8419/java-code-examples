package org.examples.validation.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.examples.validation.model.AddressDto;
import org.examples.validation.model.PersonDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ValidationJSR303Test {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenPersonDto_whenIsNotValidInfo_thenReturnFalse() throws Exception {
        PersonDto personDto = new PersonDto();
        personDto.setEmail("test");
        personDto.setAddress(new AddressDto());
        mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto))
                ).andExpect(status().isBadRequest())
                .andDo(print());
    }

    private PersonDto getPerson() {
        return new PersonDto("name-" + RandomString.make(6), "lasname-" + RandomString.make(6), "t@gmail.com");
    }
}

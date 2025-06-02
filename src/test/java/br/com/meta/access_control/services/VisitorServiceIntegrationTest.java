package br.com.meta.access_control.services;

import br.com.meta.dto.VisitorDTO;
import br.com.meta.access_control.utils.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VisitorServiceIntegrationTest {

    private static final String VISITORS_URL = "/visitors";

    @Autowired
    MockMvc mockMvc;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturn201WhenCreateVisitor() throws Exception {

        final VisitorDTO vDTO = new VisitorDTO(EntityUtils.criarNovo());
        final String payload = objectMapper.writeValueAsString(vDTO);


        final MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(VISITORS_URL).content(payload).contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isCreated())
                .andReturn();

        String str = response.getResponse().getContentAsString();

        Assertions.assertEquals("Message has been produced.", str);

    }

}

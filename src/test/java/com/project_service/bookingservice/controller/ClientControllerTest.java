package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.ClientDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNewClient() throws Exception {
        //given
        ClientDto clientDto = new ClientDto();
        clientDto.setEmail("ivanov.ivan@gmail.com");
        clientDto.setPhone("+123456789");
        clientDto.setFirstName("Ivan");
        clientDto.setLastName("Ivanov");
        clientDto.setLanguage("RU");
        clientDto.setCountry("Belarus");
        clientDto.setCommentText("comment");

        String clientStr = objectMapper.writeValueAsString(clientDto);

        //when
        MvcResult clientCreationResult = mockMvc.perform(MockMvcRequestBuilders.post("/clients/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(clientStr))
                .andExpect(status().isCreated())
                .andReturn();

        String id = clientCreationResult.getResponse().getContentAsString();

        String clientJson = mockMvc.perform(MockMvcRequestBuilders.get("/clients/" + id))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        //then
        ClientDto clientDtoResult = objectMapper.readValue(clientJson, ClientDto.class);

        Assertions.assertNotNull(clientDtoResult.getId());
        clientDtoResult.setId(null);
        Assertions.assertEquals(clientDto, clientDtoResult);
    }
}

package com.project_service.bookingservice.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_service.bookingservice.dto.CurrencyDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class CurrencyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails(value = "aloha.test@gmail.com")
    void getAllCurrencyTest() throws Exception {
        List<CurrencyDto> expected = createCurrencyList();

        MvcResult currencyJson = mockMvc.perform(MockMvcRequestBuilders.get("/currencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String currencyResultJson = currencyJson.getResponse().getContentAsString();

        List<CurrencyDto> actual = objectMapper.readValue(currencyResultJson, new TypeReference<>() {
        });

        Assertions.assertEquals(200, currencyJson.getResponse().getStatus());
        Assertions.assertEquals(expected, actual);
    }

    private List<CurrencyDto> createCurrencyList() {
        List<CurrencyDto> currencyDtoList = new ArrayList<>();

        CurrencyDto currencyDto1 = new CurrencyDto();
        currencyDto1.setId("3f4245b3-94cc-4b2d-bc7b-d29f6a0d7f20");
        currencyDto1.setName("US Dollar");
        currencyDto1.setCode("USD");
        currencyDtoList.add(currencyDto1);

        CurrencyDto currencyDto2 = new CurrencyDto();
        currencyDto2.setId("6e0727d5-8eb9-438e-8e61-c30e0506a889");
        currencyDto2.setName("Euro");
        currencyDto2.setCode("EUR");
        currencyDtoList.add(currencyDto2);

        CurrencyDto currencyDto3 = new CurrencyDto();
        currencyDto3.setId("a78c2d2b-cc71-42e1-a8c2-bc7b42bd6bae");
        currencyDto3.setName("British Pound");
        currencyDto3.setCode("GBP");
        currencyDtoList.add(currencyDto3);

        CurrencyDto currencyDto4 = new CurrencyDto();
        currencyDto4.setId("db41867b-682b-4121-b84b-830530fe9f2e");
        currencyDto4.setName("Peso");
        currencyDto4.setCode("PHP");
        currencyDtoList.add(currencyDto4);

        CurrencyDto currencyDto5 = new CurrencyDto();
        currencyDto5.setId("3b56fe6e-6910-4b0d-863e-ac60262e7a17");
        currencyDto5.setName("Yuan Renminbi");
        currencyDto5.setCode("CNY");
        currencyDtoList.add(currencyDto5);

        return currencyDtoList;
    }
}
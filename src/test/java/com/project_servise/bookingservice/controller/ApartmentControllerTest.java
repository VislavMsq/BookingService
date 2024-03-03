package com.project_servise.bookingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_servise.bookingservice.service.ApartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@Sql("/schema-cleanup.sql")
@Sql("/create_tables.sql")
@Sql("/add_test_data.sql")
@AutoConfigureMockMvc
public class ApartmentControllerTest {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    void createApartmentTest() {
    }

}

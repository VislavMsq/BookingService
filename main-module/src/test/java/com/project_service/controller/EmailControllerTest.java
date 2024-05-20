package com.project_service.controller;

import com.project_service.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("/database/schema-cleanup.sql")
@Sql("/database/create_tables.sql")
@Sql("/database/add_test_data.sql")
class EmailControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Test
    void sendCode() throws Exception {
        String email = "eusebiujacot@gmail.com";

        mockMvc.perform(MockMvcRequestBuilders.put("/emails/send_code")
                        .param("email", email))
                .andExpect(status().isOk());

        verify(emailService).resendActivationCode(email);
    }

    @Test
    void initiateForgotPassword() throws Exception {
        String email = "eusebiujacot@gmail.com";

        mockMvc.perform(MockMvcRequestBuilders.put("/emails/forgot_password")
                        .param("email", email))
                .andExpect(status().isOk());

        verify(emailService).initiatePasswordReset(email);
    }
}

package com.backfam.transfers.presentation;

import com.backfam.transfers.application.dto.response.TransactionResponseDTO;
import com.backfam.transfers.application.dto.request.TransactionRequestDTO;
import com.backfam.transfers.application.dto.response.TransactionResponseDTO;
import com.backfam.transfers.application.service.TransactionService;
import com.backfam.transfers.presentation.controller.TransactionController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void performTransaction_returnsOk() throws Exception {
        // Arrange
        TransactionRequestDTO requestDTO = new TransactionRequestDTO(
                "1234567890",
                new BigDecimal("100.00"),
                "DEPOSITO"
        );

        TransactionResponseDTO responseDTO = new TransactionResponseDTO(
                1,
                "1234567890",
                new BigDecimal("100.00"),
                "DEPOSITO",
                LocalDateTime.now()
        );

        Mockito.when(transactionService.performTransaction(any())).thenReturn(responseDTO);

        // Act + Assert
        mockMvc.perform(post("/api/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNum").value("1234567890"))
                .andExpect(jsonPath("$.amount").value(100.00))
                .andExpect(jsonPath("$.type").value("DEPOSITO"));
    }
}

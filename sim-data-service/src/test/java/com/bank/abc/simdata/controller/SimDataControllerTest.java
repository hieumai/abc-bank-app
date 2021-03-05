package com.bank.abc.simdata.controller;

import com.bank.abc.simdata.services.SimDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimDataControllerTest {
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String RESULT = "result";
    @Mock
    private SimDataService mockSimDataService;

    @InjectMocks
    private SimDataController simDataController;

    @Test
    void buyDataShouldReturnResultFromSimDataService() throws ExecutionException, InterruptedException {
        when(mockSimDataService.buyData(PHONE_NUMBER)).thenReturn(RESULT);
        ResponseEntity<String> responseEntity = simDataController.buyData(PHONE_NUMBER);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(RESULT);
    }
}
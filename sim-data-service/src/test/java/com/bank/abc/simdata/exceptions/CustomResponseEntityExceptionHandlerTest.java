package com.bank.abc.simdata.exceptions;

import com.bank.abc.simdata.models.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomResponseEntityExceptionHandlerTest {
    public static final String PARAMETER_NAME = "parameterName";
    public static final String PARAMETER_TYPE = "parameterType";
    public static final String LOCALIZED_MESSAGE = "Required " + PARAMETER_TYPE + " parameter '" + PARAMETER_NAME + "' is not present";

    @InjectMocks
    private CustomResponseEntityExceptionHandler exceptionHandler;

    @Test
    void handleMissingServletRequestParameterShouldReturnNewResponseEntityForException() {
        MissingServletRequestParameterException mockException = new MissingServletRequestParameterException(PARAMETER_NAME, PARAMETER_TYPE);
        ResponseEntity<Object> responseEntity = exceptionHandler.handleMissingServletRequestParameter(
                mockException, null, null, null);
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getHeaders()).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(responseEntity.getBody()).isInstanceOf(ApiError.class);
        ApiError apiError = (ApiError) responseEntity.getBody();
        assertThat(apiError).isNotNull();
        assertThat(apiError.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(apiError.getMessage()).isEqualTo(LOCALIZED_MESSAGE);
        assertThat(apiError.getErrors()).hasSize(1);
        assertThat(apiError.getErrors().get(0)).isEqualTo(PARAMETER_NAME + " parameter is missing");
    }
}
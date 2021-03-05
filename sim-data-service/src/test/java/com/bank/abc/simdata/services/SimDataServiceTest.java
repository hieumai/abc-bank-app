package com.bank.abc.simdata.services;

import com.bank.abc.simdata.tasks.RetrievingVoucherCodeTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RExecutorFuture;
import org.redisson.api.RedissonClient;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class SimDataServiceTest {
    public static final String EXECUTOR_NAME_VALUE = "executorNameValue";
    public static final String EXECUTOR_NAME = "executorName";
    public static final String VOUCHER_CODE_SERVICE_URL = "voucherCodeServiceUrl";
    public static final String VOUCHER_CODE_SERVICE_URL_VALUE = "voucherCodeServiceUrlValue";
    public static final String VOUCHER_CODE = "voucherCode";
    public static final String PHONE_NUMBER = "phoneNumber";

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RedissonClient mockRedissonClient;

    @Mock
    private VoucherService mockVoucherService;

    @Mock
    private SmsService mockSmsService;

    @Mock
    private RExecutorFuture<String> mockExecutorFuture;

    @Captor
    private ArgumentCaptor<BiConsumer<String, Throwable>> consumerArgumentCaptor;

    @InjectMocks
    private SimDataService simDataService;

    @BeforeEach
    public void beforeEach() {
        setField(simDataService, EXECUTOR_NAME, EXECUTOR_NAME_VALUE);
        setField(simDataService, VOUCHER_CODE_SERVICE_URL, VOUCHER_CODE_SERVICE_URL_VALUE);
        when(mockRedissonClient.getExecutorService(EXECUTOR_NAME_VALUE)
                .submitAsync(any(RetrievingVoucherCodeTask.class)))
                .thenReturn(mockExecutorFuture);
    }

    @Test
    void buyDataShouldReturnVoucherCodeFromTaskWhenResultIsReturnBeforeTimeout()
            throws InterruptedException, ExecutionException, TimeoutException {
        when(mockExecutorFuture.get(3, TimeUnit.SECONDS)).thenReturn(VOUCHER_CODE);
        String voucherCode = simDataService.buyData(PHONE_NUMBER);
        assertThat(voucherCode).isEqualTo(VOUCHER_CODE);
        verify(mockExecutorFuture).onComplete(consumerArgumentCaptor.capture());

        BiConsumer<String, Throwable> resultConsumer = consumerArgumentCaptor.getValue();
        resultConsumer.accept(VOUCHER_CODE, null);
        verify(mockSmsService, times(0)).sendVoucherCodeToPhoneNumber(PHONE_NUMBER, VOUCHER_CODE);
        verify(mockVoucherService).saveVoucherByUserPhoneNumber(PHONE_NUMBER, VOUCHER_CODE);
    }

    @Test
    void buyDataShouldReturnWaitMessageFromTaskThenSendSMSWhenResultIsNotReturnBeforeTimeout()
            throws InterruptedException, ExecutionException, TimeoutException {
        when(mockExecutorFuture.get(3, TimeUnit.SECONDS)).thenThrow(new TimeoutException());
        String message = simDataService.buyData(PHONE_NUMBER);
        assertThat(message).isEqualTo("Please wait. The request is being processed within 30 seconds");
        verify(mockExecutorFuture).onComplete(consumerArgumentCaptor.capture());

        BiConsumer<String, Throwable> resultConsumer = consumerArgumentCaptor.getValue();
        resultConsumer.accept(VOUCHER_CODE, null);
        verify(mockSmsService).sendVoucherCodeToPhoneNumber(PHONE_NUMBER, VOUCHER_CODE);
        verify(mockVoucherService).saveVoucherByUserPhoneNumber(PHONE_NUMBER, VOUCHER_CODE);
    }
}
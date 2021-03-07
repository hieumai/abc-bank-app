package com.bank.abc.simdata.tasks;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RetrievingVoucherCodeTaskTest {
    public static final String SERVER_URL = "serverUrl";
    public static final String ERROR_MESSAGE = "expected error for testing";
    public static final String DATA = "data line 1\ndata line 2";
    @Mock
    private Appender<ILoggingEvent> mockedAppender;

    @Captor
    private ArgumentCaptor<ILoggingEvent> loggingEventCaptor;

    @Mock
    private HttpClient mockClient;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpResponse mockResponse;

    private RetrievingVoucherCodeTask task;

    @BeforeEach
    public void setup() {
        Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        root.addAppender(mockedAppender);
        root.setLevel(Level.INFO);
        task = spy(new RetrievingVoucherCodeTask(SERVER_URL));
        when(task.getHttpClient()).thenReturn(mockClient);
    }

    @Test
    void callShouldCallVoucherCodeServiceToGetTheResult() throws IOException {
        when(mockClient.execute(any(HttpGet.class))).thenReturn(mockResponse);
        when(mockResponse.getEntity().getContent()).thenReturn(new ByteArrayInputStream(DATA.getBytes()));
        String result = task.call();
        assertThat(result).isEqualTo(DATA);
    }

    @Test
    void callShouldLogErrorAndReturnNullWhenExceptionHappened() throws IOException {
        when(mockClient.execute(any(HttpGet.class))).thenThrow(new IOException(ERROR_MESSAGE));
        task.call();
        verify(mockedAppender, times(2)).doAppend(loggingEventCaptor.capture());
        ILoggingEvent loggingEvent = loggingEventCaptor.getAllValues().get(1);
        assertThat(loggingEvent.getLevel()).isEqualTo(Level.ERROR);
        assertThat(loggingEvent.getMessage()).isEqualTo(ERROR_MESSAGE);
    }
}
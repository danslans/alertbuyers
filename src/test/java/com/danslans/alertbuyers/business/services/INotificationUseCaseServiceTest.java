package com.danslans.alertbuyers.business.services;

import com.danslans.alertbuyers.business.adapters.INotificationApiAdapter;
import com.danslans.alertbuyers.business.adapters.NotificationApiAdapter;
import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import com.danslans.alertbuyers.business.models.entities.TemplateEntity;
import com.danslans.alertbuyers.business.services.impl.NotificationUseCaseService;
import com.danslans.alertbuyers.business.strategy.IEmailSenderStrategy;
import com.danslans.alertbuyers.business.strategy.mailtrap.MailTrapSender;
import com.danslans.alertbuyers.business.strategy.mailtrap.ResponseMailTrap;
import com.danslans.alertbuyers.data.repositories.INotificationRepository;
import com.danslans.alertbuyers.data.repositories.ITemplateRepository;
import com.danslans.alertbuyers.exceptions.NotificationEmailException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class INotificationUseCaseServiceTest {

    INotificationUseCaseService notificationUserCaseService;
    @Mock
    ITemplateRepository templateRepository;
    @Mock
    INotificationRepository notificationRepository;
    @Mock
    WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        INotificationApiAdapter notificationApiAdapter = new NotificationApiAdapter();
        IEmailSenderStrategy emailSenderStrategy = new MailTrapSender(webClient,notificationApiAdapter);
        notificationUserCaseService = new NotificationUseCaseService(templateRepository,notificationRepository,emailSenderStrategy);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        TemplateEntity templateEntity =new  TemplateEntity();
        templateEntity.setActive(true);
        templateEntity.setContent("test");
        templateEntity.setName("test");
        templateEntity.setId(1L);
        templateEntity.setSubject("test");
        when(templateRepository.findByActiveIsTrue()).thenReturn(templateEntity);

    }

    @Test
    void when_TheCodeToSendTheNotificationIsCorrect_Then_SendTheNotification() {
        ResponseMailTrap responseMailTrap = ResponseMailTrap.builder()
                .success(true)
                .message_ids(Arrays.asList("test"))
                .build();
        when(responseSpec.bodyToMono(any(Class.class))).thenReturn(Mono.just(responseMailTrap));
        Boolean result = notificationUserCaseService.sendNotification("test",true,"test", Location.builder().latitude("0").longitude("0").build())
                .block();

        assertTrue(result);
    }

    @Test
    void when_TheCodeToSendTheNotificationIsNotCorrect_Then_NotSendTheNotification() {
        ResponseMailTrap responseMailTrap = ResponseMailTrap.builder()
                .success(false)
                .message_ids(Arrays.asList("test"))
                .build();
        when(responseSpec.bodyToMono(any(Class.class))).thenReturn(Mono.just(responseMailTrap));
        Boolean result = notificationUserCaseService.sendNotification("test",true,"test", Location.builder().latitude("0").longitude("0").build())
                .block();

        assertEquals(false,result);
    }

    @Test
    void when_NotificationFail_Then_expectException() {

        when(responseSpec.bodyToMono(ResponseMailTrap.class)).thenReturn(Mono.error(new RuntimeException("Failed to send email")));
        notificationUserCaseService.sendNotification("test",true,"test", Location.builder().latitude("0").longitude("0").build())
                .doOnError(throwable ->
                        assertEquals(NotificationEmailException.ERROR_SENDING_EMAIL_MAILTRAPSENDER, throwable.getMessage()));

    }

    @Test
    void when_NotificationSuccessByEmail_Then_ReturnListNotifications() {
        when(notificationRepository.findByEmail(any(String.class))).thenReturn(Arrays.asList(NotificationEntity.builder().email("test").date(LocalDateTime.now()).build()));
        List<NotificationEntity> result = notificationUserCaseService.getNotificationsByEmail("test");
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
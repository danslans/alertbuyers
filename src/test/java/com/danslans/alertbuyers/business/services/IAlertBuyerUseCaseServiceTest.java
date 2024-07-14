package com.danslans.alertbuyers.business.services;

import com.danslans.alertbuyers.business.adapters.INotificationApiAdapter;
import com.danslans.alertbuyers.business.adapters.IWeatherApiAdapter;
import com.danslans.alertbuyers.business.adapters.NotificationApiAdapter;
import com.danslans.alertbuyers.business.adapters.WeatherApiAdapter;
import com.danslans.alertbuyers.business.models.Location;
import com.danslans.alertbuyers.business.models.RequestAlertBuyer;
import com.danslans.alertbuyers.business.models.ResponseAlertBuyer;
import com.danslans.alertbuyers.business.models.entities.NotificationEntity;
import com.danslans.alertbuyers.business.models.entities.TemplateEntity;
import com.danslans.alertbuyers.business.services.impl.AlertBuyerUseCaseService;
import com.danslans.alertbuyers.business.services.impl.ForecastUseCaseService;
import com.danslans.alertbuyers.business.services.impl.NotificationUseCaseService;
import com.danslans.alertbuyers.business.services.impl.WeatherConditionUseCaseService;
import com.danslans.alertbuyers.business.strategy.IEmailSenderStrategy;
import com.danslans.alertbuyers.business.strategy.IWeatherClientStrategy;
import com.danslans.alertbuyers.business.strategy.mailtrap.MailTrapSender;
import com.danslans.alertbuyers.business.strategy.mailtrap.ResponseMailTrap;
import com.danslans.alertbuyers.business.strategy.weatherapi.*;
import com.danslans.alertbuyers.data.repositories.IForecastCodesRepository;
import com.danslans.alertbuyers.data.repositories.INotificationRepository;
import com.danslans.alertbuyers.data.repositories.ITemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
class IAlertBuyerUseCaseServiceTest {

    @Mock
    ITemplateRepository templateRepository;
    @Mock
    INotificationRepository notificationRepository;
    @Mock
    WebClient webClient;
    @Mock
    IForecastCodesRepository forecastCodesRepository;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    @Mock
    private WebClient.ResponseSpec responseSpecMock;

    IAlertBuyerUseCaseService alertBuyerUseCaseService;

    @BeforeEach
    void setUp() {

        IWeatherApiAdapter weatherApiAdapter = new WeatherApiAdapter();
        INotificationApiAdapter notificationApiAdapter = new NotificationApiAdapter();

        IWeatherClientStrategy weatherClientStrategy = new WeatherApiClient(webClient,weatherApiAdapter);
        IEmailSenderStrategy emailSenderStrategy = new MailTrapSender(webClient,notificationApiAdapter);


        IWeatherConditionUseCaseService weatherConditionUserCaseService = new WeatherConditionUseCaseService(weatherClientStrategy);
        INotificationUseCaseService notificationUserCaseService = new NotificationUseCaseService(templateRepository,notificationRepository,emailSenderStrategy);
        IForecastUseCaseService forecastUseCaseService = new ForecastUseCaseService(forecastCodesRepository);

        alertBuyerUseCaseService = new AlertBuyerUseCaseService(weatherConditionUserCaseService,notificationUserCaseService,forecastUseCaseService);

        // Configurar WebClient
        when(webClient.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.uri(any(), any(), any(), any())).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);



        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(any(String.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.headers(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

    }

    @Test
    void when_calling_sendAlertBuyerWithWeatherInListOfNotifications_then_return_responseWithCodeAndMessage() {

        RequestAlertBuyer requestAlertBuyer = RequestAlertBuyer.builder()
                .email("Qp3Jt@example.com")
                .location(Location.builder()
                        .latitude("0.0")
                        .longitude("0.0")
                        .build())
                .build();

        TemplateEntity templateEntity =new  TemplateEntity();
        templateEntity.setActive(true);
        templateEntity.setContent("test");
        templateEntity.setName("test");
        templateEntity.setId(1L);
        templateEntity.setSubject("test");



        ResponseWeather responseWeather = ResponseWeather.builder()
                .forecast(Forecast.builder()
                        .forecastday(Arrays.asList(ForecastDay.builder()
                                        .date(LocalDate.now().plusDays(1).toString())
                                        .day(Day.builder()
                                                .condition(Condition.builder()
                                                        .code(1192)
                                                        .text("Cloudy")
                                                        .build())
                                                .build())
                                .build()))
                        .build())
                .build();

        ResponseMailTrap responseMailTrap = ResponseMailTrap.builder()
                .success(true)
                .message_ids(Arrays.asList("test"))
                .build();

        when(templateRepository.findByActiveIsTrue()).thenReturn(templateEntity);

        when(notificationRepository.save(any())).thenReturn(NotificationEntity.builder().build());

        when(forecastCodesRepository.existsByCode(1192)).thenReturn(true);

        when(responseSpec.bodyToMono(any(Class.class))).thenReturn(Mono.just(responseMailTrap));
        when(responseSpecMock.bodyToMono(ResponseWeather.class)).thenReturn(Mono.just(responseWeather));

        ResponseAlertBuyer response = alertBuyerUseCaseService.sendAlertBuyer(requestAlertBuyer).block();

        Assert.isTrue(response.isBuyerNotification(),"can't send email");

    }

    @Test
    void when_calling_WeatherIsOk_then_nothingNotification() {

        RequestAlertBuyer requestAlertBuyer = RequestAlertBuyer.builder()
                .email("Qp3Jt@example.com")
                .location(Location.builder()
                        .latitude("0.0")
                        .longitude("0.0")
                        .build())
                .build();
        ResponseWeather responseWeather = ResponseWeather.builder()
                .forecast(Forecast.builder()
                        .forecastday(Arrays.asList(ForecastDay.builder()
                                .date(LocalDate.now().plusDays(1).toString())
                                .day(Day.builder()
                                        .condition(Condition.builder()
                                                .code(0000)
                                                .text("OK")
                                                .build())
                                        .build())
                                .build()))
                        .build())
                .build();

        ResponseMailTrap responseMailTrap = ResponseMailTrap.builder()
                .success(true)
                .message_ids(Arrays.asList("test"))
                .build();

        when(responseSpec.bodyToMono(any(Class.class))).thenReturn(Mono.just(responseMailTrap));
        when(responseSpecMock.bodyToMono(ResponseWeather.class)).thenReturn(Mono.just(responseWeather));
        ResponseAlertBuyer response = alertBuyerUseCaseService.sendAlertBuyer(requestAlertBuyer).block();
        assertEquals(false,response.isBuyerNotification());
        assertEquals(0000,response.getForecastCode());
        assertEquals("OK",response.getForecastDescription());
    }


}
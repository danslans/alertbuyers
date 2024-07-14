package com.danslans.alertbuyers.infrastructure;

import com.danslans.alertbuyers.business.strategy.IEmailSenderStrategy;
import com.danslans.alertbuyers.business.strategy.IWeatherClientStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StrategyConfig {

    @Value("${notification.strategy}")
    private String strategyBeanName;
    @Value("${weatherClient.strategy}")
    private String weatherClientStrategyBeanName;

    @Autowired
    private ApplicationContext applicationContext;
    @Bean
    public IEmailSenderStrategy emailSenderStrategy() {
       return (IEmailSenderStrategy) applicationContext.getBean(strategyBeanName);
    }
    @Bean
    public IWeatherClientStrategy weatherClientStrategy() {
        return (IWeatherClientStrategy) applicationContext.getBean(weatherClientStrategyBeanName);
    }
}

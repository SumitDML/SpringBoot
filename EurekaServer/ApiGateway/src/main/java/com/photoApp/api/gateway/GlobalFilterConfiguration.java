package com.photoApp.api.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {
    final Logger logger = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

    @Order(1)
    @Bean
    public GlobalFilter secondPreFilter(){
        return (exchange, chain) -> {
            logger.info("My Second Global pre-Filter is Executed.....");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info(("My Second Global post-Filter was Executed....."));
            }));
        };

    }
    @Order(2)
    @Bean
    public GlobalFilter thirdPreFilter(){
        return (exchange, chain) -> {
            logger.info("My third Global pre-Filter is Executed.....");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info(("My third Global post-Filter was Executed....."));
            }));
        };

    }
    @Order(3)
    @Bean
    public GlobalFilter fourthPreFilter(){
        return (exchange, chain) -> {
            logger.info("My fourth Global pre-Filter is Executed.....");
            return chain.filter(exchange).then(Mono.fromRunnable(()->{
                logger.info(("My fourth Global post-Filter was Executed....."));
            }));
        };

    }



}

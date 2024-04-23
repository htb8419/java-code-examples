package org.examples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    ThreadPoolTaskExecutor mvcTaskExecutor;

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        //Specify the amount of time, in milliseconds, before asynchronous request handling times out.
        configurer.setDefaultTimeout(120_000);
        configurer.setTaskExecutor(mvcTaskExecutor);
    }
}

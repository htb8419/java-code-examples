package org.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class LoggingResponseTimeApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoggingResponseTimeApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<OncePerRequestFilter> loggingResponseTimeFilter() {
        return new FilterRegistrationBean<>(new OncePerRequestFilter() {
            final Logger logger = LoggerFactory.getLogger(LoggingResponseTimeApplication.class);

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                StopWatch stopWatch = new StopWatch();
                try {
                    stopWatch.start();
                    filterChain.doFilter(request, response);
                } finally {
                    stopWatch.stop();
                    logger.debug("Request: [{}] completed within {} ms", getUriWithMethodAndQueryString(request),
                             stopWatch.getTotalTimeMillis());
                }
            }

            private String getUriWithMethodAndQueryString(HttpServletRequest request) {
                return request.getMethod() + " -> " + request.getRequestURI() +
                        (StringUtils.hasText(request.getQueryString()) ? "?" + request.getQueryString() : "");
            }
        });
    }
}

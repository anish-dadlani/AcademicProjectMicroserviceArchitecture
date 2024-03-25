package com.enroll.config;

import com.enroll.service.feign.CourseClient;
import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class EnrollConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
//    @Bean
//    public CourseClient courseClient() {
//        return Feign.builder()
//                // Configure Feign properties here (optional)
//                .target(CourseClient.class, "http://localhost:8082/api/v1/courses/");
//    }
}

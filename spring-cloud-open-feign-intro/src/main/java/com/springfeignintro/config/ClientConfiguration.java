package com.springfeignintro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.okhttp.OkHttpClient;

@Configuration
public class ClientConfiguration {

    @Bean
    public OkHttpClient client() {
        return new OkHttpClient();
    }
    
}

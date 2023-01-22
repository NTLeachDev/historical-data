package com.nleachdev.historicaldata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public String rootPath(@Value("${data.root.path}") final String rootPath) {
        return rootPath;
    }
}

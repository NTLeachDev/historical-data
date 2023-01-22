package com.nleachdev.historicaldata;

import com.nleachdev.historicaldata.service.UploadService;
import com.nleachdev.historicaldata.util.StopWatchTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan("com.nleachdev")
@PropertySource("connection.properties")
public class DbUploadApplication {
    private static final Logger logger = LoggerFactory.getLogger(DbUploadApplication.class);

    public static void main(final String... args) {
        final ApplicationContext context = new SpringApplicationBuilder(DbUploadApplication.class)
                .run(args);

        final UploadService uploadService = context.getBean(UploadService.class);

        new StopWatchTimer("Db Upload Application")
                .doWithTime(
                        "Upload Data",
                        uploadService::uploadData
                );
    }

    private static int seconds(final long millis) {
        return (int) (millis / 1000);
    }
}

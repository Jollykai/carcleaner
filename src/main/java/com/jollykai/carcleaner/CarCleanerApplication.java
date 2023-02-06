package com.jollykai.carcleaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Project: CarCleaner
 *
 * @author Dmitry Istomin
 * https://github.com/Jollykai
 */
@SpringBootApplication
@EnableSwagger2
public class CarCleanerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarCleanerApplication.class, args);
    }

}

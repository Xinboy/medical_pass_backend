package com.xinbo.mp.hosp;

import com.xinbo.mp.common.config.Swagger2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author xinbo
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.xinbo"})
@EnableSwagger2
public class ServiceHospApplication {
    public static void main(String[] args) {

        SpringApplication.run(ServiceHospApplication.class, args);
    }
}

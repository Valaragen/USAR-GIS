package com.usargis.usargisapi;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEncryptableProperties
@EnableSwagger2
public class UsargisApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UsargisApiApplication.class, args);
    }

}

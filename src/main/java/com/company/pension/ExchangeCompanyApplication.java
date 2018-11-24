package com.company.pension;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExchangeCompanyApplication {

    private static final String DEFAULT_DELAY = "0";
    private static final String DEFAULT_DELAY_ENV_VARIABLE_NAME = "START_DELAY";


    public static void main(String[] args) throws InterruptedException {
        String delay = System.getenv(DEFAULT_DELAY_ENV_VARIABLE_NAME) == null ? DEFAULT_DELAY : System.getenv(DEFAULT_DELAY_ENV_VARIABLE_NAME);

        Thread.sleep(Long.valueOf(delay));
        SpringApplication.run(ExchangeCompanyApplication.class, args);
    }

}

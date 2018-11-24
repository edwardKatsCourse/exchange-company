package com.company.pension.jobs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class ApplicationListenerRunner implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${path.input}")
    private String basePath;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        File directory = new File(basePath);

        if (!directory.exists()) {
            log.info("Directory [{}] does not exist", directory);
            log.info("Creating directory..");
            createDirectory(directory);
        }
    }


    private void createDirectory(File directory) {
        if (directory.exists()) {
            return;
        }

        boolean isDirectoryCreated = directory.mkdirs();
        if (!isDirectoryCreated) {
            log.error("DIRECTORY [{}] CANNOT BE CREATED", directory);
            System.exit(-1);
        }
    }


}

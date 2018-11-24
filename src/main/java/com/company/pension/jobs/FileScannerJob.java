package com.company.pension.jobs;

import com.company.pension.service.InputFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Slf4j
public class FileScannerJob {

    @Autowired
    private InputFileService inputFileService;

    @Value("${path.input}")
    private String basePath;


    @Scheduled(fixedDelay = 1000L, initialDelay = 5000L)
    public void scan() {
        File directory = new File(basePath);

        if (!directory.exists()) {
            log.error("DIRECTORY [{}] does not exist. Please, restart the server", directory);
            System.exit(-1);
        }

        Arrays.stream(directory.listFiles())
                .forEach(file -> {
                    recordFile(file);
                    removingFile(file);
                });
    }



    private void recordFile(File inputFile) {
        log.debug("Processing file: [{}]", inputFile.getName());

        inputFileService.save(inputFile);

        log.info("File: [{}] recorded", inputFile.getName());
        printDate();
    }

    private void removingFile(File inputFile) {
        log.debug("Removing file: [{}]", inputFile.getName());
        boolean deleteResult = inputFile.delete();

        String logMessage = deleteResult ? "Removed file: [{}]" : "Failed to remove file: [{}]";
        log.info(logMessage, inputFile.getName());
        printDate();
    }

    private void printDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        log.info("Date: [{}], Time: [{}]\n", dateTime.toLocalDate(), dateTime.toLocalTime());
    }
}

package com.company.pension.controller;

import com.company.pension.exception.ResourceNotFoundException;
import com.company.pension.model.dto.InputFileResponse;
import com.company.pension.model.entity.InputFile;
import com.company.pension.service.InputFileService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.List;

@RestController
public class InputFileController {


    @Autowired
    private InputFileService inputFileService;

    @GetMapping("/files")
    public List<InputFileResponse> getAllFiles() {
        return inputFileService.getAll();
    }

    @GetMapping("/files/{id}")
    public HttpEntity<byte[]> downloadFile(@PathVariable("id") Long id) {
        InputFile inputFile = inputFileService.getFileContentById(id);
        if (inputFile == null) {
            throw new ResourceNotFoundException(String.format("File with ID [%s] not found", id));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", inputFile.getFilename()));
        headers.setContentLength(inputFile.getContent().length);
        return new HttpEntity<>(inputFile.getContent(), headers);
    }

    @SneakyThrows
    @GetMapping("/test")
    public String test() {
        return InetAddress.getLocalHost().getHostAddress();
    }
}

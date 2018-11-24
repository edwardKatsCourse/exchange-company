package com.company.pension.service.impl;

import com.company.pension.model.dto.InputFileResponse;
import com.company.pension.model.entity.InputFile;
import com.company.pension.repository.InputFileRepository;
import com.company.pension.service.InputFileService;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InputFileServiceImpl implements InputFileService {

    @Autowired
    private InputFileRepository inputFileRepository;

    @Value("${host.name}")
    private String hostName;

    @Value("${server.port}")
    private Integer port;


    @Override
    @SneakyThrows
    public void save(File file) {
        String filename = file.getName();
        String fileContent = FileUtils.readFileToString(file, Charset.defaultCharset());

        InputFile inputFile = InputFile.builder()
                .filename(filename)
                .content(fileContent.getBytes())
                .build();

        inputFileRepository.save(inputFile);
    }

    @Override
    public List<InputFileResponse> getAll() {
        return inputFileRepository.findAll().stream()
                .map(x -> InputFileResponse.builder()
                        .id(x.getId())
                        .fileContent(new String(x.getContent()))
                        .filename(x.getFilename())
                        .createdOn(x.getCreatedOn())
                        .downloadLink(String.format("http://%s:%s/files/%s", hostName, port, x.getId()))
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public InputFile getFileContentById(Long id) {
        return inputFileRepository.findById(id).orElse(null);
    }
}

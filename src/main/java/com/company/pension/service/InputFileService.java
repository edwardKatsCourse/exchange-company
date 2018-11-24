package com.company.pension.service;

import com.company.pension.model.dto.InputFileResponse;
import com.company.pension.model.entity.InputFile;

import java.io.File;
import java.util.List;

public interface InputFileService {

    void save(File file);

    List<InputFileResponse> getAll();

    InputFile getFileContentById(Long id);
}

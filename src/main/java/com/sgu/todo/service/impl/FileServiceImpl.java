package com.sgu.todo.service.impl;

import com.sgu.todo.entity.File;
import com.sgu.todo.repository.FileRepository;
import com.sgu.todo.service.FileService;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    final  private  FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File findById(Integer id) {
        return fileRepository.findById(id).get();
    }
}

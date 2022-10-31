package com.example.demo.service;

import com.example.demo.model.entity.File;
import com.example.demo.repository.FileRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    @Autowired private FileRepository fileRepository;

    @Transactional
    public File upload(MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        File file = new File();
        file.setName(fileName);
        file.setType(multipartFile.getContentType());
        file.setData(multipartFile.getBytes());

        return fileRepository.save(file);
    }

    @Transactional
    public Page<File> findAll(Specification<File> filter, int page, int size) {
        Pageable paging = PageRequest.of(page - 1, size);
        return fileRepository.findAll(filter, paging);
    }

    @Transactional
    public File findById(UUID id) {
        Optional<File> file = fileRepository.findById(id);
        if (file.isEmpty()) {
            throw new EntityNotFoundException("File not found");
        }
        return file.get();
    }

    @Transactional
    public void deleteById(UUID id) {
        File file = findById(id);
        fileRepository.delete(file);
    }
}

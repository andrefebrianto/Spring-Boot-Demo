package com.example.demo.controller;

import com.example.demo.common.constant.PageConstant;
import com.example.demo.common.exception.UploadFailedException;
import com.example.demo.common.util.FileUtil;
import com.example.demo.model.entity.File;
import com.example.demo.model.mapper.FileMapper;
import com.example.demo.model.response.FileResponse;
import com.example.demo.model.response._MessageSuccessResponse;
import com.example.demo.model.response._PageResponse;
import com.example.demo.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "4. Files")
@RequestMapping("/api/v1/files")
@SecurityRequirement(name = "authorize")
public class FileController {

    @Autowired private FileUtil fileUtil;

    @Autowired private FileMapper fileMapper;

    @Autowired private FileService fileService;

    @GetMapping("/")
    @Operation(summary = "Get All")
    public ResponseEntity<_PageResponse<FileResponse>> getAll(
            @Parameter(example = PageConstant.SAMPLE_FILE_FILTER)
                    @And({
                        @Spec(path = "name", spec = LikeIgnoreCase.class),
                        @Spec(path = "type", spec = LikeIgnoreCase.class),
                        @Spec(path = "isDeleted", spec = Equal.class)
                    })
                    Specification<File> filter,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_SIZE) int size) {

        Page<File> pageItems = fileService.findAll(filter, page, size);
        Page<FileResponse> responseItems =
                pageItems.map(
                        file -> {
                            FileResponse response = fileMapper.fileToFileResponse(file);
                            response.setUrl(fileUtil.generateFileDownloadUri(file.getId().toString()));
                            response.setSize(file.getData().length);
                            return response;
                        });

        return new ResponseEntity<>(new _PageResponse<>(responseItems), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    @Operation(summary = "Download")
    public ResponseEntity<byte[]> download(@PathVariable UUID id) {
        File file = fileService.findById(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(file.getData());
    }

    @RequestMapping(
            path = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload")
    public ResponseEntity<FileResponse> upload(@RequestParam("file") MultipartFile multipartFile) {
        try {
            File file = fileService.upload(multipartFile);

            FileResponse response = fileMapper.fileToFileResponse(file);
            response.setUrl(fileUtil.generateFileDownloadUri(file.getId().toString()));
            response.setSize(file.getData().length);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new UploadFailedException("Upload file failed");
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete")
    public ResponseEntity<_MessageSuccessResponse> delete(@PathVariable UUID id) {
        fileService.deleteById(id);
        return ResponseEntity.ok(new _MessageSuccessResponse("Delete successful"));
    }
}

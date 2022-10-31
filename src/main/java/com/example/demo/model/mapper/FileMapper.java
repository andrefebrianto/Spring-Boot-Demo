package com.example.demo.model.mapper;

import com.example.demo.model.entity.File;
import com.example.demo.model.response.FileResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileMapper instance = Mappers.getMapper(FileMapper.class);

    @Mapping(target = "name", source = "name")
    FileResponse fileToFileResponse(File source);
}

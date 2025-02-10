package com.books_manager.mapper;

import com.books_manager.dto.BookDTO;
import com.books_manager.entities.Book;
import lombok.Data;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "author", target = "author")
    @Mapping(target = "file", ignore = true)
    @Mapping(target = "fileName", source = "fileName")
    @Mapping(target = "fileType", source = "fileType")
    BookDTO convertFromEntityToDto(Book book);

    @Mapping(source = "name", target = "name")
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "author", target = "author")
    @Mapping(target = "fileData", expression = "java(mapFileToBytes(bookDto.getFile()))")  // Custom conversion method for MultipartFile
    Book convertFromDtoToEntity(BookDTO bookDto);

    default byte[] mapFileToBytes(MultipartFile file) {
        try {
            if (file != null && !file.isEmpty()) {
                return file.getBytes(); // Convert MultipartFile to byte[]
            }
        } catch (IOException e) {
            // Handle the exception by logging or returning a default value (null or empty byte array)
            e.printStackTrace(); // You can log the exception or handle it differently
        }
        return null; // Return null if the file is empty or null
    }

}

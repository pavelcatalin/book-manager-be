package com.books_manager.mapper;

import com.books_manager.dto.BookDTO;
import com.books_manager.entities.Author;
import com.books_manager.entities.Book;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BookMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "author", target = "author")
    // Mapping for fileData to file (if needed for uploading the file)
    @Mapping(target = "file", ignore = true) // Ignore file in DTO during entity to DTO conversion (since we don't need it)
    BookDTO convertFromEntityToDto(Book book);

    @Mapping(source = "name", target = "name")
    @Mapping(target = "id", ignore = true)  // Ignore id as it is auto-generated
    @Mapping(source = "author", target = "author")
    // Here, we map MultipartFile to byte[]
    @Mapping(target = "fileData", expression = "java(mapFileToBytes(bookDto.getFile()))")  // Custom conversion method for MultipartFile
    Book convertFromDtoToEntity(BookDTO bookDto);

    // Custom method to convert MultipartFile to byte[]
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

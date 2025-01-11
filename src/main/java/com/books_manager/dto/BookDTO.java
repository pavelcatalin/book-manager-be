package com.books_manager.dto;

import com.books_manager.entities.Author;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookDTO {
    private int id;
    private String name;
    private AuthorDTO author;
    private MultipartFile file;
}

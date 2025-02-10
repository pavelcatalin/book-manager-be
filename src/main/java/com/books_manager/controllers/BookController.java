package com.books_manager.controllers;


import com.books_manager.dto.AuthorDTO;
import com.books_manager.dto.BookDTO;
import com.books_manager.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {


  private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<BookDTO>> getBooks() {
        return ResponseEntity.ok(bookService.getAllbooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable int id) {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(BookDTO bookDTO) throws IOException {
        BookDTO book =  bookService.createBook(bookDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @DeleteMapping("/{id}")
    public int deleteBook(@PathVariable int id){
       return  bookService.deleteBook(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable int id, @RequestBody BookDTO book) {
        int rowsUpdated = bookService.updateBook(id, book.getName());

        if (rowsUpdated > 0) {
            return ResponseEntity.ok("Book updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book with ID " + id + " not found");
        }
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<ByteArrayResource> downloadBookFile(@PathVariable int id){
        byte [] fileData = bookService.getFileDataByBookId(id);
        BookDTO bookDTO = bookService.getBookById(id);

        String fileType = bookDTO.getFileType();
        if (fileType == null || fileType.trim().isEmpty()) {
            fileType = "application/octet-stream";
        }


        ByteArrayResource resource = new ByteArrayResource(fileData);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + bookDTO.getFileName() + "\"")
                .contentType(MediaType.parseMediaType(fileType))
                .body(resource);
    }
}

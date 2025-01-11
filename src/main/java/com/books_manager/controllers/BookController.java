package com.books_manager.controllers;


import com.books_manager.dto.AuthorDTO;
import com.books_manager.dto.BookDTO;
import com.books_manager.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        try {
            BookDTO book = bookService.getBookById(id);
            return ResponseEntity.ok(book);
        }catch (Exception e){
            return (ResponseEntity<BookDTO>) ResponseEntity.notFound();
        }
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "authorId", required = false) Long authorId,
                                          @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        System.out.println("Received name: " + name);
        System.out.println("Received authorId: " + authorId);
        System.out.println("Received file: " + file.getOriginalFilename());

        try {

            BookDTO bookDTO = new BookDTO();
            bookDTO.setName(name);
            bookDTO.setAuthor(new AuthorDTO(authorId,null));
            bookDTO.setFile(file);

            bookService.createBook(bookDTO);

            return ResponseEntity.ok("Book uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error uploading the book: " + e.getMessage());
        }
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
}

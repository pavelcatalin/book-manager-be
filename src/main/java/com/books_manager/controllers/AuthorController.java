package com.books_manager.controllers;


import com.books_manager.dto.AuthorDTO;
import com.books_manager.entities.Author;
import com.books_manager.services.AuthorService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {


    private final AuthorService authorService;


    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> addAuthor(@RequestBody AuthorDTO authorDTO) {
        AuthorDTO author = authorService.addAuthor(authorDTO);
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }
}

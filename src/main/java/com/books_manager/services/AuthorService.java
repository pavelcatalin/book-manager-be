package com.books_manager.services;

import com.books_manager.dto.AuthorDTO;
import com.books_manager.entities.Author;
import com.books_manager.mapper.AuthorMapper;
import com.books_manager.repositories.AuthorRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::toDto).collect(Collectors.toList());
    }


    public AuthorDTO addAuthor(AuthorDTO authorDTO) {
        Author author = authorMapper.toEntity(authorDTO);

        Author savedAuthor = authorRepository.save(author);

        return authorMapper.toDto(savedAuthor);
    }
}

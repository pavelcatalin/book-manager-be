package com.books_manager.services;

import com.books_manager.dto.BookDTO;
import com.books_manager.entities.Book;

import java.io.IOException;
import java.util.List;

public interface  BookService {

    List<BookDTO> getAllbooks();

    Book createBook(BookDTO bookDTO) throws IOException;

    int deleteBook(int id);

    BookDTO getBookById(int id);

    int updateBook(int id, String name);
}

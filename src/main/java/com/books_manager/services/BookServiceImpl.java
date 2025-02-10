package com.books_manager.services;

import com.books_manager.error.handler.ResourceNotFoundException;
import com.books_manager.dto.BookDTO;
import com.books_manager.entities.Book;
import com.books_manager.mapper.BookMapper;
import com.books_manager.repositories.AuthorRepository;
import com.books_manager.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    
    private final BookRepository bookRepository;

    private final AuthorRepository authorRepository;


    private final BookMapper bookMapper;

    @Override
    public List<BookDTO> getAllbooks() {
        List<Book> allBooks = bookRepository.findAllBooks();

        return allBooks.stream().map(book -> bookMapper.convertFromEntityToDto(book)).collect(Collectors.toList());
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) throws IOException {
        if (bookDTO == null) {
            throw new IllegalArgumentException("BookDTO cannot be null");
        }

        Book book = bookMapper.convertFromDtoToEntity(bookDTO);

        MultipartFile file = bookDTO.getFile();

        if (file != null && !file.isEmpty()) {
            book.setFileName(file.getOriginalFilename());
            book.setFileType(file.getContentType());
            book.setFileData(file.getBytes());
        }

        Book savedBook = bookRepository.save(book);
        return bookMapper.convertFromEntityToDto(savedBook);
    }

    public int deleteBook(int id){
        return bookRepository.deleteBookById(id);
    }

    public BookDTO getBookById(int id) {
        BookDTO book = bookRepository.findBookById(id)
                .map(bookMapper::convertFromEntityToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        return book;
    }

    @Override
    public int updateBook(int id, String name) {
        return bookRepository.updateBook(id,name);
    }

    public byte [] getFileDataByBookId(int id){
        Book book = bookRepository.findBookById(id).orElseThrow(()->
                new ResourceNotFoundException("Book with id " + id + " was not found"));

        if (book.getFileData() == null) {
            throw new ResourceNotFoundException("File not found for book with id: " + id);
        }

        return book.getFileData();
    }



}

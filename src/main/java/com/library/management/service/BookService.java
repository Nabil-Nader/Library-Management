package com.library.management.service;

import com.library.management.dto.BookRequest;
import com.library.management.model.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    Book addBook(BookRequest bookRequest);
    Book updateBook(Long id, BookRequest bookRequest);
    void deleteBook(Long id);
}

package com.library.management.service;

import com.library.management.dto.BookRequest;
import com.library.management.exception.BookNotFoundException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImpleTest {
    @InjectMocks
    BookServiceImple bookServiceImple;

    @Mock
    BookRepository bookRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getAllBooksTest() {
        Book book1 = new Book();
        Book book2 = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookServiceImple.getAllBooks();

        assertEquals(2, books.size());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
     void getBookByIdTest() {
        Book book = new Book();
        when(bookRepository.getReferenceById(1L)).thenReturn(book);

        Book result = bookServiceImple.getBookById(1L);

        assertEquals(book, result);
        verify(bookRepository, times(1)).getReferenceById(1L);
    }

    @Test
     void getBookByIdNotFoundTest() {
        when(bookRepository.getReferenceById(1L)).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> bookServiceImple.getBookById(1L));
        verify(bookRepository, times(1)).getReferenceById(1L);
    }

    @Test
     void addBookTest() {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setTitle("Title");
        bookRequest.setAuthor("Author");
        bookRequest.setPublicationYear(2021);
        bookRequest.setIsbn("1234567890");

        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setPublicationYear(2021);
        book.setIsbn("1234567890");

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookServiceImple.addBook(bookRequest);

        assertEquals("Title", result.getTitle());
        assertEquals("Author", result.getAuthor());
        assertEquals(2021, result.getPublicationYear());
        assertEquals("1234567890", result.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
     void updateBookTest() {
        BookRequest updatedBook = new BookRequest();
        updatedBook.setTitle("Updated Title");
        updatedBook.setAuthor("Updated Author");
        updatedBook.setPublicationYear(2022);
        updatedBook.setIsbn("0987654321");

        Book book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setPublicationYear(2021);
        book.setIsbn("1234567890");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookServiceImple.updateBook(1L, updatedBook);

        assertEquals("Updated Title", result.getTitle());
        assertEquals("Updated Author", result.getAuthor());
        assertEquals(2022, result.getPublicationYear());
        assertEquals("0987654321", result.getIsbn());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
     void updateBookNotFoundTest() {
        BookRequest updatedBook = new BookRequest();
        updatedBook.setTitle("Updated Title");
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookServiceImple.updateBook(1L, updatedBook));
        verify(bookRepository, times(1)).findById(1L);
    }

}
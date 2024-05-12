package com.library.management.service;

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
        Book book = new Book();
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookServiceImple.addBook(book);

        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
     void updateBookTest() {
        Book book = new Book();
        book.setId(1L);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        Book result = bookServiceImple.updateBook(1L, updatedBook);

        assertEquals("Updated Title", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
     void updateBookNotFoundTest() {
        Book updatedBook = new Book();
        updatedBook.setTitle("Updated Title");
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookServiceImple.updateBook(1L, updatedBook));
        verify(bookRepository, times(1)).findById(1L);
    }

}
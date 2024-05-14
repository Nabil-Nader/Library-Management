package com.library.management.controller;


import com.library.management.dto.BookRequest;
import com.library.management.dto.Response;
import com.library.management.model.Book;
import com.library.management.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {


    private final BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }

    @PreAuthorize("hasRole('LIBRARIAN')")
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookRequest bookRequest) {
        Book savedBook = bookService.addBook(bookRequest);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @Valid @RequestBody BookRequest bookRequest) {
        Book updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok().body(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(new Response<>("Book deleted successfully", true, HttpStatus.OK.value()), HttpStatus.OK);
    }
}
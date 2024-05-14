package com.library.management.service;

import com.library.management.dto.BookRequest;
import com.library.management.exception.BookNotFoundException;
import com.library.management.model.Book;
import com.library.management.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImple  implements BookService{

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<Book> getAllBooks() {
        return  bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.getReferenceById(id);
    }

    @Override
    public Book addBook(BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest,Book.class);
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, BookRequest book) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            Book updatedBook = optionalBook.get();
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setPublicationYear(book.getPublicationYear());
            updatedBook.setIsbn(book.getIsbn());
            return bookRepository.save(updatedBook);
        }else {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }

    @Override
    public void deleteBook(Long id) {
        Optional<Book>optionalBook = bookRepository.findById(id);
        if(optionalBook.isPresent()){
            bookRepository.delete(optionalBook.get());
        } else {
            throw new BookNotFoundException("Book not found with id: " + id);
        }

    }
}

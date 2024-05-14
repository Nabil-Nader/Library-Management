package com.library.management.service;

import com.library.management.exception.BookAlreadyBorrowedException;
import com.library.management.exception.BookNotFoundException;
import com.library.management.exception.PatronNotFoundException;
import com.library.management.model.Book;
import com.library.management.model.BorrowingRecord;
import com.library.management.model.Patron;
import com.library.management.repository.BookRepository;
import com.library.management.repository.BorrowingRecordRepository;
import com.library.management.repository.PatronRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BorrowingRecordServiceImpl  implements BorrowingRecordService{

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final PatronRepository patronRepository;

    @Override
    @Transactional
    public void borrowBook(Long bookId, Long patronId) {
        BorrowingRecord existingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId);
        if (existingRecord != null) {
            throw new BookAlreadyBorrowedException("Book with id: " + bookId + " is already borrowed by patron with id: " + patronId);
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        borrowingRecordRepository.save(borrowingRecord);
    }

    @Override
    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        // Retrieve book and patron from database
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + patronId));

        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(book.getId(), patron.getId());
        if (borrowingRecord == null) {
            throw new BookAlreadyBorrowedException("Book with id: " + bookId + " is not borrowed by patron with id: " + patronId);
        }

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);
    }
}
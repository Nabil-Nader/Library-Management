package com.library.management.service;

import com.library.management.exception.*;
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
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));

        if (!book.isAvailableForBorrow()) {
            throw new BookNotAvailableException("Book with id: " + bookId + " is not available for borrowing.");
        }

        Patron patron = patronRepository.findById(patronId)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + patronId));

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBook(book);
        borrowingRecord.setPatron(patron);
        borrowingRecord.setBorrowingDate(LocalDate.now());

        borrowingRecordRepository.save(borrowingRecord);

        // Update book availability status
        book.setAvailableForBorrow(false);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void returnBook(Long bookId, Long patronId) {
        BorrowingRecord borrowingRecord = borrowingRecordRepository.findByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .orElseThrow(() -> new BorrowingRecordNotFoundException("Borrowing record not found for bookId: " + bookId + " and patronId: " + patronId));

        borrowingRecord.setReturnDate(LocalDate.now());
        borrowingRecordRepository.save(borrowingRecord);

        // Update book availability status
        Book book = borrowingRecord.getBook();
        book.setAvailableForBorrow(true);
        bookRepository.save(book);
    }
}

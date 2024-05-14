package com.library.management.controller;


import com.library.management.service.BorrowingRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BorrowingRecordController {


    private final BorrowingRecordService borrowingRecordService;

    @PreAuthorize("hasAnyRole('LIBRARIAN', 'PUBLIC_USER')")
    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok("Book with ID " + bookId + " has been successfully borrowed by Patron with ID " + patronId);
    }

    @PreAuthorize("hasAnyRole('LIBRARIAN', 'PUBLIC_USER')")

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok("Book with ID " + bookId + " has been successfully returned by Patron with ID " + patronId);
    }

}
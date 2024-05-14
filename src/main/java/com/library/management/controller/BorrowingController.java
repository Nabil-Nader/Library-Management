package com.library.management.controller;

import com.library.management.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BorrowingController {

    private final BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("/api/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.borrowBook(bookId, patronId);
        return ResponseEntity.ok("Book with ID: " + bookId + " has been successfully borrowed by patron with ID: " + patronId);
    }

    @PutMapping("/api/return/{bookId}/patron/{patronId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
        borrowingRecordService.returnBook(bookId, patronId);
        return ResponseEntity.ok("Book with ID: " + bookId + " has been successfully returned by patron with ID: " + patronId);
    }
}
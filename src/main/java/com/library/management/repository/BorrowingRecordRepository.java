package com.library.management.repository;

import com.library.management.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long>{



   Optional<BorrowingRecord>  findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
}

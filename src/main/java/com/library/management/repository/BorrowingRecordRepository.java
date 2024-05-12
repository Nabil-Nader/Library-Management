package com.library.management.repository;

import com.library.management.model.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long>{

   BorrowingRecord  findByBookIdAndPatronIdAndReturnDateIsNull(Long bookId, Long patronId);
}

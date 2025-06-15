package com.example.libraryapp.repository;

import com.example.libraryapp.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

    @Query("SELECT b FROM BorrowedBook b WHERE " +
            "((:bookTitle IS NULL AND :memberName IS NULL) OR " +
            " (LOWER(b.book.title) LIKE LOWER(CONCAT('%', :bookTitle, '%')) OR " +
            "  LOWER(b.member.name) LIKE LOWER(CONCAT('%', :memberName, '%')))) AND " +
            "(:borrowDate IS NULL OR b.borrowDate = :borrowDate) AND " +
            "(:returnDate IS NULL OR b.returnDate = :returnDate)")
    List<BorrowedBook> search(
            @Param("bookTitle") String bookTitle,
            @Param("memberName") String memberName,
            @Param("borrowDate") LocalDate borrowDate,
            @Param("returnDate") LocalDate returnDate
    );
}

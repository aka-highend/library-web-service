package com.example.libraryapp.repository;

import com.example.libraryapp.model.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    List<BorrowedBook> findByBook_TitleContainingIgnoreCaseOrMember_NameContainingIgnoreCase(String bookTitle, String memberName);
}

package com.example.libraryapp.repository;

import com.example.libraryapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(String title, String category);
}
package com.example.libraryapp.utils;

import com.example.libraryapp.dto.BookDTO;
import com.example.libraryapp.model.Book;

public class BookMapper {
    public static BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getCategory(),
                book.getPublishingYear(),
                book.getAuthor() != null ? book.getAuthor().getName() : "Unknown"
        );
    }
}

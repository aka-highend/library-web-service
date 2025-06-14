package com.example.libraryapp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.dto.BookDTO;
import com.example.libraryapp.utils.BookMapper;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/search")
    public List<BookDTO> searchBooks(@RequestParam String query) {
        return bookRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setCategory(updatedBook.getCategory());
            book.setPublishingYear(updatedBook.getPublishingYear());
            book.setAuthor(updatedBook.getAuthor());
            return bookRepository.save(book);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}

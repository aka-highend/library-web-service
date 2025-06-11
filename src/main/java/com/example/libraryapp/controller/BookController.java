package com.example.libraryapp.controller;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.repository.BookRepository;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
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

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String query) {
        return bookRepository.findByTitleContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query);
    }
}

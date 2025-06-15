package com.example.libraryapp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import com.example.libraryapp.model.Book;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.dto.BookDTO;
import com.example.libraryapp.utils.BookMapper;

@RestController
@RequestMapping("/api")
public class BookController {
    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // GET: http://localhost:8080/api/books
    @GetMapping("/books")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    // GET: http://localhost:8080/api/books/search?query=...
    @GetMapping("/books/search")
    public List<BookDTO> searchBooks(@RequestParam String query) {
        return bookRepository.findByTitleContainingIgnoreCase(query).stream()
                .map(BookMapper::toDTO)
                .collect(Collectors.toList());
    }

    // POST: http://localhost:8080/api/add-book
    @PostMapping("/add-book")
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    // PUT: http://localhost:8080/api/edit-book/{id}
    @PutMapping("/edit-book/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(updatedBook.getTitle());
            book.setCategory(updatedBook.getCategory());
            book.setPublishingYear(updatedBook.getPublishingYear());
            book.setAuthor(updatedBook.getAuthor());
            return bookRepository.save(book);
        }).orElseThrow();
    }

    // DELETE: http://localhost:8080/api/delete-book/{id}
    @DeleteMapping("/delete-book/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
    }
}

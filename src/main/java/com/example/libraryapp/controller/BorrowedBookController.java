package com.example.libraryapp.controller;

import com.example.libraryapp.model.BorrowedBook;
import com.example.libraryapp.repository.BorrowedBookRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowed")
public class BorrowedBookController {
    private final BorrowedBookRepository borrowedBookRepository;

    public BorrowedBookController(BorrowedBookRepository repository) {
        this.borrowedBookRepository = repository;
    }

    @GetMapping
    public List<BorrowedBook> getAll() {
        return borrowedBookRepository.findAll();
    }

    @PostMapping
    public BorrowedBook create(@RequestBody BorrowedBook entry) {
        return borrowedBookRepository.save(entry);
    }

    @PutMapping("/{id}")
    public BorrowedBook update(@PathVariable Long id, @RequestBody BorrowedBook updated) {
        return borrowedBookRepository.findById(id).map(b -> {
            b.setBook(updated.getBook());
            b.setMember(updated.getMember());
            b.setBorrowDate(updated.getBorrowDate());
            b.setReturnDate(updated.getReturnDate());
            return borrowedBookRepository.save(b);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        borrowedBookRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<BorrowedBook> searchBorrowedBooks(@RequestParam String query) {
        return borrowedBookRepository.findByBook_TitleContainingIgnoreCaseOrMember_NameContainingIgnoreCase(query, query);
    }
}

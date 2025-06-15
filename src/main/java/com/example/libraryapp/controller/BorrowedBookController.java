package com.example.libraryapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import com.example.libraryapp.dto.BorrowedBookDTO;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.BorrowedBook;
import com.example.libraryapp.model.Member;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.BorrowedBookRepository;
import com.example.libraryapp.repository.MemberRepository;

@RestController
@RequestMapping("/api")
public class BorrowedBookController {
    private final BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public BorrowedBookController(BorrowedBookRepository repository) {
        this.borrowedBookRepository = repository;
    }

    // GET: http://localhost:8080/api/borrowed
    @GetMapping("/borrowed")
    public List<BorrowedBook> getAll() {
        return borrowedBookRepository.findAll();
    }

    // POST: http://localhost:8080/api/add-borrowed
    @PostMapping("/add-borrowed")
    public BorrowedBook create(@RequestBody BorrowedBookDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setBorrowDate(dto.getBorrowDate());
        borrowedBook.setReturnDate(dto.getReturnDate());
        borrowedBook.setBook(book);
        borrowedBook.setMember(member);

        return borrowedBookRepository.save(borrowedBook);
    }

    // PUT: http://localhost:8080/api/edit-borrowed/{id}
    @PutMapping("/edit-borrowed/{id}")
    public BorrowedBook update(@PathVariable Long id, @RequestBody BorrowedBookDTO dto) {
        return borrowedBookRepository.findById(id).map(b -> {
            Book book = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            Member member = memberRepository.findById(dto.getMemberId())
                    .orElseThrow(() -> new RuntimeException("Member not found"));

            b.setBorrowDate(dto.getBorrowDate());
            b.setReturnDate(dto.getReturnDate());
            b.setBook(book);
            b.setMember(member);

            return borrowedBookRepository.save(b);
        }).orElseThrow(() -> new RuntimeException("BorrowedBook not found"));
    }

    // DELETE: http://localhost:8080/api/delete-borrowed/{id}
    @DeleteMapping("/delete-borrowed/{id}")
    public void delete(@PathVariable Long id) {
        borrowedBookRepository.deleteById(id);
    }

    // GET: http://localhost:8080/api/borrowed/search
    @GetMapping("/borrowed/search")
    public List<BorrowedBook> searchBorrowedBooks(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) String memberName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate borrowDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate
    ) {
        return borrowedBookRepository.search(bookTitle, memberName, borrowDate, returnDate);
    }
}

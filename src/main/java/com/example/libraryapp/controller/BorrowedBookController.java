package com.example.libraryapp.controller;

import com.example.libraryapp.dto.BorrowedBookDTO;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.BorrowedBook;
import com.example.libraryapp.model.Member;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.BorrowedBookRepository;
import com.example.libraryapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/borrowed")
public class BorrowedBookController {
    private final BorrowedBookRepository borrowedBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public BorrowedBookController(BorrowedBookRepository repository) {
        this.borrowedBookRepository = repository;
    }

    @GetMapping
    public List<BorrowedBook> getAll() {
        return borrowedBookRepository.findAll();
    }

    @PostMapping
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

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        borrowedBookRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<BorrowedBook> searchBorrowedBooks(
            @RequestParam(required = false) String bookTitle,
            @RequestParam(required = false) String memberName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate borrowDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate
    ) {
        return borrowedBookRepository.search(bookTitle, memberName, borrowDate, returnDate);
    }

}

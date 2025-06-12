package com.example.libraryapp.controller;

import com.example.libraryapp.dto.BorrowedBookDTO;
import com.example.libraryapp.model.Book;
import com.example.libraryapp.model.BorrowedBook;
import com.example.libraryapp.model.Member;
import com.example.libraryapp.repository.BookRepository;
import com.example.libraryapp.repository.BorrowedBookRepository;
import com.example.libraryapp.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

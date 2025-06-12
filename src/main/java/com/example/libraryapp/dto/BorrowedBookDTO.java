package com.example.libraryapp.dto;

import java.time.LocalDate;

public class BorrowedBookDTO {
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private Long bookId;
    private Long memberId;

    // Getters and Setters
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }
}

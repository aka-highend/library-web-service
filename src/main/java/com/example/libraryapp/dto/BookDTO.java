package com.example.libraryapp.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String category;
    private int publishingYear;
    private String author;

    public BookDTO() {}

    public BookDTO(Long id, String title, String category, int publishingYear, String author) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.publishingYear = publishingYear;
        this.author = author;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getPublishingYear() { return publishingYear; }
    public void setPublishingYear(int publishingYear) { this.publishingYear = publishingYear; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
}

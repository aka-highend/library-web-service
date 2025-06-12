package com.example.libraryapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private int publishingYear;

    // Many books belong to one author
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    public Book() {}

    public Book(String title, String category, int publishingYear, Author author) {
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

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}

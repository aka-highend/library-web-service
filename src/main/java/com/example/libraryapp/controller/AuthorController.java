package com.example.libraryapp.controller;

import com.example.libraryapp.model.Author;
import com.example.libraryapp.repository.AuthorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        return authorRepository.findById(id).map(author -> {
            author.setName(updatedAuthor.getName());
            return authorRepository.save(author);
        }).orElseThrow();
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<Author> searchAuthors(@RequestParam String query) {
        return authorRepository.findByNameContainingIgnoreCase(query);
    }
}

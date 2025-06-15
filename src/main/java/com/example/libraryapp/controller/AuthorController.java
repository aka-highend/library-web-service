package com.example.libraryapp.controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.example.libraryapp.model.Author;
import com.example.libraryapp.repository.AuthorRepository;

@RestController
@RequestMapping("/api")
public class AuthorController {
    private final AuthorRepository authorRepository;

    public AuthorController(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // GET: http://localhost:8080/api/authors
    @GetMapping("/authors")
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // POST: http://localhost:8080/api/add-author
    @PostMapping("/add-author")
    public Author createAuthor(@RequestBody Author author) {
        return authorRepository.save(author);
    }

    // PUT: http://localhost:8080/api/edit-author/{id}
    @PutMapping("/edit-author/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author updatedAuthor) {
        return authorRepository.findById(id).map(author -> {
            author.setName(updatedAuthor.getName());
            return authorRepository.save(author);
        }).orElseThrow();
    }

    // DELETE: http://localhost:8080/api/delete-author/{id}
    @DeleteMapping("/delete-author/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorRepository.deleteById(id);
    }

    // GET: http://localhost:8080/api/authors/search?query=...
    @GetMapping("/authors/search")
    public List<Author> searchAuthors(@RequestParam String query) {
        return authorRepository.findByNameContainingIgnoreCase(query);
    }
}

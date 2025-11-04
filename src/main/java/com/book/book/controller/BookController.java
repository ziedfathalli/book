package com.book.book.controller;

import com.book.book.model.Book;
import com.book.book.repository.BookRepository;
import com.book.book.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin("*")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping(params = "id")
    public Book getBookById(@RequestParam long id) {
        return bookService.getBookById(id).orElse(null);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        return bookService.getBookById(book.getId())
                .map(existing -> {
                    existing.setTitle(book.getTitle());
                    existing.setAuthor(book.getAuthor());
                    return bookService.updateBook(book);
                })
                .orElse(null);
    }

    @DeleteMapping
    public void deleteBookById(@RequestParam long id) {
        bookService.deleteBook(id);
    }
}

package com.esempla.proxy.controller;

import com.esempla.proxy.model.Book;
import com.esempla.proxy.model.dto.AuthenticationDto;
import com.esempla.proxy.model.dto.BookSaveDto;
import com.esempla.proxy.service.BookService;
import com.itextpdf.text.DocumentException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/download")
    public ResponseEntity<Void> putBooksPdf() throws IOException, DocumentException {
        bookService.downloadBookAsPdf();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/books/show")
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> books = bookService.getAll();
        return ResponseEntity.ok().body(books);
    }

    @PostMapping("/books/add")
    public ResponseEntity<Book> addBook(@RequestBody BookSaveDto dto) {
        Book book = bookService.addBook(dto);
        return ResponseEntity.ok().body(book);
    }

    @GetMapping("/books/get/{isbn}")
    public ResponseEntity<Book> getBook(@PathVariable String isbn) {
        Book book=bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok().body(book);
    }

    @PutMapping("/books/update/{isbn}")
    public ResponseEntity<Book> updateBook(@PathVariable String isbn,@RequestBody BookSaveDto bookSaveDto) {
            Book book = bookService.updateBook(isbn,bookSaveDto);
            return ResponseEntity.ok().body(book);
        }

    @DeleteMapping("/books/delete/{isbn}")
    public ResponseEntity<Void> deleteBook(@PathVariable String isbn) {
       bookService.deleteBook(isbn);
        return ResponseEntity.noContent().build();
    }
    }


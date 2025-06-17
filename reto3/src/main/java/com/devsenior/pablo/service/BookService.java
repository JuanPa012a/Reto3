package com.devsenior.pablo.service;

import java.util.ArrayList;
import java.util.List;

import com.devsenior.pablo.exception.BookNotFoundException;
import com.devsenior.pablo.model.Book;

public class BookService {
    private final List<Book> books;

    public BookService() {
        books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(Book book) {
        books.remove(book);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(String id) {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con el ID: " + id));
    }

    public Book getBookByIsbn(String isbn) {
        return books.stream()
                .filter(b -> b.getIsbn().equals(isbn))
                .findFirst()
                .orElseThrow(() -> new BookNotFoundException("Libro no encontrado con el ISBN: " + isbn));
    }

    public Boolean isEmpty(){
        if(books.isEmpty()){
            throw new BookNotFoundException("No hay libros registrados en el momento");
        }
        return true;
    }

}

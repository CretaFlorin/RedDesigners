package service;

import domain.Book;
import repository.BookRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class BookController {
    BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public Book getBookById(Integer id) {
        return this.bookRepository.getEntityById(id);
    }

    public void createBook(String name, String author, Float price) {
        this.bookRepository.createEntity(new Book(name, author, price));
    }

    public void updateBook(Integer id, String name, String author, Float price) {
        this.bookRepository.updateEntity(new Book(id, name, author, price));
    }

    public void removeBook(Integer id) {
        this.bookRepository.deleteEntity(
                this.bookRepository.getBooks()
                        .stream()
                        .filter(b -> Objects.equals(b.getId(), id))
                        .findFirst().orElse(null)
        );
    }

    public ArrayList<Book> filterByPrice(Float min, Float max) {
        return this.bookRepository.getBooks()
                .stream()
                .filter(b -> b.getPrice() >= min && b.getPrice() <= max).collect(Collectors.toCollection(ArrayList::new));
    }
}
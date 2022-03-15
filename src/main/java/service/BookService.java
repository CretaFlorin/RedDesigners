package service;

import domain.Book;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class BookService {
    private final Repository<Long, Book> repository;

    public BookService(Repository<Long, Book> repository) {
        this.repository = repository;
    }

    public void addBook(Book book) throws ValidatorException {
        repository.save(book);
    }

    public Set<Book> getAllBooks() {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
    }


    public Set<Book> filterBooksByName(String s) {
        Iterable<Book> Books = repository.findAll();
        //version 1
//        Set<Book> filteredBooks = StreamSupport.stream(Books.spliterator(), false)
//                .filter(Book -> Book.getName().contains(s)).collect(Collectors.toSet());

        //version 2
        Set<Book> filteredBooks = new HashSet<>();
        Books.forEach(filteredBooks::add);
        filteredBooks.removeIf(Book -> !Book.getName().contains(s));

        return filteredBooks;
    }
}

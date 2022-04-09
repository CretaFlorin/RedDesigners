package service;

import domain.Book;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.ArrayList;
import java.util.Collection;
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

    public void updateBook(Book newBook) {
        repository.update(newBook);
    }

    public void removeBook(Long bookId) {
        repository.delete(bookId);
    }


    public Set<Book> getAllBooks() {
        Iterable<Book> books = repository.findAll();
        return StreamSupport.stream(books.spliterator(), false).collect(Collectors.toSet());
    }

    public ArrayList<Book> filterByPrice(Double min, Double max) {
        return new ArrayList<Book>((Collection<? extends Book>) this.repository.findAll())
                .stream()
                .filter(b -> b.getPrice() >= min && b.getPrice() <= max).collect(Collectors.toCollection(ArrayList::new));
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

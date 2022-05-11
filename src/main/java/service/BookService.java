package service;

import domain.Book;
import domain.Purchase;
import domain.validators.ValidatorException;
import repository.Repository;

import java.util.*;
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

    public List<Map.Entry<Long, Integer>> bestSellersList(Set<Purchase> purchases) {
        HashMap<Long, Integer> booksFr = new HashMap<>();
        purchases.forEach(p -> {
            int count = booksFr.getOrDefault(p.getBookId(), 0);
            booksFr.put(p.getBookId(), count + 1);
        });
        return booksFr.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    if(Objects.equals(e1.getValue(), e2.getValue()))
                        return e1.getKey().compareTo(e2.getKey());
                    if( e1.getValue() < e2.getValue())
                        return 1;
                    return -1;
                })
                .collect(Collectors.toList());
    }

    public Set<Book> filterBooksByName(String s) {
        Iterable<Book> Books = repository.findAll();
        Set<Book> filteredBooks = new HashSet<>();
        Books.forEach(filteredBooks::add);
        filteredBooks.removeIf(Book -> !Book.getName().contains(s));
        return filteredBooks;
    }


}

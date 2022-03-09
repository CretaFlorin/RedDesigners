package domain;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {
    private static final AtomicInteger count = new AtomicInteger(0);
    Integer id;
    String name;
    ArrayList<Book> boughtBooks;

    public Client(String name) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.boughtBooks = new ArrayList<>();
    }

    public Client(String name, ArrayList<Book> boughtBooks) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.boughtBooks = boughtBooks;
    }

    public Client(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.boughtBooks = new ArrayList<>();
    }

    // -- GETTERS --
    public String getName() {
        return name;
    }

    public ArrayList<Book> getBoughtBooks() {
        return boughtBooks;
    }

    public Integer getId() {
        return id;
    }

    public Float getSpentMoney() {
        return this.boughtBooks
                .stream()
                .map(Book::getPrice)
                .reduce(0F, Float::sum);
    }

    // -- SETTERS --
    public void setName(String name) {
        this.name = name;
    }

    public void setBoughtBooks(ArrayList<Book> boughtBooks) {
        this.boughtBooks = boughtBooks;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", boughtBooks=" + boughtBooks.toString() +
                '}';
    }
}

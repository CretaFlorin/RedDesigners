package domain;

import java.util.concurrent.atomic.AtomicInteger;

public class Book {
    private static final AtomicInteger count = new AtomicInteger(0);
    Integer id;
    String name;
    String author;
    Float price;

    public Book(String name, String author, Float price) {
        this.id = count.incrementAndGet();
        this.name = name;
        this.author = author;
        this.price = price;
    }

    public Book(Integer id, String name, String author, Float price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
    }

    // -- GETTERS --
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Float getPrice() {
        return price;
    }

    // -- SETTERS --
    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                '}';
    }
}

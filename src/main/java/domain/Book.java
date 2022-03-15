package domain;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Book extends BaseEntity<Long> {
    String name;
    String author;
    Double price;

    public Book(String name, String author, Double price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }

    // -- GETTERS --
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public Double getPrice() {
        return price;
    }

    // -- SETTERS --
    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!this.name.equals(book.getName())) return false;
        if (!this.author.equals(book.getAuthor())) return false;
        return this.price.equals(book.getPrice());
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + this.getId() +
                ", name='" + this.name + '\'' +
                ", author='" + this.author + '\'' +
                ", price=" + this.price +
                '}';
    }
}

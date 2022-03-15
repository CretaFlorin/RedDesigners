package domain;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Client extends BaseEntity<Long>{
    String name;
    ArrayList<Book> boughtBooks;

    public Client(String name) {
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


    public Double getSpentMoney() {
        return this.boughtBooks
                .stream()
                .map(Book::getPrice)
                .reduce(0D, Double::sum);
    }

    // -- SETTERS --
    public void setName(String name) {
        this.name = name;
    }

    public void setBoughtBooks(ArrayList<Book> boughtBooks) {
        this.boughtBooks = boughtBooks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (!Objects.equals(this.name, client.getName())) return false;
        return this.boughtBooks.equals(client.getBoughtBooks());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + this.getId() +
                ", name='" + name + '\'' +
                ", boughtBooks=" + boughtBooks.toString() +
                '}';
    }
}

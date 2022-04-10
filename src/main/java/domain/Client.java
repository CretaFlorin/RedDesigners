package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Client extends BaseEntity<Long> {
    String name;
    ArrayList<Long> boughtBooks;

    public Client(String name) {
        this.name = name;
        this.boughtBooks = new ArrayList<>();
    }

    // -- GETTERS --
    public String getName() {
        return name;
    }

    // -- SETTERS --
    public void setName(String name) {
        this.name = name;
    }


//    public Double getSpentMoney() {
//        return this.boughtBooks.stream().map(Book::getPrice).reduce(0D, Double::sum);
//    }

    public ArrayList<Long> getBoughtBooks() {
        return boughtBooks;
    }

    public void setBoughtBooks(ArrayList<Long> boughtBooks) {
        this.boughtBooks = boughtBooks;
    }

    public void buyBook(Long bookId) {
        this.boughtBooks.add(bookId);
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
        return "Client{" + "id=" + this.getId() + ", name='" + name + '\'' + ", boughtBooks=" + boughtBooks.toString() + '}';
    }
}

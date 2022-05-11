package domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Purchase extends BaseEntity<Long> {
    Long clientId;
    Long bookId;
    Date date;

    public Purchase(Long clientId, Long bookId, Date date) {
        this.clientId = clientId;
        this.bookId = bookId;
        this.date = date;
    }

    // -- GETTERS --
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getClientId() {
        return clientId;
    }

    // -- SETTERS --
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Purchase)) return false;

        Purchase purchase = (Purchase) o;

        return Objects.equals(bookId, purchase.bookId) && Objects.equals(clientId, purchase.clientId) && Objects.equals(date, purchase.date);
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "clientId=" + clientId +
                ", bookId=" + bookId +
                ", date=" + new SimpleDateFormat("dd/MM/yyyy").format(date) +
                '}';
    }
}
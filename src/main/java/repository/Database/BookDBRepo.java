package repository.Database;

import domain.Book;
import domain.validators.StoreException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class BookDBRepo extends DatabaseRepo<Long, Book> {

    public BookDBRepo() {
        super();
    }

    @Override
    public void addToMemory(Book book) throws StoreException {
        String sql = "Insert into books values(?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, book.getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setDouble(4, book.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromMemory(Long id) throws StoreException {
        String sql = "delete from books where idBook=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateMemory(Book book) throws StoreException {
        String sql = "update books set name=?, author=?, price=? where idBook=?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, book.getName());
            statement.setString(2, book.getAuthor());
            statement.setDouble(3,book.getPrice());
            statement.setLong(4, book.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadFromMemory() throws StoreException {
        String sql = "Select * from books";
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                Book book = new Book(set.getString("name"), set.getString("author"), set.getDouble("price"));
                book.setId(set.getLong("idBook"));
                entities.put(book.getId(), book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
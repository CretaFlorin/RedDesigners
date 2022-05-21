package repository.Database;

import domain.Client;
import domain.validators.StoreException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ClientDBRepo extends DatabaseRepo<Long, Client> {

    public ClientDBRepo() {
        super();
    }

    @Override
    public void addToMemory(Client client) throws StoreException {
        String sql = "Insert into clients values(?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, client.getId());
            statement.setString(2, client.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromMemory(Long id) throws StoreException {
        String sql = "delete from clients where idClient=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateMemory(Client client) throws StoreException {
        String sql = "update clients set name=? where idClient=?;";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, client.getName());
            statement.setLong(2, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadFromMemory() throws StoreException {
        String sql = "Select * from clients";
        try {
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            while (set.next()) {
                Client client = new Client(set.getString("name"));
                client.setId(set.getLong("idClient"));
                entities.put(client.getId(), client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
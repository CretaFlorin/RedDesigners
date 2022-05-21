package repository.Database;


import domain.BaseEntity;
import domain.validators.StoreException;
import domain.validators.ValidatorException;
import repository.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class DatabaseRepo<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {
    protected Map<ID, T> entities;
    protected Connection connection;

    /**
     * The constructor for the database repository class
     */
    public DatabaseRepo() throws StoreException {
        this.entities = new HashMap<>();
        String url = "jdbc:postgresql://localhost:5432/SDI";
        String userName = "postgres";
        String password = "postgres";
        try {
            this.connection = DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        this.loadFromMemory();
    }


    @Override
    public Optional<T> findOne(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
//        return entities.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toSet());
        return entities.values();
    }

    @Override
    public Optional<T> save(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<T> opt = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
        this.addToMemory(entity);
        return opt;
    }

    @Override
    public Optional<T> delete(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }
        Optional<T> opt = Optional.ofNullable(entities.remove(id));
        this.deleteFromMemory(id);
        return opt;
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
        Optional<T> opt = Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
        this.updateMemory(entity);
        return opt;
    }

    public abstract void addToMemory(T entity) throws StoreException;

    public abstract void deleteFromMemory(ID id) throws StoreException;

    public abstract void loadFromMemory() throws StoreException;

    public abstract void updateMemory(T entity) throws StoreException;
}

package repository;

public interface Repository<T> {

    T getEntityById(Integer id);

    void createEntity(T entity);

    void updateEntity(T entity);

    void deleteEntity(T entity);
}
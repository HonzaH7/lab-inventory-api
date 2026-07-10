package cz.jan.labinventoryapi.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<ID, T> {
    void save(ID id, T entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
}


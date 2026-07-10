package cz.jan.labinventoryapi.repository;


import java.util.*;

public class InMemoryRepository<ID, T> implements Repository<ID, T> {
    private final Map<ID, T> repo = new HashMap<>();

    @Override
    public void save(ID id, T entity) {
        repo.put(id, entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(repo.get(id));
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public void deleteById(ID id) {
        repo.remove(id);
    }

}

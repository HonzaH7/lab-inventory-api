package cz.jan.labinventoryapi.service;

import cz.jan.labinventoryapi.dto.ReagentRequest;
import cz.jan.labinventoryapi.exception.ReagentNotFoundException;
import cz.jan.labinventoryapi.model.Category;
import cz.jan.labinventoryapi.model.Reagent;
import cz.jan.labinventoryapi.model.Unit;
import cz.jan.labinventoryapi.repository.ReagentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReagentService {

    private final ReagentRepository repository;
    private final AtomicLong sequence = new AtomicLong(0);

    public ReagentService(ReagentRepository repository,
                          @Value("${lab.seed-data:false}") boolean seedData) {
        this.repository = repository;
        if (seedData) {
            seed();
        }
    }

    public Reagent getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReagentNotFoundException(id));
    }

    public List<Reagent> findAll() {
        return repository.findAll();
    }

    public Reagent create(ReagentRequest request) {
        return create(
                request.name(),
                request.category(),
                request.amount(),
                request.unit(),
                request.expiration()
        );
    }

    public void deleteById(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    public Reagent update(Long id, ReagentRequest request) {
        getById(id);
        Reagent updated = new Reagent(
                id,
                request.name(),
                request.category(),
                request.amount(),
                request.unit(),
                request.expiration()
        );
        repository.save(id, updated);
        return updated;
    }

    private Reagent create(String name, Category category, double amount, Unit unit, LocalDate expiration) {
        Long id = sequence.incrementAndGet();
        Reagent reagent = new Reagent(id, name, category, amount, unit, expiration);
        repository.save(id, reagent);
        return reagent;
    }

    private void seed() {
        create("Kyselina sírová", Category.ACID, 500, Unit.ML, LocalDate.of(2027, 1, 1));
        create("Hydroxid sodný", Category.BASE, 250, Unit.G, LocalDate.of(2026, 12, 31));
    }
}
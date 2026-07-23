package cz.jan.labinventoryapi.service;

import cz.jan.labinventoryapi.dto.ReagentRequest;
import cz.jan.labinventoryapi.exception.ReagentNotFoundException;
import cz.jan.labinventoryapi.model.Category;
import cz.jan.labinventoryapi.model.Reagent;
import cz.jan.labinventoryapi.model.Unit;
import cz.jan.labinventoryapi.repository.ReagentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReagentService {

    private final ReagentRepository repository;

    public ReagentService(ReagentRepository repository) {
        this.repository = repository;
    }

    public Reagent getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ReagentNotFoundException(id));
    }

    public List<Reagent> findAll() {
        return repository.findAll();
    }


    @Transactional
    public Reagent create(ReagentRequest request) {
        return create(
                request.name(),
                request.category(),
                request.amount(),
                request.unit(),
                request.expiration()
        );
    }

    @Transactional
    public void deleteById(Long id) {
        getById(id);
        repository.deleteById(id);
    }

    @Transactional
    public Reagent update(Long id, ReagentRequest request) {
        Reagent reagent = getById(id);
        reagent.setName(request.name());
        reagent.setCategory(request.category());
        reagent.setAmount(request.amount());
        reagent.setUnit(request.unit());
        reagent.setExpiration(request.expiration());

        return repository.save(reagent);
    }

    private Reagent create(String name, Category category, double amount, Unit unit, LocalDate expiration) {
        return repository.save(new Reagent(name, category, amount, unit, expiration));
    }
}
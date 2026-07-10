package cz.jan.labinventoryapi.repository;

import cz.jan.labinventoryapi.model.Reagent;
import org.springframework.stereotype.Repository;

@Repository
public class ReagentRepository extends InMemoryRepository<Long, Reagent> {
}
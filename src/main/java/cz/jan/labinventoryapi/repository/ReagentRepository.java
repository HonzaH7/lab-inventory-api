package cz.jan.labinventoryapi.repository;

import cz.jan.labinventoryapi.model.Reagent;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReagentRepository extends JpaRepository<Reagent, Long> {
}
package cz.jan.labinventoryapi.dto;

import cz.jan.labinventoryapi.model.Category;
import cz.jan.labinventoryapi.model.Reagent;
import cz.jan.labinventoryapi.model.Unit;

import java.time.LocalDate;

public record ReagentResponse(
        Long id,
        String name,
        Category category,
        double amount,
        Unit unit,
        LocalDate expiration
) {
    public static ReagentResponse from(Reagent reagent) {
        return new ReagentResponse(
                reagent.getId(),
                reagent.getName(),
                reagent.getCategory(),
                reagent.getAmount(),
                reagent.getUnit(),
                reagent.getExpiration()
        );
    }

}

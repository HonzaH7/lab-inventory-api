package cz.jan.labinventoryapi.dto;

import cz.jan.labinventoryapi.model.Category;
import cz.jan.labinventoryapi.model.Unit;

import java.time.LocalDate;

public record ReagentRequest(
        String name,
        Category category,
        double amount,
        Unit unit,
        LocalDate expiration
) {}

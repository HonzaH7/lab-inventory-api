package cz.jan.labinventoryapi.dto;

import cz.jan.labinventoryapi.model.Category;
import cz.jan.labinventoryapi.model.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record ReagentRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotNull(message = "category is required")
        Category category,

        @PositiveOrZero(message = "amount cannot be negative")
        double amount,

        @NotNull(message = "unit is required")
        Unit unit,

        LocalDate expiration
) {}

package cz.jan.labinventoryapi.model;

import java.time.LocalDate;
import java.util.Objects;

public class Reagent {
    private final Long id;
    private final String name;
    private final Category category;
    private final double amount;
    private final Unit unit;
    private final LocalDate expiration;

    public Reagent(Long id, String name, Category category, double amount, Unit unit, LocalDate expiration) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("id cannot be null or <= 0");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative (" + amount + ")");
        }
        this.id = id;
        this.name = name;
        this.category = Objects.requireNonNull(category, "category cannot be null");
        this.unit = Objects.requireNonNull(unit, "unit cannot be null");
        this.amount = amount;
        this.expiration = expiration;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reagent reagent = (Reagent) o;
        return Objects.equals(id, reagent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

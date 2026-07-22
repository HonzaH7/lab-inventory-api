package cz.jan.labinventoryapi.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Reagent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit unit;

    private LocalDate expiration;

    public Reagent(String name, Category category, double amount, Unit unit, LocalDate expiration) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }

        if (amount < 0) {
            throw new IllegalArgumentException("amount cannot be negative (" + amount + ")");
        }

        this.name = name;
        this.category = Objects.requireNonNull(category, "category cannot be null");
        this.unit = Objects.requireNonNull(unit, "unit cannot be null");
        this.amount = amount;
        this.expiration = expiration;
    }

    protected Reagent() {

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

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
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

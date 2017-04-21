package cz.janhrcek.tifi.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Expense {
    @Id
    @GeneratedValue
    private int id;
    private LocalDate date;
    private int amount;
    private String category;
    private String subcategory;
    private String description;

    public Expense() {
    }

    public Expense(LocalDate date, int amount, String category, String subcategory, String description) {
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.subcategory = subcategory;
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }
}

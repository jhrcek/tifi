package cz.janhrcek.tifi.ui;

import cz.janhrcek.tifi.model.Expense;
import javafx.beans.property.*;

import java.time.LocalDate;

public class ExpenseUiModel {
    private final IntegerProperty id;
    private final ObjectProperty<LocalDate> date;
    private final IntegerProperty amount;
    private final StringProperty category;
    private final StringProperty subcategory;
    private final StringProperty description;

    public static ExpenseUiModel wrap(Expense e) {
        return new ExpenseUiModel(e.getId(), e.getDate(), e.getAmount(), e.getCategory(), e.getSubcategory(), e.getDescription());
    }

    private ExpenseUiModel(int id, LocalDate date, Integer amount, String category, String subcategory, String lastName) {
        this.id = new SimpleIntegerProperty(this, "id", id);
        this.date = new SimpleObjectProperty<>(this, "date", date);
        this.amount = new SimpleIntegerProperty(this, "amount", amount);
        this.category = new SimpleStringProperty(this, "category", category);
        this.subcategory = new SimpleStringProperty(this, "subcategory", subcategory);
        this.description = new SimpleStringProperty(this, "description", lastName);
    }

    public Integer getId() {
        return id.get();
    }

    public Integer getAmount() {
        return amount.get();
    }

    public String getDescription() {
        return description.get();
    }

    public LocalDate getDate() {
        return date.get();
    }

    public String getCategory() {
        return category.get();
    }

    public String getSubcategory() {
        return subcategory.get();
    }
}

package cz.janhrcek.tifi.ui;

import cz.janhrcek.tifi.model.Expense;
import javafx.beans.property.*;

import java.time.LocalDate;

public class ExpenseUiModel {

    private final ObjectProperty<LocalDate> date;
    private final IntegerProperty amount;
    private final StringProperty category;
    private final StringProperty subcategory;
    private final StringProperty description;

    public static ExpenseUiModel wrap(Expense e) {
        return new ExpenseUiModel(e.getDate(), e.getAmount(), e.getCategory(), e.getSubcategory(), e.getDescription());
    }

    private ExpenseUiModel(LocalDate date, Integer amount, String category, String subcategory, String lastName) {
        this.date = new SimpleObjectProperty<>(this, "date", date);
        this.amount = new SimpleIntegerProperty(this, "amount", amount);
        this.category = new SimpleStringProperty(this, "category", category);
        this.subcategory = new SimpleStringProperty(this, "subcategory", subcategory);
        this.description = new SimpleStringProperty(this, "description", lastName);
    }

    public Integer getAmount() {
        return amount.get();
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty dateProperty() {
        return date;
    }

    public String getCategory() {
        return category.get();
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getSubcategory() {
        return subcategory.get();
    }

    public StringProperty subcategoryProperty() {
        return subcategory;
    }

}

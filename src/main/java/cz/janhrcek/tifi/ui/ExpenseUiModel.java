package cz.janhrcek.tifi.ui;

import cz.janhrcek.tifi.model.Expense;
import javafx.beans.property.*;

import java.time.LocalDate;

public class ExpenseUiModel {

    private final ObjectProperty<LocalDate> date;
    private final IntegerProperty amount;
    private final StringProperty description;

    public static ExpenseUiModel wrap(Expense e) {
        return new ExpenseUiModel(e.getDate(), e.getAmount(), e.getDescription());
    }

    private ExpenseUiModel(LocalDate date, Integer amount, String lastName) {
        this.date = new SimpleObjectProperty<>(this, "date", date);
        this.amount = new SimpleIntegerProperty(this, "amount", amount);
        this.description = new SimpleStringProperty(this, "description", lastName);
    }

    public Integer getAmount() {
        return amount.get();
    }

    public void setAmount(Integer value) {
        amount.set(value);
    }

    public IntegerProperty amountProperty() {
        return amount;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate value) {
        date.set(value);
    }

    public ObjectProperty dateProperty() {
        return date;
    }
}

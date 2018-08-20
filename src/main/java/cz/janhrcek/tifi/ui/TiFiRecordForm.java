package cz.janhrcek.tifi.ui;

import cz.janhrcek.tifi.model.Categories;
import cz.janhrcek.tifi.model.Expense;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

class TiFiRecordForm extends HBox {

    private final DatePicker datePicker;
    private final TextField amountField;
    private final ChoiceBox<String> catDropdown;
    private final ChoiceBox<String> subcatDropdown;
    private final TextField descriptionField;
    private static final Categories cats = Categories.INSTANCE;

    public TiFiRecordForm(Consumer<Expense> newExpenseCallback) {
        this.datePicker = new DatePicker(LocalDate.now());
        datePicker.setMinWidth(120);

        this.amountField = new TextField();
        amountField.setPromptText("Amount");
        amountField.setMinWidth(70);
        // Allow numbers only
        amountField.lengthProperty().addListener((observable, oldLength, newLength) -> {
            if (newLength.intValue() > oldLength.intValue()) {
                String input = amountField.getText();
                char ch = input.charAt(oldLength.intValue());
                if (!('0' <= ch && ch <= '9')) {
                    amountField.setText(input.substring(0, input.length() - 1));
                }
            }
        });

        this.subcatDropdown = new ChoiceBox<>();
        subcatDropdown.setMinWidth(100);

        this.catDropdown = new ChoiceBox<>();
        catDropdown.getItems().addAll(cats.getCategories());
        catDropdown.setMinWidth(140);
        catDropdown.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    subcatDropdown.getItems().clear();

                    List<String> subcats = cats.getSubcategories(newValue);

                    if (subcats.isEmpty()) {
                        subcatDropdown.setDisable(true);
                    } else {
                        subcatDropdown.setDisable(false);
                        subcatDropdown.getItems().addAll(subcats);
                    }
                });


        this.descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        descriptionField.setMinWidth(150);

        Button addButton = new Button("Add");
        addButton.setMinWidth(50);
        addButton.setOnAction(event -> getData().ifPresent(newExpenseCallback));

        getChildren().addAll(datePicker, amountField, catDropdown, subcatDropdown, descriptionField, addButton);
    }

    private void clearForm() {
        amountField.setText("");
        descriptionField.setText("");
    }

    private Optional<Expense> getData() {
        LocalDate date = datePicker.getValue();
        String amtStr = amountField.getText();
        String cat = catDropdown.getValue();
        String subcat = subcatDropdown.getValue();
        String desc = descriptionField.getText();
        if (date != null && !amtStr.isEmpty()) {
            clearForm();
            return Optional.of(new Expense(date, Integer.parseInt(amtStr), cat, subcat, desc));
        } else {
            return Optional.empty();
        }
    }
}

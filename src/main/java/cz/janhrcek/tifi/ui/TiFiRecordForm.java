package cz.janhrcek.tifi.ui;

import cz.janhrcek.tifi.model.Expense;
import cz.janhrcek.tifi.storage.Storage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.time.LocalDate;
import java.util.Optional;

public class TiFiRecordForm extends HBox {
    private final DatePicker datePicker;
    private final TextField amountField;
    private final TextField descriptionField;
    private final Storage storage;

    public TiFiRecordForm(Storage storage) {
        this.storage = storage;
        this.datePicker = new DatePicker(LocalDate.now());
        datePicker.setMinWidth(110);

        this.amountField = new TextField();
        amountField.setPromptText("Amount");
        amountField.lengthProperty().addListener(ALLOW_NUMBERS_ONLY);

        this.descriptionField = new TextField();
        descriptionField.setPromptText("Description");
        descriptionField.setMinWidth(150);

        Button addButton = new Button("Add");
        addButton.setMinWidth(50);
        addButton.setOnAction(ADD_BUTTON_CLICK_HANDLER);

        getChildren().addAll(datePicker, amountField, descriptionField, addButton);
    }

    private void clearForm() {
        amountField.setText("");
        descriptionField.setText("");
    }

    /*
    * Listens to length of text field that doesn't allow anything else to be entered than numbers.
    */
    private final ChangeListener<Number> ALLOW_NUMBERS_ONLY = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> observable, Number oldLength, Number newLength) {
            if (newLength.intValue() > oldLength.intValue()) {
                String input = amountField.getText();
                char ch = input.charAt(oldLength.intValue());
                if (!('0' <= ch && ch <= '9')) {
                    amountField.setText(input.substring(0, input.length() - 1));
                }
            }
        }
    };

    private final EventHandler<ActionEvent> ADD_BUTTON_CLICK_HANDLER = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            getData().ifPresent(storage::addExpense);
        }

        private Optional<Expense> getData() {
            LocalDate date = datePicker.getValue();
            String amtStr = amountField.getText();
            String desc = descriptionField.getText();
            if (date != null && !amtStr.isEmpty()) {
                clearForm();
                return Optional.of(new Expense(date, Integer.parseInt(amtStr), desc));
            } else {
                return Optional.empty();
            }
        }
    };
}

package cz.janhrcek.tifi.ui;


import cz.janhrcek.tifi.storage.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Optional;

public class Ui {

    private final Pane rootPane;
    private final TableView<ExpenseUiModel> table;
    private final Storage storage;

    public Ui(Storage storage) {
        this.storage = storage;
        table = createTable();
        TiFiRecordForm form = new TiFiRecordForm(expense -> {
            storage.addExpense(expense);
            loadDbDataIntoTable();
        });
        loadDbDataIntoTable();
        rootPane = new VBox(
                createMenu(),
                table,
                form
        );
    }

    public Pane getRoot() {
        return rootPane;
    }

    private MenuBar createMenu() {
        MenuItem deleteAction = new MenuItem("Delete record");
        deleteAction.setOnAction(event -> {
            Optional<Integer> oId = getIdToDelete();
            if (oId.isEmpty()) {
                System.err.println("Didn't get valid input - ignoring deletion request");
            }
            oId.ifPresent(idToDelete -> {
                storage.deleteExpense(idToDelete);
                loadDbDataIntoTable();
            });
        });
        Menu actionsMenu = new Menu("Actions");
        actionsMenu.getItems().add(deleteAction);
        return new MenuBar(actionsMenu);
    }

    private Optional<Integer> getIdToDelete() {
        return askForInput()
                .filter(this::isValidInt)
                .map(Integer::parseInt);
    }

    private Optional<String> askForInput() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Please enter the id of a record to delete");
        dialog.setTitle("Delete a record");
        return dialog.showAndWait();
    }

    private boolean isValidInt(String s) {
        try {
            int ignore = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private void loadDbDataIntoTable() {
        ObservableList<ExpenseUiModel> data = loadExpenseData(storage);
        table.setItems(data);
    }

    private ObservableList<ExpenseUiModel> loadExpenseData(Storage storage) {
        ObservableList<ExpenseUiModel> tableData = FXCollections.observableArrayList();
        storage.getExpenses().forEach(e ->
                tableData.add(ExpenseUiModel.wrap(e))
        );
        return tableData;
    }

    private TableView<ExpenseUiModel> createTable() {
        TableView<ExpenseUiModel> table = new TableView<>();
        table.setEditable(true);
        TableColumn<ExpenseUiModel, Integer> idCol = new TableColumn<>("Id");
        TableColumn<ExpenseUiModel, LocalDate> dateCol = new TableColumn<>("Date");
        TableColumn<ExpenseUiModel, Integer> amountCol = new TableColumn<>("Amount");
        TableColumn<ExpenseUiModel, Integer> catCol = new TableColumn<>("Category");
        TableColumn<ExpenseUiModel, Integer> subcatCol = new TableColumn<>("Subcategory");
        TableColumn<ExpenseUiModel, String> descCol = new TableColumn<>("Description");

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        subcatCol.setCellValueFactory(new PropertyValueFactory<>("subcategory"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(idCol, dateCol, amountCol, catCol, subcatCol, descCol);
        return table;
    }
}

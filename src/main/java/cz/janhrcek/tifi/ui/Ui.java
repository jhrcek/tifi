package cz.janhrcek.tifi.ui;


import cz.janhrcek.tifi.storage.Storage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

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
                wrapInScrollPane(table),
                form
        );
    }

    public Pane getRoot() {
        return rootPane;
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

    private ScrollPane wrapInScrollPane(TableView<ExpenseUiModel> table) {
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }

    private TableView<ExpenseUiModel> createTable() {
        TableView<ExpenseUiModel> table = new TableView<>();
        table.setEditable(true);
        TableColumn<ExpenseUiModel, LocalDate> dateCol = new TableColumn<>("Date");
        TableColumn<ExpenseUiModel, Integer> amountCol = new TableColumn<>("Amount");
        TableColumn<ExpenseUiModel, Integer> catCol = new TableColumn<>("Category");
        TableColumn<ExpenseUiModel, Integer> subcatCol = new TableColumn<>("Subcategory");
        TableColumn<ExpenseUiModel, String> descCol = new TableColumn<>("Description");

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        subcatCol.setCellValueFactory(new PropertyValueFactory<>("subcategory"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(dateCol, amountCol, catCol, subcatCol, descCol);
        return table;
    }
}

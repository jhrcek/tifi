package cz.janhrcek.tifi;

import cz.janhrcek.tifi.storage.Storage;
import cz.janhrcek.tifi.ui.ExpenseUiModel;
import cz.janhrcek.tifi.ui.TiFiRecordForm;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainApp extends Application {
    final private Storage storage = new Storage();

    private final ObservableList<ExpenseUiModel> tableData =
            FXCollections.observableArrayList();


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tiny Finances");

        loadExpenseData();

        Pane root = createRootPane(storage);
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    private void loadExpenseData() {
        storage.getExpenses().forEach(e ->
                tableData.add(ExpenseUiModel.wrap(e))
        );
    }

    @Override
    public void stop() {
        System.out.println("Closing storage");
        storage.close();
    }

    private Pane createRootPane(Storage storage) {
        VBox root = new VBox();

        root.getChildren().addAll(
                initTable(storage),
                new TiFiRecordForm(storage)
        );
        return root;
    }


    private ScrollPane initTable(Storage storage) {
        TableView<ExpenseUiModel> table = new TableView<>();
        table.setEditable(true);
        TableColumn<ExpenseUiModel, LocalDate> dateCol = new TableColumn<>("Date");
        TableColumn<ExpenseUiModel, Integer> amountCol = new TableColumn<>("Amount");
        TableColumn<ExpenseUiModel, String> descCol = new TableColumn<>("Description");

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        table.getColumns().addAll(dateCol, amountCol, descCol);
        table.setItems(tableData);
        ScrollPane scrollPane = new ScrollPane(table);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);
        return scrollPane;
    }
}

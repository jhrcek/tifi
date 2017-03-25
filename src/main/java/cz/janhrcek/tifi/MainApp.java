package cz.janhrcek.tifi;

import cz.janhrcek.tifi.storage.Storage;
import cz.janhrcek.tifi.ui.TiFiRecordForm;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private Storage storage = new Storage();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Tiny Finances");

        Pane root = createRootPane(storage);
        primaryStage.setScene(new Scene(root, 400, 200));
        primaryStage.show();
    }

    @Override
    public void stop(){
        System.out.println("Closing storage");
        storage.close();
    }

    private Pane createRootPane(Storage storage) {
        VBox root = new VBox();
        root.getChildren().add(new TiFiRecordForm(storage));
        return root;
    }
}

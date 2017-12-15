package cz.janhrcek.tifi;

import cz.janhrcek.tifi.storage.Storage;
import cz.janhrcek.tifi.ui.Ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    final private Storage storage = new Storage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tiny Finances");
        Ui ui = new Ui(storage);
        primaryStage.setScene(new Scene(ui.getRoot(), 630, 200));
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Closing storage");
        storage.close();
    }
}

package main;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application{
    void main(String[] args) {
        launch(args);
    }

    // function to start the application
    @Override
    public void start(Stage stage) {
        InputScene is = new InputScene();
        stage.setTitle("PolyCubes");
        stage.setScene(is.createInputScene()); // set the scene to the input stage
        stage.show();
    }
}

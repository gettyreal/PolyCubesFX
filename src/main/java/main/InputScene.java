package main;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class InputScene {
    public Scene createInputScene() {
        Text title = new Text("Enter the number of cubes:");
        title.setStyle("-fx-font-size: 16px; -fx-fill: #ED7D3A; -fx-font-weight: bolder;");
        title.setLayoutX(50);
        title.setLayoutY(80);

        TextField inputField = new TextField();
        inputField.setStyle("-fx-font-size: 14px; -fx-border-radius: 5px;");

        inputField.setLayoutX(50);
        inputField.setLayoutY(100);

        Button starButton = new Button("Start generation");
        starButton.setStyle("-fx-background-color: #ED7D3A; -fx-text-fill: white; -fx-font-size: 14px;-fx-border-radius: 5px;");
        starButton.setOnAction(event -> handleAppStart(inputField.getText()));
    
        starButton.setLayoutX(50);
        starButton.setLayoutY(140);

        Group root = new Group();
        root.getChildren().addAll(title ,inputField, starButton);

        Scene scene = new Scene(root, 300, 250);
        scene.setFill(Color.web("#292F36"));
        
        return scene;
    }

    // HANDLERS

    private void handleAppStart(String input) {
        String CubeNumString = input;
        if(!CubeNumString.isEmpty()) {
            int CubeNum = Integer.parseInt(CubeNumString);
            openPolyCubeStage(CubeNum);
        }
            
    }

    private void openPolyCubeStage(int cubeNum) {
        PolyCubeScene ap = new PolyCubeScene(cubeNum);
        Stage polyCubeStage = new Stage();
        polyCubeStage.setTitle("PolyCubes");
        polyCubeStage.setScene(ap.createScene());
        polyCubeStage.show();
    }
}

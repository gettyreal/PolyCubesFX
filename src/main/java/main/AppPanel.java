package main;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.application.Application;
import javafx.scene.Group;

public class AppPanel extends Application {
    public final int screenWidth = 1000;
    public final int screenHeight = 750;

    public final int cubeSize = 200;

    private double lastX = 0;
    private double lastY = 0;
    private double angleX = 0;
    private double angleY = 0;

    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    @Override
    public void start(Stage stage) { 
      // visualizing window
      stage.setTitle("PolyCubes");
      stage.setScene(this.createScene()); 
      stage.show(); 
   }

    public Scene createScene() {
        Box box = new Box(cubeSize, cubeSize, cubeSize); //set a single cube

        //set cube position
        box.setTranslateX(screenWidth / 2);
        box.setTranslateY(screenHeight / 2);  

        // Add rotation transforms to the box
        box.getTransforms().addAll(rotateX, rotateY);

        // Creating group and scene to visualize the cubes
        Group root = new Group(box);
        Scene scene = new Scene(root, screenWidth, screenHeight);
        scene.setFill(Color.web("#252323"));

        // handling mouse events
        scene.setOnMousePressed(event -> handleMousePressed(event));
        scene.setOnMouseDragged(event -> handleMouseDragged(event));

        return scene;
    }

    void handleMousePressed(MouseEvent event) {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }

    void handleMouseDragged(MouseEvent event) {
        double deltaX = event.getSceneX() - lastX;
        double deltaY = event.getSceneY() - lastY;
        double speedRotation = 0.25;


        // Rotate around the Y-axis
        angleY -= speedRotation * deltaX;

        // Rotate around the X-axis
        angleX += speedRotation * deltaY;

        // Apply rotations to the transforms
        rotateY.setAngle(angleY);
        rotateX.setAngle(angleX);

        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }
}

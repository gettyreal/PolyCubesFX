package main;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Group;

public class AppPanel extends Application {
    public final int screenWidth = 1000;
    public final int screenHeight = 750;

    Box[][][] cubeGrid;
    public final int cubeSize = 50;
    Group grid;

    private double lastX = 0;
    private double lastY = 0;
    private double angleX = 0;
    private double angleY = 0;

    private Rotate rotateX = new Rotate(15, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(45, Rotate.Y_AXIS);
    private Translate translateRoot = new Translate(screenWidth / 2 - 125, screenHeight / 2 - 125, 0);

    @Override
    public void start(Stage stage) { 
      // visualizing window
      stage.setTitle("PolyCubes");
      stage.setScene(this.createScene()); 
      stage.show(); 
   }

    public Scene createScene() {        
        // Creating group and scene to visualize the cubes
        this.grid = new Group();
        createCubeGrid(5);

        // Apply rotation transforms to the entire group
        grid.getTransforms().add(translateRoot);
        grid.getTransforms().addAll(rotateX, rotateY);

        Scene scene = new Scene(grid, screenWidth, screenHeight);
        scene.setFill(Color.web("#252323"));

        // handling mouse events
        scene.setOnMousePressed(event -> handleMousePressed(event));
        scene.setOnMouseDragged(event -> handleMouseDragged(event, grid));

        return scene;
    }

    // function to create a grid of cubes
    void createCubeGrid(int gridSize) {

        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                for (int z = 0; z < gridSize; z++) {
                    // Create a new Box (cell) at position (x, y, z)
                    Box box = new Box(cubeSize, cubeSize, cubeSize);
                    box.setTranslateX(x * cubeSize); // Positioning in X direction
                    box.setTranslateY(y * cubeSize); // Positioning in Y direction
                    box.setTranslateZ(z * cubeSize); // Positioning in Z direction

                    // Add the box to the grid group
                    grid.getChildren().add(box);
                }
            }
        }

    }

    void handleMousePressed(MouseEvent event) {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }

    void handleMouseDragged(MouseEvent event, Group root) {
        double deltaX = event.getSceneX() - lastX;
        double deltaY = event.getSceneY() - lastY;
        double speedRotation = 0.25;

        // Rotate around the Y-axis
        angleY += speedRotation * deltaX;

        // Rotate around the X-axis
        angleX += speedRotation * deltaY;

        // Apply rotations to the group transforms
        rotateY.setAngle(angleY);
        rotateX.setAngle(angleX);

        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }
}

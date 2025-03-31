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

    private double lastX = 0;
    private double lastY = 0;
    private double angleX = 0;
    private double angleY = 0;

    private Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
    private Translate translateRoot = new Translate(500, 500, 50);

    @Override
    public void start(Stage stage) { 
      // visualizing window
      stage.setTitle("PolyCubes");
      stage.setScene(this.createScene()); 
      stage.show(); 
   }

    public Scene createScene() {
        createCubeGrid(4);
        
        // Creating group and scene to visualize the cubes
        Group root = new Group();
        for (Box[][] plane : cubeGrid) {
            for (Box[] row : plane) {
                for (Box cube : row) {
                    root.getChildren().add(cube);
                }
            }
        }

        // Apply rotation transforms to the entire group
        root.getTransforms().add(translateRoot);
        root.getTransforms().addAll(rotateX, rotateY);

        Scene scene = new Scene(root, screenWidth, screenHeight);
        scene.setFill(Color.web("#252323"));

        // handling mouse events
        scene.setOnMousePressed(event -> handleMousePressed(event));
        scene.setOnMouseDragged(event -> handleMouseDragged(event, root));

        return scene;
    }

    // function to create a grid of cubes
    void createCubeGrid(int gridSize) {
        int widthOffset = (screenWidth - (gridSize * cubeSize)) / 2;
        int heightOffset = (screenHeight - (gridSize * cubeSize)) / 2;

        cubeGrid = new Box[gridSize][gridSize][gridSize];
        for (int x = 0; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                for (int z = 0; z < gridSize; z++) {
                    Box cube = new Box(cubeSize, cubeSize, cubeSize);
                    cube.setTranslateX(x * cubeSize + widthOffset);
                    cube.setTranslateY(y * cubeSize + heightOffset);
                    cube.setTranslateZ(z * cubeSize);

                    cube.setDrawMode(javafx.scene.shape.DrawMode.LINE);

                    cubeGrid[x][y][z] = cube;
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
        angleY -= speedRotation * deltaX;

        // Rotate around the X-axis
        angleX += speedRotation * deltaY;

        // Apply rotations to the group transforms
        rotateY.setAngle(angleY);
        rotateX.setAngle(angleX);

        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }
}

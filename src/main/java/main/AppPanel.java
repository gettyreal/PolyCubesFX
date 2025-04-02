package main;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import math.Cube;
import math.Management;
import math.Polycube;
import javafx.application.Application;
import javafx.scene.Group;

public class AppPanel extends Application {
    public final int screenWidth = 1920;
    public final int screenHeight = 1000;

    public final int cubeSize = 50;
    Group grid;
    final int gridSize = 4;

    private double lastX = 0;
    private double lastY = 0;
    private double angleX = 0;
    private double angleY = 0;

    private Rotate rotateX;
    private Rotate rotateY;
    private Translate translateRoot;

    Management management;

    @Override
    public void start(Stage stage) {
        // visualizing window
        stage.setTitle("PolyCubes");
        stage.setScene(this.createScene());
        stage.show();
    }

    public Scene createScene() {
        // setupping management of math part
        managementSetup();

        // setupping the grid containing the Polycube
        gridSetup();

        // creating a new scene
        Scene scene = new Scene(grid, screenWidth, screenHeight);
        scene.setFill(Color.web("#252323"));

        // handling mouse events
        scene.setOnMousePressed(event -> handleMousePressed(event));
        scene.setOnMouseDragged(event -> handleMouseDragged(event, grid));

        addPolyCube(management.polycubes.get(132));

        return scene;
    }

    void managementSetup() {
        this.management = new Management();
        Polycube inputPolycube = Management.generateBasic2Polycube();
        management.generatePolycubes(gridSize - 2, inputPolycube);
        management.translatePolycubes();
    }

    void gridSetup() {
        // Creating group and scene to visualize the cubes
        this.grid = new Group();
        createCubeGrid(gridSize);
        addAxes();

        // Apply rotation transforms to the entire group
        final double gridOffset = gridSize * cubeSize / 2;
        this.rotateX = new Rotate(0, gridOffset, gridOffset, gridOffset, Rotate.X_AXIS);
        this.rotateY = new Rotate(0, gridOffset, gridOffset, gridOffset, Rotate.Y_AXIS);
        this.translateRoot = new Translate(screenWidth / 2 - gridOffset, screenHeight / 2 - gridOffset, 0);
        grid.getTransforms().add(translateRoot);
        grid.getTransforms().addAll(rotateX, rotateY);
    }

    // function to create a grid of cubes
    void addAxes() {
        final int  axisLength = gridSize * cubeSize;

        // X-axis
        Line xAxis = new Line(-25, axisLength - 25, axisLength + 25, axisLength - 25);
        xAxis.setTranslateZ(- 25);
        xAxis.setStroke(Color.RED);
        xAxis.setStrokeWidth(2);
        grid.getChildren().add(xAxis);

        // Y-axis
        Line yAxis = new Line(-25, -75, -25, axisLength - 25);
        yAxis.setStroke(Color.BLUE);
        yAxis.setStrokeWidth(2);
        yAxis.setTranslateZ(-25);
        grid.getChildren().add(yAxis);

        // Z-axis
        Line zAxis = new Line(-25, 0, -25, axisLength + 75);
        zAxis.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        zAxis.setStroke(Color.GREEN);
        zAxis.setStrokeWidth(2);
        zAxis.setTranslateZ(-25);
        zAxis.setTranslateY(axisLength - 25);
        grid.getChildren().add(zAxis);
    }

    void createCubeGrid(int gridSize) {
        for (int x = 0; x < gridSize; x++) {
            for (int y = gridSize - 1; y >= 0; y--) {
                for (int z = 0; z < gridSize; z++) {
                    // Create a new Box (functioning as a cell) at position (x, y, z)
                    Box box = new Box(cubeSize, cubeSize, cubeSize); // create the cell
                    PhongMaterial cubeMaterial = new PhongMaterial(); // new material for transparent color
                    cubeMaterial.setDiffuseColor(Color.web("#00000000"));

                    // set material and box coordinates
                    box.setTranslateX(x * cubeSize);
                    box.setTranslateY((gridSize - 1 - y) * cubeSize);
                    box.setTranslateZ(z * cubeSize);
                    box.setMaterial(cubeMaterial);
                    // setting box id to match coordinate system of polyCubes
                    box.setId("box_" + x + "_" + y + "_" + z);
                    // Add the box to the grid group
                    grid.getChildren().add(box);
                }
            }
        }
    }

    void addPolyCube(Polycube polycube) {
        for (Cube cube : polycube.cubes) {
            for (int i = 0; i < grid.getChildren().size(); i++) {
                Box box = (Box) grid.getChildren().get(i);
                if (box.getId().equals(cube.id)) {
                    PhongMaterial cubeMaterial = new PhongMaterial();
                    cubeMaterial.setDiffuseColor(Color.web("#69D1C5"));
                    box.setMaterial(cubeMaterial);
                    System.out.println("Cube added at: " + cube.id);
                    break;
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

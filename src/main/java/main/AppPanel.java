package main;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import math.Cube;
import math.Management;
import math.Polycube;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Group;

public class AppPanel extends Application {
    public final int screenWidth = 1920;
    public final int screenHeight = 1000;

    public final int cubeSize = 50;
    ArrayList<Group> PolyCubes3D;
    Group grid;
    final int gridSize = 4;

    private int groupOffsetX = 250;
    private int groupOffsetY = 250;

    private double lastX = 0;
    private double lastY = 0;
    private double angleX = 0;
    private double angleY = 0;

    private Translate translateRoot;

    private Map<Group, Rotate> rotateXMap = new HashMap<>();
    private Map<Group, Rotate> rotateYMap = new HashMap<>();

    Management management;

    @Override
    public void start(Stage stage) {
        // visualizing window
        stage.setTitle("PolyCubes");
        stage.setScene(this.createScene());
        stage.show();
    }

    public Scene createScene() {
        // math calculations
        managementSetup();

        this.PolyCubes3D = new ArrayList<Group>();
        for(int i = 0; i < management.polycubes.size(); i++) {
            gridSetup();
        }

        for(int i = 0; i < PolyCubes3D.size(); i++) {
            addPolyCube(management.polycubes.get(i), PolyCubes3D.get(i));
        }

        // creating a new scene
        Group root = new Group();

        String titleString = "PolyCubes with 4 cubes";
        Text title = new Text(titleString);
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 50px;");
        title.setTranslateX(screenWidth / 2 - 256);
        title.setTranslateY(100);
        root.getChildren().add(title);

        for (Group polyCubeGrid : PolyCubes3D) {
            root.getChildren().add(polyCubeGrid);
        }
        Scene scene = new Scene(root, screenWidth, screenHeight);
        scene.setFill(Color.web("#252323"));

        // handling mouse events
        scene.setOnMousePressed(event -> handleMousePressed(event));
        scene.setOnMouseDragged(event -> handleMouseDragged(event, grid));
        
        return scene;
    }

    void managementSetup() {
        this.management = new Management();
        Polycube inputPolycube = new Polycube();
        management.generatePolycubes(gridSize - 1, inputPolycube);
        System.out.println("Polycubes generated: " + management.polycubes.size() + '\n');
        management.printPolycubes();
    }

    void gridSetup() {
        // Creating group and scene to visualize the cubes
        Group tempGrid = new Group();
        addAxes(tempGrid);
        createCubeGrid(gridSize, tempGrid);

        // Apply rotation transforms to the entire group
        final double gridOffset = gridSize * cubeSize / 2;
        Rotate rotateX = new Rotate(0, gridOffset, gridOffset, gridOffset, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, gridOffset, gridOffset, gridOffset, Rotate.Y_AXIS);
        this.rotateXMap.put(tempGrid, rotateX);
        this.rotateYMap.put(tempGrid, rotateY);

        this.translateRoot = new Translate(groupOffsetX, groupOffsetY, 0);
        tempGrid.getTransforms().add(translateRoot);
        tempGrid.getTransforms().addAll(rotateX, rotateY);

        groupOffsetX += 400;

        if (groupOffsetX > 1600) {
            groupOffsetX = 300;
            groupOffsetY += 400;
        }

        this.PolyCubes3D.add(tempGrid);
    }

    // function to create a grid of cubes
    void addAxes(Group grid) {
        final int  axisLength = gridSize * cubeSize;

        // X-axis
        Line xAxis = new Line(-25, axisLength - 25, axisLength + 25, axisLength - 25);
        xAxis.setTranslateZ(- 25);
        xAxis.setStroke(Color.RED);
        xAxis.setStrokeWidth(3);
        grid.getChildren().add(xAxis);

        // Y-axis
        Line yAxis = new Line(-25, -75, -25, axisLength - 25);
        yAxis.setStroke(Color.BLUE);
        yAxis.setStrokeWidth(3);
        yAxis.setTranslateZ(-25);
        grid.getChildren().add(yAxis);

        // Z-axis
        Line zAxis = new Line(-25, 0, -25, axisLength + 75);
        zAxis.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        zAxis.setStroke(Color.GREEN);
        zAxis.setStrokeWidth(3);
        zAxis.setTranslateZ(-25);
        zAxis.setTranslateY(axisLength - 25);
        grid.getChildren().add(zAxis);
    }

    void createCubeGrid(int gridSize, Group grid) {
        for (int x = 0; x < gridSize; x++) {
            for (int y = gridSize - 1; y >= 0; y--) {
                for (int z = 0; z < gridSize; z++) {
                    // Create a new Box (functioning as a cell) at position (x, y, z)
                    Box box = new Box(cubeSize, cubeSize, cubeSize); // create the cell
                    PhongMaterial cubeMaterial = new PhongMaterial(); // new material for transparent color
                    cubeMaterial.setDiffuseColor(Color.web("#00000000"));
                    box.setDrawMode(DrawMode.LINE); 

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

    void addPolyCube(Polycube polycube, Group grid) {
        for (Cube cube : polycube.cubes) {
            for (int i = 0; i < grid.getChildren().size(); i++) {
                if (grid.getChildren().get(i) instanceof Box) {
                    Box box = (Box) grid.getChildren().get(i); // Safe cast
                    if (box.getId().equals(cube.id)) {
                        PhongMaterial cubeMaterial = new PhongMaterial();
                        cubeMaterial.setDiffuseColor(Color.web("#000000"));
                        box.setMaterial(cubeMaterial);
                        break;
                    }
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
        for (Group polyCubeGrid : PolyCubes3D) {
            Rotate rotateY = rotateYMap.get(polyCubeGrid);
            Rotate rotateX = rotateXMap.get(polyCubeGrid);
            rotateY.setAngle(angleY);
            rotateX.setAngle(angleX);
        }

        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }
}

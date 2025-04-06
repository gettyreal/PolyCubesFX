package main;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import math.Cube;
import math.Management;
import math.Polycube;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.Group;

public class PolyCubeScene{
    public final int screenWidth = 1920; // width of the screen
    public final int screenHeight = 1000; // height of the screen

    public Stage stage;
    Group root; //root group used to create the scene

    public final int cubeSize = 50; //default px cube size
    ArrayList<Group> PolyCubes3D; //array contaning all possible polycubes in 3d
    
    final int gridSize; // variable to set the size of the grid  and also the 
    // number of cubes in the polycube

    private int groupOffsetX = 250; // star offset x of grid in scene
    private int groupOffsetY = 250; // start offset y of grid in scene

    // rotation variables
    private double lastX = 0;
    private double lastY = 0;
    private double angleX = 0;
    private double angleY = 0;

    // variavble to translate the root group into correct spce
    private Translate translateRoot;

    // Maps to store the rotation transforms for each grid
    private Map<Group, Rotate> rotateXMap = new HashMap<>();
    private Map<Group, Rotate> rotateYMap = new HashMap<>();

    // management obj for math part of the programm
    Management management;

    // constructor
    // take as parameter the grid size (equivalent to CubeNum)
    public PolyCubeScene(int gridSize) {
        this.gridSize = gridSize;
    }

    // function to create the starting scene
    // returns the scene with all elements already computated
    public Scene createScene() {
        // creating application root group
        this.root = new Group();

        // math calculations
        managementSetup();

        // visyal setup
        polycubes3dSetup();

        // scene creation and setup
        Scene scene = new Scene(root, screenWidth, screenHeight);
        scene.setFill(Color.web("#292F36"));

        // handling camera movement and zoom
        scene.setOnMousePressed(event -> handleMousePressed(event));
        scene.setOnMouseDragged(event -> handleMouseDragged(event, root));
        scene.setOnScroll(event -> handleMouseScroll(event));

        return scene;
    }

    // function to create the math part of the application
    // this function creates all possyble combination of polycubes
    void managementSetup() {
        // creating management to manage polycube generation
        this.management = new Management();
        Polycube inputPolycube = new Polycube(); // create first polycube
        management.generatePolycubes(gridSize - 1, inputPolycube); // start generatioon of possible polycubes
        System.out.println("Polycubes generated: " + management.polycubes.size() + '\n'); // get the numbber of polycubes generated
        management.printPolycubes(); // print them all into console with relative coorinates and id
    }

    // function to create the 3d representation of each polycubes
    void polycubes3dSetup() {
        // create and array for all 3d rappresentation of polycubes
        this.PolyCubes3D = new ArrayList<Group>();

        // creating each polycube 3d representation
        for (int i = 0; i < management.polycubes.size(); i++) {
            gridSetup();
        }

        // adding into the grid visivle polycube
        for (int i = 0; i < PolyCubes3D.size(); i++) {
            addPolyCube(management.polycubes.get(i), PolyCubes3D.get(i));
        }

        // adding all the rappresentations into the root
        for (Group polyCubeGrid : PolyCubes3D) {
            root.getChildren().add(polyCubeGrid);
        }
    }

    // function to setup single grid for polycube to be added
    void gridSetup() {
        // Creating group to encase the cube grid
        Group tempGrid = new Group();
        addAxes(tempGrid); // grid axes setup (only visual thing)
        createCubeGrid(gridSize, tempGrid); // creating the grid

        // Apply rotation transforms to the entire group
        final double gridOffset = gridSize * cubeSize / 2; // variable to get the center of a dimension in the grid

        // rotation with pivots in the center of the grid
        Rotate rotateX = new Rotate(0, gridOffset, gridOffset, gridOffset, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, gridOffset, gridOffset, gridOffset, Rotate.Y_AXIS);

        // adding rotations and grid correlation in the worldMap
        this.rotateXMap.put(tempGrid, rotateX);
        this.rotateYMap.put(tempGrid, rotateY);

        // setting grid position into the scene
        this.translateRoot = new Translate(groupOffsetX, groupOffsetY, 0);

        //adding all transorm and rotation to the grid
        tempGrid.getTransforms().add(translateRoot);
        tempGrid.getTransforms().addAll(rotateX, rotateY);

        // variable manipulation to allocate grids in the scene
        groupOffsetX += 400;
        if (groupOffsetX > 1600) {
            groupOffsetX = 300;
            groupOffsetY += 400;
        }

        //add the single grid into the array
        this.PolyCubes3D.add(tempGrid);
    }

    // function to create the axes of a single grid
    void addAxes(Group grid) {
        final int axisLength = gridSize * cubeSize; //calculates the axis lenght

        // X-axis
        Line xAxis = new Line(-25, axisLength - 25, axisLength + 25, axisLength - 25);
        xAxis.setTranslateZ(-25);
        xAxis.setStroke(Color.web("#EF2D56"));
        xAxis.setStrokeWidth(3);
        grid.getChildren().add(xAxis);

        // Y-axis
        Line yAxis = new Line(-25, -75, -25, axisLength - 25);
        yAxis.setStroke(Color.web("#2FBF71"));
        yAxis.setStrokeWidth(3);
        yAxis.setTranslateZ(-25);
        grid.getChildren().add(yAxis);

        // Z-axis
        Line zAxis = new Line(-25, 0, -25, axisLength + 75);
        zAxis.getTransforms().add(new Rotate(90, Rotate.X_AXIS));
        zAxis.setStroke(Color.web("#1D4E89"));
        zAxis.setStrokeWidth(3);

        zAxis.setTranslateZ(-25);
        zAxis.setTranslateY(axisLength - 25);
        grid.getChildren().add(zAxis);
    }

    // function to create the alone grid of cubes
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
                    box.setTranslateY((gridSize - 1 - y) * cubeSize); // invert y axis to match 3d coordinates
                    box.setTranslateZ(z * cubeSize);
                    box.setMaterial(cubeMaterial);
                    // setting box id for cube identification and confrontation
                    box.setId("box_" + x + "_" + y + "_" + z);
                    // Add the box to the grid group
                    grid.getChildren().add(box);
                }
            }
        }
    }

    // function to add visually a single polycube into a grid
    void addPolyCube(Polycube polycube, Group grid) {
        for (Cube cube : polycube.cubes) {
            for (int i = 0; i < grid.getChildren().size(); i++) {
                if (grid.getChildren().get(i) instanceof Box) { // check if the child is a Box (in the group are present also axes (lines))
                    Box box = (Box) grid.getChildren().get(i); // down cast to get the single cube
                    if (box.getId().equals(cube.id)) { // if id are equal adss the visual representation of the cube
                        PhongMaterial cubeMaterial = new PhongMaterial();
                        cubeMaterial.setDiffuseColor(Color.web("#FEFEFEFF")); // by simply canging the color
                        box.setMaterial(cubeMaterial);
                        break;
                    }
                }
            }
        }
    }

    // EVENT HANDLES

    // function to handle the mouse pressed event
    // this function is used to get the last position of the mouse in the scene
    void handleMousePressed(MouseEvent event) {
        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }

    // function to handle the mouse dragged event
    // used to rotate the grid in the scene
    void handleMouseDragged(MouseEvent event, Group root) {
        double deltaX = event.getSceneX() - lastX;
        double deltaY = event.getSceneY() - lastY;
        double speedRotation = 0.25;

        // Rotate around the Y-axis
        angleY -= speedRotation * deltaX;

        // Rotate around the X-axis
        angleX += speedRotation * deltaY;

        // Apply rotations to all polyCube grids
        for (Group polyCubeGrid : PolyCubes3D) {
            // for each grid get rotation and rotates it the same way
            Rotate rotateY = rotateYMap.get(polyCubeGrid);
            Rotate rotateX = rotateXMap.get(polyCubeGrid);
            rotateY.setAngle(angleY);
            rotateX.setAngle(angleX);
        }

        // resets the last position of the mouse
        lastX = event.getSceneX();
        lastY = event.getSceneY();
    }

    // function to handle the mouse scroll event
    // used to zoom in and out the grid in the scene
    void handleMouseScroll(ScrollEvent event) {
        double zoomFactor = 1.5; // multiplier for zooming
        double deltaY = event.getDeltaY(); // gets value to check if zoom in or out
        double mouseX = event.getSceneX(); // gets mouse x position
        double mouseY = event.getSceneY();  // gets mouse y position

        // for each PolyCube3d apply the same zoom effect
        for (Group polyCubeGrid : PolyCubes3D) {
            // gets initial scale values
            double initialScaleX = polyCubeGrid.getScaleX();
            double initialScaleY = polyCubeGrid.getScaleY();
            double initialScaleZ = polyCubeGrid.getScaleZ();

            // calculates new scale 
            double newScaleX = initialScaleX;
            double newScaleY = initialScaleY;
            double newScaleZ = initialScaleZ;
            if (deltaY > 0) {
                // Zoom in
                newScaleX = initialScaleX * zoomFactor;
                newScaleY = initialScaleY * zoomFactor;
                newScaleZ = initialScaleZ * zoomFactor;
            } else {
                // Zoom out
                newScaleX = initialScaleX / zoomFactor;
                newScaleY = initialScaleY / zoomFactor;
                newScaleZ = initialScaleZ / zoomFactor;
            }   

            // apply new scale to the grid
            polyCubeGrid.setScaleX(newScaleX);
            polyCubeGrid.setScaleY(newScaleY);
            polyCubeGrid.setScaleZ(newScaleZ);

            // Adjust the pivot point for zoom
            polyCubeGrid.setTranslateX(polyCubeGrid.getTranslateX()
                    + (mouseX - polyCubeGrid.getTranslateX()) * (initialScaleX - newScaleX) / initialScaleX);
            polyCubeGrid.setTranslateY(polyCubeGrid.getTranslateY()
                    + (mouseY - polyCubeGrid.getTranslateY()) * (initialScaleY - newScaleY) / initialScaleY);
        }
    }
}

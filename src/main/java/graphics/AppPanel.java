package graphics;

import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.shape.Box; 
import javafx.scene.Group;

public class AppPanel {
    public static Scene createScene() {
            // Create a Box (cube) with width, height, and depth
            Box cube = new Box(50, 50, 50);
            
            // Set material for the cube (color and shine)
            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.BLUE);  // Set color
            cube.setMaterial(material);
            
            // Create a camera to view the 3D scene
            PerspectiveCamera camera = new PerspectiveCamera(true);
            camera.setTranslateZ(-200); // Adjust the camera position closer to the cube
        
            // Create a group to hold the cube
            Group root = new Group();
            root.getChildren().add(cube);
        
            // Apply a rotation to the cube for a dynamic effect
            cube.setRotationAxis(Rotate.Y_AXIS);
            cube.setRotate(45);
        
            // Position the cube slightly in the scene to ensure it is visible
            cube.setTranslateX(0);
            cube.setTranslateY(0);
        
            // Create a scene with a width and height
            Scene scene = new Scene(root, 800, 600, true);
            scene.setFill(Color.LIGHTGRAY);  // Background color
        
            // Set the camera for the scene
            scene.setCamera(camera);
        
            return scene;
        }
}

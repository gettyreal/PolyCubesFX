package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.Group;
import javafx.scene.shape.Box;
import javafx.scene.transform.Translate; 
/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) { 
      //Drawing a Box 
      Box box = new Box();  
      
      //Setting the properties of the Box 
      box.setWidth(200.0); 
      box.setHeight(200.0);  
      box.setDepth(200.0);

      Translate translate = new Translate();       
      translate.setX(200); 
      translate.setY(150); 
      translate.setZ(25); 
      
      box.getTransforms().addAll(translate);
	  
      //Creating a Group object  
      Group root = new Group(box); 
         
      //Creating a scene object 
      Scene scene = new Scene(root, 400, 300);
	  
      scene.setFill(Color.web("#81c483"));	  
      
      //Setting title to the Stage 
      stage.setTitle("Translate a Box"); 
         
      //Adding scene to the stage 
      stage.setScene(scene); 
         
      //Displaying the contents of the stage 
      stage.show(); 
   }
}

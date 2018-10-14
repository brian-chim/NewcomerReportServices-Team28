package application;

import javafx.application.Application; 
import static javafx.application.Application.launch; 
import javafx.geometry.Insets; 
import javafx.geometry.Pos; 

import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.control.PasswordField; 
import javafx.scene.layout.GridPane; 
import javafx.scene.text.Text; 
import javafx.scene.control.TextField; 
import javafx.stage.Stage;  
         
public class Main extends Application { 

   @Override
   public void start(Stage stage) {
	   Scene loginScene = new Scene(new LoginScene(stage, true), 800, 600);
	   //create the first scene
	   stage.setScene(loginScene);
	   stage.setTitle("JavaBeans");
	   stage.show();
   }

   public static void main(String args[]){ 
      launch(args); 
   } 
}
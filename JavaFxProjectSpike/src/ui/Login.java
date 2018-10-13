package ui;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import application.User;
 
/**
 *
 * @web http://zoranpavlovic.blogspot.com/
 */
public class Login extends Application {
 
 String user = "admin";
 String password = "12345";
 String checkUser, checkPw;
 
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(50,70,70,70));
        
        //Adding HBox
        HBox hb = new HBox();
        hb.setPadding(new Insets(50,50,50,70));
        
        //Adding GridPane
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(30,30,30,30));
        gridPane.setHgap(5);
        gridPane.setVgap(15);
        
       //Implementing Nodes for GridPane
        Label lblUserName = new Label("Username");
        final TextField txtUserName = new TextField();
        Label lblPassword = new Label("Password");
        final PasswordField pf = new PasswordField();
        Button btnLogin = new Button("Login");
        final Label lblMessage = new Label();
        Hyperlink link = new Hyperlink();
        link.setText("Or Register Here");
        link.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
          	  Stage newStage = new Stage();
          	  new Signup().start(newStage);
            }
        });
        
        //Adding Nodes to GridPane layout
        gridPane.add(lblUserName, 0, 0);
        gridPane.add(txtUserName, 1, 0);
        gridPane.add(lblPassword, 0, 1);
        gridPane.add(pf, 1, 1);
        gridPane.add(btnLogin, 0, 3);
        gridPane.add(link, 1, 3);
        gridPane.add(lblMessage, 0, 4, 3, 1);
        
                
        //Reflection for gridPane
        Reflection r = new Reflection();
        r.setFraction(0.3f);
        gridPane.setEffect(r);
        
        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        
        //Adding text and DropShadow effect to it
        Text text = new Text("LOGIN");
        text.setEffect(dropShadow);
        
        //Adding text to HBox
        hb.getChildren().add(text);
        hb.setAlignment(Pos.CENTER);
                          
        //Add ID's to Nodes
        bp.setId("bp");
        gridPane.setId("root");
        btnLogin.setId("btnLogin");
        text.setId("text");
        link.setId("link");
                
        //Action for btnLogin
        btnLogin.setOnAction(new EventHandler<ActionEvent>() {
        // this handler should check the db for username&password matching
         public void handle(ActionEvent event) {
          checkUser = txtUserName.getText().toString();
          checkPw = pf.getText().toString();
          if(checkUser.equals(user) && checkPw.equals(password)){
        	  User user = new User(checkUser, checkPw);
        	  Stage newStage = new Stage();
        	  new WorkStage().start(newStage, user);
        	  primaryStage.close();
          }
          else{
              lblMessage.setText("Wrong username or password!");
              lblMessage.setTextFill(Color.RED);
          }
          txtUserName.setText("");
          pf.setText("");
         }
         });
       
        //Add HBox and GridPane layout to BorderPane Layout
        bp.setTop(hb);
        bp.setCenter(gridPane);  
        
        //Adding BorderPane to the scene and loading CSS
     Scene scene = new Scene(bp);
     scene.getStylesheets().add(getClass().getResource("login.css").toExternalForm());
     primaryStage.setScene(scene);
     primaryStage.setTitle("Application Spike");
     primaryStage.setResizable(false);
     primaryStage.show();
    }
}
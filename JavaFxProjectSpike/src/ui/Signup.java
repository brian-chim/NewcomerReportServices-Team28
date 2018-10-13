package ui;



import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class Signup extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Registration Form JavaFX Application");
	    // Instantiate a new Grid Pane
	    GridPane gridPane = initializeGridPane();
	    gridPane.setId("pane");
	    addUIControls(gridPane, primaryStage);
	    Scene scene = new Scene(gridPane, 800, 500);
	    // Set the scene in primary stage
	    scene.getStylesheets().add(getClass().getResource("signup.css").toExternalForm());
	    primaryStage.setScene(scene);
	    
	    primaryStage.show();

		
	}
	
	private GridPane initializeGridPane() {
	    // Instantiate a new Grid Pane
	    GridPane gridPane = new GridPane();

	    // Position and padding settings
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setPadding(new Insets(40, 40, 40, 40));
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);

//	    // columnOneConstraints will be applied to all the nodes placed in column one.
//	    ColumnConstraints columnOneConstraints = new ColumnConstraints(200, 200, Double.MAX_VALUE);
//	    columnOneConstraints.setHalignment(HPos.RIGHT);
//
//	    // columnTwoConstraints will be applied to all the nodes placed in column two.
//	    ColumnConstraints columnTwoConstrains = new ColumnConstraints(150,150, Double.MAX_VALUE);
//	    columnTwoConstrains.setHgrow(Priority.ALWAYS);
//
//	    gridPane.getColumnConstraints().addAll(columnOneConstraints, columnTwoConstrains);
	    
	    return gridPane;
	}
	
	private void addUIControls(GridPane gridPane, Stage primaryStage) {
	    // create nodes
	    Label headerLabel = new Label("Registration Form");
	    Label nameLabel = new Label("User Name : ");
	    final TextField nameField = new TextField();
	    Label emailLabel = new Label("Registering Email: ");
	    final TextField emailField = new TextField();
	    Label orgLabel = new Label("Agency/Organization Name: ");
	    TextField orgField = new TextField();
	    Label serviceLabel = new Label("Service Stream: ");
	    final ObservableList<String> options = 
	    	    FXCollections.observableArrayList(
	    	        "Client Profile Bulk",
	    	        "Needs Assessment & Referral Service (NARS)",
	    	        "Employment Related Services",
	    	        "Community Connections Services",
	    	        "Information & Orientation Services",
	    	        "Language Training Services"
	    	    );

	    // how to multiple select? how to import CheckComboBox? 
	    final ComboBox<String> checkComboBox = new ComboBox<String>(options);
	    Label passwordLabel = new Label("Password : ");
	    final PasswordField passwordField = new PasswordField();
	    
    
	    // set nodes to the gridPane
	    gridPane.add(headerLabel, 1 ,0, 2, 1);
	    gridPane.add(nameLabel, 0,1);
	    gridPane.add(nameField, 1, 1);
	    gridPane.add(emailLabel, 0, 2);
	    gridPane.add(emailField, 1, 2);    
	    gridPane.add(orgLabel, 0, 3);
	    gridPane.add(orgField, 1, 3);
	    gridPane.add(serviceLabel, 0, 4);
	    gridPane.add(checkComboBox, 1, 4);
	    gridPane.add(passwordLabel, 0, 5);
	    gridPane.add(passwordField, 1, 5);

	    // Add Submit Button
	    Button submitButton = new Button("Submit");
	    submitButton.setPrefHeight(40);
	    submitButton.setDefaultButton(true);
	    submitButton.setPrefWidth(100);
	    submitButton.setOnAction(new EventHandler<ActionEvent>(){
	        @Override
	        public void handle(ActionEvent e) {
	        	// get info from from and save to database
	        	primaryStage.close();
	        }
	    });
	    gridPane.add(submitButton, 0, 6, 2, 1);
	    GridPane.setHalignment(submitButton, HPos.CENTER);
	    GridPane.setMargin(submitButton, new Insets(20, 0,20,0));
	    GridPane.setMargin(headerLabel, new Insets(20, 20,20,20));
	}

}

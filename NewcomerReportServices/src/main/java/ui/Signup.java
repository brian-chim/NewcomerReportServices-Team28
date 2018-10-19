package ui;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Signup extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Newcomer Report Services Registration");
	    // Instantiate a new Grid Pane
	    GridPane gridPane = new GridPane();
	    addUIControls(gridPane, primaryStage);
	    Scene scene = new Scene(gridPane, 800, 600);
	    // Set the scene in primary stage
	    scene.getStylesheets().add(getClass().getResource("/signup.css").toExternalForm());
	    primaryStage.setScene(scene);
	    
	    primaryStage.show();
	
	}

	
	private VBox initializeServiceStreamLayout() {
		ArrayList<String> streams = new ArrayList<String>(
				Arrays.asList(
		    	        "Client Profile Bulk",
		    	        "Needs Assessment & Referral Service (NARS)",
		    	        "Employment Related Services",
		    	        "Community Connections Services",
		    	        "Information & Orientation Services",
		    	        "Language Training Services")
		);
		// with VBox with spacing
		VBox serviceStreamLayout = new VBox(5);
		
		// create checkbox for each stream and add to the VBox
		for (int i=0; i < streams.size(); i ++) {
			serviceStreamLayout.getChildren().add(new CheckBox(streams.get(i)));
		}
				
		return serviceStreamLayout;
	}
	
	private void addUIControls(GridPane gridPane, final Stage primaryStage) {
	    // create nodes
	    Label headerLabel = new Label("Participating Organization Registration");
	    Label nameLabel = new Label("User Name : ");
	    final TextField nameField = new TextField();
	    Label orgLabel = new Label("Agency/Organization Name: ");
	    // test data for selecting agency
	    // real data comes from db handler
	    ArrayList<String> agencies = new ArrayList<String>();
	    agencies.add("default agency");	    
	    ObservableList<String> agencyOptions = FXCollections.observableArrayList(agencies);
	    final ComboBox<String> orgField = new ComboBox<String>(agencyOptions);
	    
	    Label serviceLabel = new Label("Service Stream: ");	    
	    final VBox serviceStreamLayout = initializeServiceStreamLayout();
	    
	    Label passwordLabel = new Label("Password : ");
	    final PasswordField passwordField = new PasswordField();
	    
    
	    // set nodes to the gridPane
	    gridPane.add(headerLabel, 0 ,0, 2, 1);
	    gridPane.add(nameLabel, 0,1);
	    gridPane.add(nameField, 1, 1);   
	    gridPane.add(orgLabel, 0, 2);
	    gridPane.add(orgField, 1, 2);
	    gridPane.add(serviceLabel, 0, 3);
	    gridPane.add(serviceStreamLayout, 1, 3);
	    gridPane.add(passwordLabel, 0, 4);
	    gridPane.add(passwordField, 1, 4);

	    // Add Submit Button
	    Button submitButton = new Button("Submit");
	    submitButton.setOnAction(new EventHandler<ActionEvent>(){
	        public void handle(ActionEvent e) {
	        	String username = nameField.getText();
	        	String orgname = orgField.getValue();
	        	ArrayList<String> services = new ArrayList<String>();
	        	for (Node stream : serviceStreamLayout.getChildrenUnmodifiable()) {
	        		if (((CheckBox)stream).isSelected()) {
	        			services.add(((Labeled) stream).getText());
	        		}
	        	}
	        	String password = passwordField.getText();
	        	if (username == "" || orgname == null || services.isEmpty() || password == "") {
	        		Alert alert = new Alert(AlertType.ERROR);
	        		 
	        		alert.setTitle("Error alert");
	        		alert.setHeaderText("Mandatory Field(s) Missing");
	        		alert.setContentText("Please fill in all the fields!");
	        		 
	        		alert.showAndWait();
	        	} else {
	        		primaryStage.close();
	        	}
	        }
	    });
	    gridPane.add(submitButton, 0, 5, 2, 1);
	    GridPane.setHalignment(submitButton, HPos.CENTER);
	    // javafx css has no margin setting, so set it in code
	    GridPane.setMargin(submitButton, new Insets(20, 0,20,0));
	    GridPane.setMargin(headerLabel, new Insets(20, 20,20,20));
	    
	    // define ID for nodes which require CSS formatting
	    gridPane.setId("mainGridPane");
	    headerLabel.setId("header");
	    submitButton.setId("submitButton");
	}


}


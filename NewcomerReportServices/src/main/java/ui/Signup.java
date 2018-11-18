package ui;

import javafx.application.Application;
import application.database.DatabaseHandler;

import application.users.UserTypes;
import application.util.DatabaseServiceStreams;
import application.util.Formatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
		ArrayList<String> streams = DatabaseHandler.getServiceStreams();	
		// with VBox with spacing
		VBox serviceStreamLayout = new VBox(5);
		
		// create checkbox for each stream and add to the VBox
		for (int i=0; i < streams.size(); i ++) {
			serviceStreamLayout.getChildren().add(new CheckBox(streams.get(i)));
		}
				
		return serviceStreamLayout;
	}
	
	private ComboBox<String> userTypeSelect() {
		ComboBox<String> userType;
		ArrayList<String> types = new ArrayList<String>(
				Arrays.asList(
						UserTypes.AGENCY.getUiName(),
						UserTypes.STAFF.getUiName(),
						UserTypes.ADMIN.getUiName())
		);
		ObservableList<String> typeOptions = FXCollections.observableArrayList(types);
				
		userType = new ComboBox<String>(typeOptions);
		return userType;
				
		
	}
	
	private void addUIControls(GridPane gridPane, final Stage primaryStage) {
	    // create nodes
	    Label headerLabel = new Label("Service Account Registration");
	    Label nameLabel = new Label("User Name : ");
	    final TextField nameField = new TextField();
	    Label emailLabel = new Label("Email : ");
	    final TextField emailField = new TextField();
	    Label passwordLabel = new Label("Password : ");
	    final PasswordField passwordField = new PasswordField();
	    Label typeLabel = new Label("User Type : ");
	    final ComboBox<String> userType = userTypeSelect();
	    
	   
	    final Label orgLabel = new Label("Agency/Organization Name: ");
	    final ArrayList<String> agencies = DatabaseHandler.getAgencies();	    
	    ObservableList<String> agencyOptions = FXCollections.observableArrayList(agencies);
	    final ComboBox<String> orgField = new ComboBox<String>(agencyOptions);
	    
	    final Label serviceLabel = new Label("Service Stream: ");	    
	    final VBox serviceStreamLayout = initializeServiceStreamLayout();
	    
	    
    
	    // set nodes to the gridPane
	    gridPane.add(headerLabel, 0 ,0, 2, 1);
	    gridPane.add(nameLabel, 0,1);
	    gridPane.add(nameField, 1, 1);
	    gridPane.add(emailLabel, 0,2);
	    gridPane.add(emailField, 1, 2);
	    gridPane.add(passwordLabel, 0, 3);
	    gridPane.add(passwordField, 1, 3);
	    gridPane.add(typeLabel, 0,4);
	    gridPane.add(userType, 1, 4);
	    
	    userType.valueProperty().addListener(new ChangeListener<String>() {
	        @Override 
	        public void changed(ObservableValue ov, String prev, String curr) {
	        	if (curr == UserTypes.AGENCY.getUiName()) {
	        		orgLabel.setVisible(true);
	        		orgField.setVisible(true);
	        		serviceLabel.setVisible(true);
	        		serviceStreamLayout.setVisible(true);
	        		
	        	} else {
	        		if (curr == UserTypes.ADMIN.getUiName()) {
		        		orgLabel.setVisible(false);
		        		orgField.setVisible(false);
	        		} else {
		        		orgLabel.setVisible(true);
		        		orgField.setVisible(true);
	        		}
	        		serviceLabel.setVisible(false);
	        		serviceStreamLayout.setVisible(false);
	        	}
	          }
  
	      });
	    
	    
	    gridPane.add(orgLabel, 0, 5);
	    gridPane.add(orgField, 1, 5);
	    gridPane.add(serviceLabel, 0, 6);
	    gridPane.add(serviceStreamLayout, 1, 6);

	    // Add Submit Button
	    Button submitButton = new Button("Submit");
	    submitButton.setOnAction(new EventHandler<ActionEvent>(){
	        public void handle(ActionEvent e) {
	        	String username = nameField.getText();
	        	String email = emailField.getText();
	        	String orgname = orgField.getValue();
	        	String password = passwordField.getText();
	        	String type = userType.getValue();
	        	ArrayList<String> services = new ArrayList<String>();
	        	String orgID = "-1";
	        	
	        	if(type != UserTypes.ADMIN.getUiName()) {
		        	if (orgname != null) {
		        		orgID = String.valueOf(agencies.indexOf(orgname));
		        	}  
		        	if (type == UserTypes.AGENCY.getUiName()) {
			        	for (Node stream : serviceStreamLayout.getChildrenUnmodifiable()) {
			        		if (((CheckBox)stream).isSelected()) {
			        			services.add(((Labeled) stream).getText());
			        		}
			        	}
		        	}
		        	
	        	} 
	        	boolean complete = false;
	        	if (username != "" && password != "" && email != "") {
	        		if (type == UserTypes.ADMIN.getUiName()) {
	        			complete = true;
	        		} else if (type == UserTypes.STAFF.getUiName() && orgID != "") {
	        			complete = true;
	        		} else if (type == UserTypes.AGENCY.getUiName() && orgID != "" && !services.isEmpty()) {
	        			complete = true;
	        		}
	        	}
	        	// check for a valid email
	        	boolean validEmail = Formatter.checkValidEmail(email);
				if (complete && validEmail) {
	        		HashMap<String, String> userDetails = new HashMap<>();
	        		userDetails.put("UserType", type);
	        		userDetails.put("Username", username);
	        		userDetails.put("Password", password);
	        		userDetails.put("OrganizationID", orgID);
	        		userDetails.put("Email", email);
	        		// get the values of the checkboxes and put truth value
	        		for (String stream : services)
	        			userDetails.put(DatabaseServiceStreams.fromUiName(stream).getDbName(), "TRUE");
	        		boolean submitted = DatabaseHandler.insert("Users", userDetails);
	        		if(submitted) {
	        	        Alert alert = new Alert(AlertType.INFORMATION);
	        	        alert.setHeaderText("Successful Signup!");
	        	        alert.setContentText("Registration form has been submitted!");
	        	        alert.showAndWait();
	        	        primaryStage.close();
	        	        
	        		} else {
		        		Alert alert = new Alert(AlertType.ERROR);		        		 
		        		alert.setTitle("Error alert");
		        		alert.setHeaderText("Signup Failed..");
		        		alert.setContentText("Errors occured during submission..");	        		 
		        		alert.showAndWait();
	        		}
		        	
	        	} else if (complete && !validEmail){
	        		Alert alert = new Alert(AlertType.ERROR);
	        		alert.setTitle("Error alert");
	        		alert.setHeaderText("Invalid Email");
	        		alert.setContentText("Please enter in a valid email!");
	        		alert.showAndWait();

	        	} else {
	        		Alert alert = new Alert(AlertType.ERROR);	        		 
	        		alert.setTitle("Error alert");
	        		alert.setHeaderText("Mandatory Field(s) Missing");
	        		alert.setContentText("Please fill in all the fields!");
	        		alert.showAndWait();
	        	}
	        }
	    });
	    gridPane.add(submitButton, 0, 7, 2, 1);
	    GridPane.setHalignment(submitButton, HPos.CENTER);
	    GridPane.setHalignment(headerLabel, HPos.CENTER);
	    // javafx css has no margin setting, so set it in code
	    GridPane.setMargin(submitButton, new Insets(20, 0,20,0));
	    GridPane.setMargin(headerLabel, new Insets(20, 20,20,20));
	    
	    // define ID for nodes which require CSS formatting
	    serviceLabel.getStyleClass().add("agency-only");
	    serviceStreamLayout.getStyleClass().add("agency-only");
	    gridPane.setId("mainGridPane");
	    headerLabel.setId("header");
	    submitButton.setId("submitButton");
	}


}


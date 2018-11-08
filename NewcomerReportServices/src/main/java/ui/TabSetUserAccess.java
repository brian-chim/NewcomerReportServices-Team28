package ui;

import java.util.ArrayList;

import application.users.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TabSetUserAccess extends Tab {

	TabSetUserAccess(User user, Stage stage) {
		BorderPane bp = new BorderPane();
		this.setText("User Access");
		this.setClosable(false);

		// create an instructional label
		Label delInstructionTxt = new Label("Please input the user ID(s) of user(s) to delete. Please separate users by commas.");
		// create a text field for the user to input the userIds
		TextField userIdInput = new TextField();
		// set the background text
		userIdInput.setPromptText("Example: 0,1,2");
		userIdInput.setMaxWidth(300);
		Button deleteUserButton = new Button("Delete User(s)");
        deleteUserButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		// split the user input by commas
        		String[] usersToDelete = userIdInput.getText().split(","); 
        		ArrayList<String> failedToDelete = new ArrayList<String>();
        		// for now, simply print the users who are desired to be deleted.
        		for (String userId : usersToDelete) {
        			System.out.println("Deleted user " + userId);
        		}
        		// replace the top with below once deleteUser method is ready.
        		/* for (String userId : usersToDelete) {
        			// try to delete the user
        			boolean deleted = DatabaseHandler.deleteUser(userId);
        			if(!deleted) {
        				failedToDelete.add(userId);
        			}
        		} */
        		// check if some user had failed the delete stage
        		if (!failedToDelete.isEmpty()) {
        			// create the alert
        			Alert failed = new Alert(AlertType.ERROR);
        			String failedUsers = "";
        			// add the userId into the string of failed to delete users
        			for (String userId : failedToDelete) {
        				failedUsers += "\n" + userId;
        			}
        			// set the alert content and show it
        			failed.setTitle("Failed!");
        			failed.setHeaderText("Failed!");
        			failed.setContentText("Most of the your selected users were deleted!\nHowever, the following "
        					+ "users were not successfully deleted:" + failedUsers);
        			failed.show();
        		} else {
        			// set the dialog content and show it
        			Alert success = new Alert(AlertType.INFORMATION);
        			success.setTitle("Success!");
        			success.setHeaderText("Success!");
        			success.setContentText("Operation was successful! Users deleted.");
        			success.show();
        		}
        		// reset the input field
    			userIdInput.setText("");
        	}
    	});
        
        // create a VBox to house the deleted user functionality
		VBox delUserFeature = new VBox();
		delUserFeature.getChildren().add(delInstructionTxt);
		delUserFeature.getChildren().add(userIdInput);
		delUserFeature.getChildren().add(deleteUserButton);
		delUserFeature.setAlignment(Pos.CENTER);
		delUserFeature.setSpacing(10);

		// add the delete user functionality into the root
		bp.setBottom(delUserFeature);
		this.setContent(delUserFeature);
	}

}

package ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import application.database.DatabaseHandler;
import application.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSetOrganizationAccess extends Tab {	
	
	TabSetOrganizationAccess(User user, Stage stage) {
		BorderPane bp = new BorderPane();
		this.setText("Organization Access");
		this.setClosable(false);
		Text text = new Text("This is the add organization tab.");
		this.setContent(text);
		
		
		// construct user table
        TableColumn<Map, String> organizationName = new TableColumn<>("Organization Name");
        organizationName.setCellValueFactory(new MapValueFactory("OrganizationName"));
        TableView table = new TableView<>(buildOrganizationTable());
        table.getColumns().setAll(organizationName);
        table.setItems(buildOrganizationTable());    

        table.setMaxWidth(450);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
	    // create an instructional label
		Label addInstructionTxt = new Label("Please input the name of the new Organization you would like to add");
		// create a text field for the user to input the userIds
		TextField orgNameInput = new TextField();
		orgNameInput.setMaxWidth(300);
		Button addOrgButton = new Button("Add Organization");
		
		addOrgButton.setOnAction(new EventHandler<ActionEvent>() {
        	public void handle(ActionEvent event) {
        		// TODO: handle organization being added
        	}
    	});
		
		// create a VBox to house the deleted user functionality
		VBox addOrg = new VBox();
		addOrg.getChildren().add(table);
		addOrg.getChildren().add(addInstructionTxt);
		addOrg.getChildren().add(orgNameInput);
		addOrg.getChildren().add(addOrgButton);
		addOrg.setAlignment(Pos.CENTER);
		addOrg.setSpacing(10);

		// add the delete user functionality into the root
		bp.setBottom(addOrg);
		this.setContent(addOrg);
	}

	
	private ObservableList<HashMap<String, String>> buildOrganizationTable(){
		ObservableList<HashMap<String, String>> tableData = FXCollections.observableArrayList();
		ArrayList<String> raw = DatabaseHandler.getAgencies();
		HashMap<String, String> orgsMap = new HashMap<>();
		
		for(String org : raw) {
			orgsMap.put("OrganizationName", org);
		}
		tableData.add(orgsMap);
		return tableData;
		
	}
}


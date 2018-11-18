package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import application.database.DatabaseHandler;
import application.users.User;
import application.util.DatabaseServiceStreams;
import application.util.FileParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TabUpdate extends Tab {

	TabUpdate(User user, final Stage stage) {
		this.setClosable(false);
		this.setText("Update Files");

		final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Upload a single file");
        final Button openMultipleButton = new Button("Upload multiple files");
        final Button updateButton = new Button("UPDATE");
        
        
 
        // vertical container for butons and dropdown
        VBox vbox = new VBox();
        vbox.setId("container");
        vbox.setMinWidth(200);
        vbox.setAlignment(Pos.CENTER);
        
        // horizontal button row for single and multi file selection
        HBox buttonRow = new HBox();
        buttonRow.setId("uploadHbox");
        buttonRow.setMinWidth(700);
        buttonRow.setAlignment(Pos.CENTER);
        buttonRow.getChildren().addAll(openButton, openMultipleButton);
        
        // service stream dropdown selector row
        HBox serviceDropdownSelectorRow = new HBox();
        serviceDropdownSelectorRow.setMinWidth(700);
        serviceDropdownSelectorRow.setAlignment(Pos.CENTER);
        ComboBox<String> serviceStream = getServiceStreamDropdown(user);
        serviceDropdownSelectorRow.getChildren().addAll(serviceStream);
        
        // a text area displaying selected file path
        final TextArea filePath = new TextArea();
        filePath.setMaxWidth(400);
        filePath.setMaxHeight(10);
        filePath.setEditable(false);

        
        // everything put together
        vbox.getChildren().addAll(buttonRow, serviceDropdownSelectorRow, filePath, updateButton);
        this.setContent(vbox);
        
        // from the oracle file chooser docs
        // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
        // click handler for single file selector
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                    	filePath.setText(file.getPath());
                    }
                }
            });
        // click handler for multi file selector
        openMultipleButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    List<File> list =
                        fileChooser.showOpenMultipleDialog(stage);
                    if (list != null) {
                    	String paths = "";
                        for (File file : list) {
                            if (file != null) {
                            	paths += file.getPath() + ";";
                            }
                        }
                        filePath.setText(paths);
                    }
                }
            });

        updateButton.setOnAction(
        	new EventHandler<ActionEvent>() {
        		@Override
                public void handle(final ActionEvent e) {
        			DatabaseServiceStreams streamEnum = DatabaseServiceStreams.fromUiName(serviceStream.getValue());
        			String[] paths = filePath.getText().split(";");
        			for (String path : paths) {
                        try {
                            ArrayList<HashMap<String, String>> data = FileParser.readSpreadsheet(path, streamEnum.getSheetName());
                            Integer index = 1;
                            for (HashMap<String, String> entry : data) {
                            	if(noMergeConflicts(streamEnum.getDbName(), entry)) {
                            		DatabaseHandler.insert(streamEnum.getDbName(), entry);
                            	}
                            	// send error alert informing user there is a conflict with the row
                            	else {
                            		Alert alert = new Alert(AlertType.ERROR);	        		 
                	        		alert.setTitle("Error alert");
                	        		alert.setHeaderText("Format Error");
                	        		alert.setContentText("There are merge conflicts with the file in row: " + index + ", please ensure the data is 100% accurate. If so, please update through the upload tab.");
                	        		
                	        		alert.showAndWait();
                            	}
                                index++;
                            }
                        }
                        // if there is an error -- should be fileNotFoundException, alert user
                        catch (Exception error) {
                            
                            Alert alert = new Alert(AlertType.ERROR);	        		 
        	        		alert.setTitle("Retrieval Error");
        	        		alert.setHeaderText("There was an issue retrieving the file(s)");
        	        		alert.setContentText("Please ensure file(s) are selected and try again.");
        	        		
        	        		alert.showAndWait();
                        }
        			}
        		}
        		
        	}
        );
	}
	
	/**
	 * checks for merge conflicts the given row.
	 * 
	 * NOTE: when being properly implemented, might need to change the params
	 * 
	 * @param stream - service stream being updated
	 * @param values - values being added
	 * @return - true if no conflict, false otherwise
	 */
	private boolean noMergeConflicts(String stream, HashMap<String, String> values) {
		// TODO: implement properly
		return false;
	}
	
	// dropdown for service streams
	private ComboBox<String> getServiceStreamDropdown(User user) {
		ComboBox<String> serviceStream;
		ArrayList<String> services = new ArrayList<String>();
		HashMap<DatabaseServiceStreams, Boolean> userStreams = user.getServiceStreams();
		for (DatabaseServiceStreams stream : userStreams.keySet()) {
			if (userStreams.get(stream)) {
				services.add(stream.getUiName());
			}
		}
		ObservableList<String> typeOptions = FXCollections.observableArrayList(services);
				
		serviceStream = new ComboBox<String>(typeOptions);
		serviceStream.setPromptText("Select a service stream");
		return serviceStream;
	}
}

package ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import application.util.FileParser;
import application.database.DatabaseHandler;
import application.users.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.POIXMLException;

public class TabUpload extends Tab {
	
	 TabUpload(User user, final Stage stage) {
		this.setClosable(false);

		final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Upload a single file");
        final Button openMultipleButton = new Button("Upload multiple files");
        final Button uploadButton = new Button("UPLOAD");
 
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
        serviceDropdownSelectorRow.getChildren().addAll(getServiceStreamDropdown());
        
        // a text area displaying selected file path
        TextArea filePath = new TextArea();
        filePath.setMaxWidth(400);
        filePath.setMaxHeight(10);
        filePath.setEditable(false);

        
        // everything put together
        vbox.getChildren().addAll(buttonRow, serviceDropdownSelectorRow, filePath, uploadButton);
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
//                        try {
//                            HashMap<String, String> data = FileParser.readSpreadsheet(file.getPath(), "Employment");
//                            DatabaseHandler.insert("EmploymentServiceStream", data);
//                        } catch (POIXMLException error) {
//                            error.printStackTrace();
//                        }
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
//                                try {
//                                    HashMap<String, String> data = FileParser.readSpreadsheet(file.getPath(), "Employment");
//                                    DatabaseHandler.insert("EmploymentServiceStream", data);
//                                } catch (POIXMLException error) {
//                                    error.printStackTrace();
//                                }
                            }
                        }
                        filePath.setText(paths);
                    }
                }
            });
        this.setText("Upload Files");
        
        uploadButton.setOnAction(
        	new EventHandler<ActionEvent>() {
        		@Override
                public void handle(final ActionEvent e) {
        			String[] paths = filePath.getText().split(";");
        			for (String path : paths) {        				
        				try {	
        					HashMap<String, String> data = FileParser.readSpreadsheet(path, "Employment");
        					System.out.println(data);
        					DatabaseHandler.insert("EmploymentServiceStream", data);
        				} catch (POIXMLException error) {
        					error.printStackTrace();
        				}
        			}
        		}
        		
        	}
        );
	}
	// dropdown for service streams
	private ComboBox<String> getServiceStreamDropdown() {
		ComboBox<String> serviceStream;
		ArrayList<String> services = DatabaseHandler.getServiceStreams();
		ObservableList<String> typeOptions = FXCollections.observableArrayList(services);
				
		serviceStream = new ComboBox<String>(typeOptions);
		serviceStream.setPromptText("Select a service stream");
		return serviceStream;
	}
}

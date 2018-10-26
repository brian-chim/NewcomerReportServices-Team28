package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import application.database.DatabaseHandler;
import application.users.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Home extends Application {

	public void start(Stage primaryStage, User user) {
  	    Stage newStage = new Stage();
  	    newStage.show();
  	  
	  	TabPane tp = new TabPane();
	    Scene scene = new Scene(tp, 900, 500);
	    tp.setSide(Side.LEFT);
		tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tp.getTabs().add(this.getUploadTab(newStage));
		
		tp.setId("tp");
		scene.getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
		newStage.setScene(scene);
		newStage.setTitle("Newcomer Home");
		newStage.setResizable(true);
		newStage.show();
	}
	
	private Tab getUploadTab(Stage stage) {
		Tab tab = new Tab("Upload");
		
		final FileChooser fileChooser = new FileChooser();
        final Button openButton = new Button("Upload a single file");
        final Button openMultipleButton = new Button("Upload multiple files");
 
        // vertical container for butons and dropdown
        VBox vbox = new VBox();
        vbox.setId("container");
        vbox.setMinWidth(700);
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
        
        // everything put together
        vbox.getChildren().addAll(buttonRow, serviceDropdownSelectorRow);
        tab.setContent(vbox);
        
        // from the oracle file chooser docs
        // https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm
        // click handler for single file selector
        openButton.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    File file = fileChooser.showOpenDialog(stage);
                    if (file != null) {
                    	// TODO: HANDLE FILE SELECTED
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
                        for (File file : list) {
                        	// TODO: HANDLE FILE SELECTED
                        }
                    }
                }
            });
        return tab;
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

	// this is only to satisfy the Application interface's unimplemented method
	// since home should always be started with a user, throw an exception if this
	// start method is called
	@Override
	public void start(Stage stage) throws Exception {
		throw new Exception();
	}
}

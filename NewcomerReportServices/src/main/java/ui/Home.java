package ui;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import application.users.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
		scene.getStylesheets().add(getClass().getResource("home.css").toExternalForm());
		newStage.setScene(scene);
		newStage.setTitle("Newcomer Home");
		newStage.setResizable(true);
		newStage.show();
	}
	
	private Tab getUploadTab(Stage stage) {
		Tab tab = new Tab("Upload");
		
		//stage.setTitle("File Chooser Sample");
		final FileChooser fileChooser = new FileChooser();
		
        final Button openButton = new Button("Upload a single file");
        final Button openMultipleButton = new Button("Upload multiple files");
 
        HBox hbox = new HBox();
        hbox.setId("uploadHbox");
        hbox.setMinWidth(700);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(openButton, openMultipleButton);
        tab.setContent(hbox);
        
        openButton.setOnAction(
	            new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(final ActionEvent e) {
	                    File file = fileChooser.showOpenDialog(stage);
	                    if (file != null) {
	                    	openFile(file);
	                    }
	                }
	            });
        openMultipleButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        List<File> list =
                            fileChooser.showOpenMultipleDialog(stage);
                        if (list != null) {
                            for (File file : list) {
                                openFile(file);
                            }
                        }
                    }
                });
        
        return tab;
	}
	
	private void openFile(File file) {
		final Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException ex) {
           
        }
    }
	
	

	// this is only to satisfy the Application interface's unimplemented method
	// since home should always be started with a user, throw an exception if this
	// start method is called
	@Override
	public void start(Stage stage) throws Exception {
		throw new Exception();
	}

}

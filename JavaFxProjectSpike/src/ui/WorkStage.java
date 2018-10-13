package ui;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Optional;

import application.User;

public class WorkStage extends Application {

	public void start(Stage primaryStage, User user) {
		
		TabPane tp = new TabPane();
	    Scene scene = new Scene(tp, 900, 900);
	    tp.setSide(Side.LEFT);
	    tp.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	    tp.getTabs().add(new Tab("User Profile"));
	    ArrayList<String> userInfo = (ArrayList<String>) user.getUserInfo();
	       for (String line : userInfo.subList(1, userInfo.size())) {
	    	   Tab tab = new Tab(line);
	           
	            HBox hbox = new HBox();
	            hbox.setMinWidth(700);
	            hbox.setAlignment(Pos.CENTER);
	            tab.setContent(hbox);
	            tp.getTabs().add(tab);
	        }
	    tp.getTabs().add(this.setLogOutTab(primaryStage));
	       
	    tp.setId("tp");
	     scene.getStylesheets().add(getClass().getResource("workstage.css").toExternalForm());
	     primaryStage.setScene(scene);
	     primaryStage.setTitle("Application Spike");
	     primaryStage.setResizable(false);
	     primaryStage.show();
		
	}
	
	private Tab setLogOutTab(Stage primaryStage) {
		Tab tab = new Tab("Log out");
		tab.setOnSelectionChanged(new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
				if(tab.isSelected()) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Log out");
					String s = "Are you sure to log out?";
					alert.setContentText(s);

					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == ButtonType.OK) {
			        	  Stage newStage = new Stage();
			        	  new Login().start(newStage);
			        	  primaryStage.close();
					} 
				}
			}});
		return tab;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
  	  User user = new User("test", "test");
  	  Stage newStage = new Stage();
  	  new WorkStage().start(newStage, user);
		
	}
	
    public static void main(String[] args) {
        launch(args);
    }
	

}

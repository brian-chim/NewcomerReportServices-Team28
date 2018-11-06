package ui;

import application.users.User;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Home extends Application {

	public void start(Stage stage, User user) {
	  // create a tab pane to host whatever tabs the user will have available
	  TabPane pane = TabFactory.createTabs(user, stage);
	  // create the scene
	  Scene reportServices = new Scene(pane, 1200, 700);
	  reportServices.getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
	  stage.setTitle("Newcomer's Report Services");
  	  stage.setScene(reportServices);
  	  stage.show();
	}

	// this is only to satisfy the Application interface's unimplemented method
	// since home should always be started with a user, throw an exception if this
	// start method is called
	@Override
	public void start(Stage stage) throws Exception {
		throw new Exception();
	}
}

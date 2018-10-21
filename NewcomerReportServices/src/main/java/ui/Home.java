package ui;

import application.users.User;
import javafx.application.Application;
import javafx.stage.Stage;

public class Home extends Application {

	public void start(Stage primaryStage, User user) {
  	  Stage newStage = new Stage();
  	  newStage.show();
		
	}

	// this is only to satisfy the Application interface's unimplemented method
	// since home should always be started with a user, throw an exception if this
	// start method is called
	@Override
	public void start(Stage stage) throws Exception {
		throw new Exception();
	}

}

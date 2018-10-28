package ui;

import java.util.HashMap;

import application.users.User;
import application.users.UserPermissions;
import javafx.application.Application;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Home extends Application {

	public void start(Stage primaryStage, User user) {
	  // create a tab pane to host whatever tabs the user will have available
	  TabPane pane = new TabPane();
	  // add a home tab that will be present for all kinds of users
	  pane.getTabs().add(new TabHome(user));
	  // get the permissions of the user
	  HashMap<UserPermissions, Boolean> permissions = user.getPermissions();
	  // add a tab for each permission
	  for (UserPermissions userPermission : permissions.keySet()) {
		  if (permissions.get(userPermission)) {
			  Tab tab;
			  switch (userPermission) {
			  	case UPLOADFILES: 
			  		tab = new TabUpload(user);
			  		break;
			  	case GENERATESUMMARYREPORT:
			  		tab = new TabSummaryReport(user);
			  		break;
			  	case GENERATETRENDREPORT:
			  		tab = new TabTrendReport(user);
			  		break;
			  	case SETACCESS:
			  		tab = new TabSetAccess(user);
			  		break;
			  	default:
			  		tab = new Tab();
			  }
			  pane.getTabs().add(tab);
		  }
		  pane.setSide(Side.LEFT);
	  }
	  // create the scene
	  Scene reportServices = new Scene(pane, 1200, 700);
	  reportServices.getStylesheets().add(getClass().getResource("/home.css").toExternalForm());
	  primaryStage.setTitle("Newcomer's Report Services");
  	  primaryStage.setScene(reportServices);
  	  primaryStage.show();
	}

	// this is only to satisfy the Application interface's unimplemented method
	// since home should always be started with a user, throw an exception if this
	// start method is called
	@Override
	public void start(Stage stage) throws Exception {
		throw new Exception();
	}

}

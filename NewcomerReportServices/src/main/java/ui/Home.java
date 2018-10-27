package ui;

import java.util.HashMap;
import java.util.Set;

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
	  HashMap<UserPermissions, Boolean> permissions = user.getPermissions();
	  for (UserPermissions userPermission : permissions.keySet()) {
		  if (permissions.get(userPermission)) {
			  Tab tab;
			  switch (userPermission) {
			  	case UPLOADFILES: 
			  		tab = new TabUpload(user);
			  	case GENERATESUMMARYREPORT:
			  		tab = new TabSummaryReport(user);
			  	case GENERATETRENDREPORT:
			  		tab = new TabTrendReport(user);
			  	case SETACCESS:
			  		tab = new TabSetAccess(user);
			  	default:
			  		tab = new Tab();
			  }
			  pane.getTabs().add(tab);
			  pane.setSide(Side.LEFT);
		  }
	  }
	  Scene reportServices = new Scene(pane);
	  // add tabs based on user permissions
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

package ui;

import javafx.geometry.Side;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.HashMap;

import application.users.User;
import application.users.UserPermissions;

public class TabFactory {
	
	public static TabPane createTabs(User user, Stage stage) {
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
				  		tab = new TabUpload(user, stage);
				  		break;
				  	case UPDATEFILES:
				  		tab = new TabUpdate(user, stage);
				  		break;
				  	case GENERATESUMMARYREPORT:
				  		tab = new TabSummaryReport(user, stage);
				  		break;
				  	case GENERATETRENDREPORT:
				  		tab = new TabTrendReport(user, stage);
				  		break;
				  	case SETUSERACCESS:
				  		tab = new TabSetUserAccess(user, stage);
				  		break;
				  	case SETORGANIZATIONACCESS:
				  		tab = new TabSetOrganizationAccess(user, stage);
				  		break;
				  	default:
				  		tab = new Tab();
				  }
				  pane.getTabs().add(tab);
			  }
			  pane.setSide(Side.LEFT);
		  }
		  return pane;
	}

}

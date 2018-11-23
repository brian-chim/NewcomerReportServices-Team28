package ui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Optional;

import application.users.User;
import application.users.UserPermissions;

public class TabFactory {
	
	public static TabPane createTabs(User user, Stage stage) {
		  TabPane pane = new TabPane();
		  // add a home tab that will be present for all kinds of users
		  pane.getTabs().add(new TabHome(user));
		  // get the permissions of the user
		  HashMap<UserPermissions, Boolean> permissions = user.permissions;
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
		  }
		  pane.getTabs().add(createLogOutTab(stage, pane));
		  pane.setSide(Side.LEFT);
		  return pane;
	}
	
	private static Tab createLogOutTab(Stage stage, TabPane pane) {
		Tab tab = new Tab("Log out");
		tab.setClosable(false);
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
			        	  stage.close();
					}
					if (result.get() == ButtonType.CANCEL) {
						// return to the Home tab
						pane.getSelectionModel().select(0);
					}
				}
			}
		});
		return tab;
	}

}

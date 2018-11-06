package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSetOrganizationAccess extends Tab {	
	TabSetOrganizationAccess(User user, Stage stage) {
		this.setText("Organization Access");
		this.setClosable(false);
		Text text = new Text("This is the add organization tab.");
		this.setContent(text);
	}

}


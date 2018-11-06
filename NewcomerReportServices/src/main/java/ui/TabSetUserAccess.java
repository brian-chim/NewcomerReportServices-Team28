package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSetUserAccess extends Tab {

	TabSetUserAccess(User user, Stage stage) {
		this.setText("User Access");
		this.setClosable(false);
		Text text = new Text("This is the set user access tab.");
		this.setContent(text);
	}

}

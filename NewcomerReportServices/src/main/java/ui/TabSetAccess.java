package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSetAccess extends Tab {

	TabSetAccess(User user, Stage stage) {
		this.setText("Set Access");
		this.setClosable(false);
		Text text = new Text("This is the set access tab.");
		this.setContent(text);
	}

}

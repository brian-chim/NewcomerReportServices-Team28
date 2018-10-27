package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class TabUpload extends Tab {
	TabUpload(User user) {
		this.setText("Upload Files");
		Text text = new Text("This is the upload tab.");
		this.setContent(text);
	}
}

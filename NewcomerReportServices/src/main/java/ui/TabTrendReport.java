package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class TabTrendReport extends Tab {

	TabTrendReport(User user) {
		this.setText("Trend Reports");
		Text text = new Text("This is the trend report tab.");
		this.setContent(text);
	}

}

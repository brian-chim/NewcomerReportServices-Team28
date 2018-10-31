package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabTrendReport extends Tab {

	TabTrendReport(User user, Stage stage) {
		this.setText("Trend Reports");
		this.setClosable(false);
		Text text = new Text("This is the trend report tab.");
		this.setContent(text);
	}

}

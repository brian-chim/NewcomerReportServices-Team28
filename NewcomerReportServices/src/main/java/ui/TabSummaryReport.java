package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSummaryReport extends Tab {

	TabSummaryReport(User user, Stage stage) {
		this.setText("Summary Reports");
		this.setClosable(false);
		Text text = new Text("This is the summary report tab.");
		this.setContent(text);
	}
}

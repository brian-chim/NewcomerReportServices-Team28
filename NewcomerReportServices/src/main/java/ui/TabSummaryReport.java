package ui;

import application.users.User;
import javafx.scene.control.Tab;
import javafx.scene.text.Text;

public class TabSummaryReport extends Tab {

	TabSummaryReport(User user) {
		this.setText("Summary Reports");
		Text text = new Text("This is the summary report tab.");
		this.setContent(text);
	}
}

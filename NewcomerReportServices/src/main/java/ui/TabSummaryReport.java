package ui;

import java.util.ArrayList;

import application.users.User;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSummaryReport extends Tab {

	TabSummaryReport(User user, Stage stage) {
		this.setText("Summary Reports");
		this.setClosable(false);
		Text text = new Text("This is the summary report tab.");
		GridPane gp = createCheckboxScheme();
		this.setContent(text);
	}

	private GridPane createCheckboxScheme() {
		GridPane gp = new GridPane();
		Text filterText = new Text("Filter By:");
		ArrayList<String> checkboxNames = new ArrayList<String>();
		gp.add(filterText, 0, 0, 2, 1);
		gp.setGridLinesVisible(true);
		gp.setAlignment(Pos.CENTER);
		return gp;
	}
}

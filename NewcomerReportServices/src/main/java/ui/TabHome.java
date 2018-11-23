package ui;

import application.users.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import java.util.Date;
import java.text.SimpleDateFormat;

public class TabHome extends Tab {

	TabHome(User user) {
		BorderPane bp = createHomeTab(user);
		this.setText("Home");
		this.setClosable(false);
		this.setContent(bp);
	}

	private BorderPane createHomeTab(User user) {
		// create the date with formatter
		Date date = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM d, yyyy");
		BorderPane bp = new BorderPane();
		Text currDate = new Text(dateFormatter.format(date));
		currDate.setId("date");
		// put the current date at the top right
		bp.setTop(currDate);
		BorderPane.setAlignment(currDate, Pos.CENTER_RIGHT);
		// set the text inside the borderpane
		GridPane gp = setGridText(user);
		bp.setCenter(gp);
		BorderPane.setAlignment(gp, Pos.CENTER);
		BorderPane.setMargin(gp, new Insets(12, 12, 12, 12));
		BorderPane.setMargin(currDate, new Insets(12, 12, 12, 12));
		return bp;
	}
	
	private GridPane setGridText(User user) {
		Text welcomeText = new Text("Welcome " + user.username + "!");
		welcomeText.setId("welcome");
		Text info = new Text("The Newcomer's Report Service is an application "
				+ "designed to centralize the information between different "
				+ "organizations and agencies with the goal to benefit all newcomers.");
		info.setId("info");
		info.setWrappingWidth(900);
		GridPane gp = new GridPane();
		gp.add(welcomeText, 0, 0);
		gp.add(info, 0, 1);
		gp.setAlignment(Pos.CENTER);
		GridPane.setMargin(info, new Insets(30, 30, 0, 0));
		return gp;
	}
}

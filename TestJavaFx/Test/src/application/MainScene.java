package application;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainScene extends GridPane {
	
	private Stage stage;
	MainScene(Stage stage) {
		this.setAlignment(Pos.TOP_CENTER);
		// x (column), y (row)
		this.stage = stage;
		OptionsToolBar options = new OptionsToolBar(stage);
		this.add(options, 0, 0);
		stage.setTitle("Home");
		Label title = new Label("This is the home page");
		this.add(title, 0, 1);
	}
}

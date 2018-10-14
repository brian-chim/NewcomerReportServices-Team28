package application;

import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OptionFour extends GridPane {
	
	private Stage stage;
	OptionFour(Stage stage) {
		// x (column), y (row)
		this.stage = stage;
		OptionsToolBar options = new OptionsToolBar(stage);
		this.add(options, 0, 0);
		stage.setTitle("Option Four");
		
	}
}

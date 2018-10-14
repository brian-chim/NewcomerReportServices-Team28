package application;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OptionSix extends GridPane {
	
	private Stage stage;
	OptionSix(Stage stage) {
		// x (column), y (row)
		this.stage = stage;
		OptionsToolBar options = new OptionsToolBar(stage);
		this.add(options, 0, 0);
		stage.setTitle("Option Six");
		
	}
}

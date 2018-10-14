package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class OptionsToolBar extends HBox {
	
	OptionsToolBar(Stage stage) {
		// x (column), y (row)
		Button catOne = new Button("Home");
		catOne.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GridPane home = new MainScene(stage);
				stage.setScene(new Scene(home, 800, 600));
			}
		});

		Button catTwo = new Button("Option Two");
		catTwo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GridPane optionTwo = new OptionTwo(stage);
				stage.setScene(new Scene(optionTwo, 800, 600));
			}
		});
		
		Button catThree = new Button("Option Three");
		catThree.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GridPane optionThree = new OptionThree(stage);
				stage.setScene(new Scene(optionThree, 800, 600));
			}
		});
		
		Button catFour = new Button("Option Four");
		catFour.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GridPane optionFour = new OptionFour(stage);
				stage.setScene(new Scene(optionFour, 800, 600));
			}
		});
		
		Button catFive = new Button("Option Five");
		catFive.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GridPane optionFive = new OptionFive(stage);
				stage.setScene(new Scene(optionFive, 800, 600));
			}
		});
		
		Button catSix = new Button("Option Six");
		catSix.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				GridPane optionSix = new OptionSix (stage);
				stage.setScene(new Scene(optionSix, 800, 600));
			}
		});
		
		this.getChildren().add(catOne);
		this.getChildren().add(catTwo);
		this.getChildren().add(catThree);
		this.getChildren().add(catFour);
		this.getChildren().add(catFive);
		this.getChildren().add(catSix);
	}
}

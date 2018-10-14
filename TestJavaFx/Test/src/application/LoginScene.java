package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginScene extends GridPane {
	
	private Stage stage;
	LoginScene(Stage stage, boolean first) {
		// x (column), y (row)
		this.stage = stage;
		// 0 = success, 1 = fail, 2 = new scene
		this.setAlignment(Pos.CENTER);
		Label title = new Label("Login");
		Text username = new Text("Username: ");
		TextField userField = new TextField();
		Text password = new Text("Password: ");
		PasswordField passField = new PasswordField();
		Button login = new Button("Login");

		EventHandler<MouseEvent> loginClick = new EventHandler<MouseEvent>() {
			boolean pass = true;
			@Override
			public void handle(MouseEvent e) {
				System.out.println(userField.getText() + passField.getText());
				if(userField.getText().equals("username") && passField.getText().equals("password")) {
					// success scenario
					// find out what user
					// set appropriate scene
					Scene mainScene = new Scene(new MainScene(stage), 800, 600);
					stage.setScene(mainScene);
				} 
				else {
					// fail scenario
					Scene failedLogin = new Scene(new LoginScene(stage, false), 800, 600);
					stage.setScene(failedLogin);
				}
			}
		};
		login.addEventHandler(MouseEvent.MOUSE_CLICKED, loginClick);
		// if it isn't the first attempt to login, they have attempted to login already so add
		// incorrect message
		if (first == false) {
			Text failed = new Text("Incorrect username/password.");
			this.add(failed, 0, 4, 2, 1);
		}
		this.add(title, 0, 0, 2, 1);
		this.add(username, 0, 1);
		this.add(userField, 1, 1);
		this.add(password, 0, 2);
		this.add(passField, 1, 2);
		this.add(login, 0, 3, 2, 1);
	}
}

package ui;

import java.util.ArrayList;

import application.database.DatabaseHandler;
import application.users.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSummaryReport extends Tab {

	TabSummaryReport(User user, Stage stage) {
		this.setText("Summary Reports");
		this.setClosable(false);
		GridPane gp = new GridPane();

		HBox serviceStream = new HBox();
		ComboBox<String> streamDropdown = getServiceStreamDropdown();
		serviceStream.setMinWidth(700);
		serviceStream.getChildren().add(streamDropdown);
	    streamDropdown.valueProperty().addListener(new ChangeListener<String>() {
	        @Override 
	        public void changed(ObservableValue ov, String prev, String curr) {
	        	if (curr != "") {
	        		
	        	}
	        }});

		gp.add(serviceStream, 0, 0, 2, 1);

		ArrayList<String> queries = new ArrayList<String>();
		switch(streamDropdown.getValue()) {
		
		}
		this.setContent(gp);
	}

	private ComboBox<String> getServiceStreamDropdown() {
		ComboBox<String> serviceStream;
		ArrayList<String> services = DatabaseHandler.getServiceStreams();
		ObservableList<String> typeOptions = FXCollections.observableArrayList(services);

		serviceStream = new ComboBox<String>(typeOptions);
		serviceStream.setPromptText("Select a service stream");
		return serviceStream;
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

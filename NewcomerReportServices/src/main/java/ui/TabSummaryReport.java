package ui;

import java.util.ArrayList;
import java.util.HashMap;

import application.database.DatabaseHandler;
import application.users.User;
import application.util.DatabaseServiceStreams;
import application.util.EmploymentStreamColumnQueries;
import application.util.NeedsAssessmentsColumnQueries;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.layout.FlowPane;
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
		// create a flowpane for the checkboxes
		final FlowPane fp = new FlowPane();
		Text defaultText = new Text("Please select a service stream.");
		fp.getChildren().add(defaultText);
		// key is the stream, value is the list of queries
		final HashMap<String, ArrayList<String>> allQueries = createQueries();
	    final Button genReport = new Button("Generate Report");
	    genReport.setDisable(true);
		final Text noQueries = new Text("Unfortunately we do not support this stream yet.");
		// add a listener for whenever the value changes
	    streamDropdown.valueProperty().addListener(new ChangeListener<String>() {
	        @Override 
	        public void changed(ObservableValue ov, String prev, String curr) {
	        	fp.getChildren().clear();
	    		ArrayList<String> streamQueries = allQueries.get(curr);
	    		if (streamQueries != null) {
	    			for (String query : streamQueries) {
	    				fp.getChildren().add(new CheckBox(query));
	    			}
	    			genReport.setDisable(false);
	    		} else {
	    			// so no checkboxes
	    			fp.getChildren().add(noQueries);
	    			noQueries.setVisible(true);
	    			genReport.setDisable(true);
	    		}
	        }});
	    
		gp.add(serviceStream, 0, 0);
		gp.add(fp, 0, 1);
		gp.add(genReport, 0, 3);
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

	private HashMap<String, ArrayList<String>> createQueries() {
		HashMap<String, ArrayList<String>> queries = new HashMap<String, ArrayList<String>>();
		ArrayList<String> employmentStreamQueries = new ArrayList<String>();
		// get the queries from Employment Stream
		for (EmploymentStreamColumnQueries query : EmploymentStreamColumnQueries.values()) {
			employmentStreamQueries.add(query.getUiName());
		}
		queries.put(DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES.getName(), employmentStreamQueries);
		
		// add NARS Stream
		ArrayList<String> narsStreamQueries = new ArrayList<String>();
		for (NeedsAssessmentsColumnQueries query : NeedsAssessmentsColumnQueries.values()) {
			narsStreamQueries.add(query.getUiName());
		}
		queries.put(DatabaseServiceStreams.NEEDSASSESSMENT.getName(), narsStreamQueries);
		return queries;
	}
	
}

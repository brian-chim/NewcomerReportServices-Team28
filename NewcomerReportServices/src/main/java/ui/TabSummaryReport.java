package ui;

import java.util.ArrayList;
import java.util.HashMap;

import application.database.DatabaseHandler;
import application.users.User;
import application.util.CommunityConnectionsColumnQueries;
import application.util.DatabaseServiceStreams;
import application.util.EmploymentStreamColumnQueries;
import application.util.NeedsAssessmentsColumnQueries;
import application.util.ReportDirectory;
import application.util.ReportGenerator;
import application.util.WriteReport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TabSummaryReport extends Tab {
	
	TabSummaryReport(User user, Stage stage) {
		this.setText("Summary Reports");
		this.setClosable(false);
		VBox root = new VBox();

		// create the service stream dropdown
		HBox serviceStream = new HBox();
		final ComboBox<String> streamDropdown = getServiceStreamDropdown();
		serviceStream.setMinWidth(700);
		serviceStream.getChildren().add(streamDropdown);

		// create a flowpane for the checkboxes
		final FlowPane fp = new FlowPane();

		// key is the stream, value is the list of queries
		final HashMap<String, ArrayList<String>> allQueries = createQueries();
	    final Button genReport = new Button("Generate Report");
	    // set the generate report button as disabled on first load
	    genReport.setDisable(true);
		final Text noQueries = new Text("Unfortunately we do not support this stream yet.");
		// add a listener for whenever the dropdown value changes
	    streamDropdown.valueProperty().addListener(new ChangeListener<String>() {
	        @Override 
	        public void changed(ObservableValue ov, String prev, String curr) {
	        	// clear the checkboxes
	        	fp.getChildren().clear();
	        	// get the specific queries of the service stream
	    		ArrayList<String> streamQueries = allQueries.get(curr);
	    		if (streamQueries != null) {
	    			for (String query : streamQueries) {
	    				// add a checkbox for each query inside the enum
	    				fp.getChildren().add(new CheckBox(query));
	    			}
	    			// enable the button because queries were chosen
	    			genReport.setDisable(false);
	    		} else {
	    			// so no checkboxes
	    			fp.getChildren().add(noQueries);
	    			// set the default "not supported" text to be visible
	    			noQueries.setVisible(true);
	    			// set the button to be disabled
	    			genReport.setDisable(true);
	    		}
	        }});
	    
	    // set the listener of the button
        genReport.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                    	// first figure out what stream we are handling
                    	String handlingStream = streamDropdown.getValue();
                    	
                    	ArrayList<String> ReportCols = new ArrayList<>();
                    	String tableName = new String();
                    	
                    	// for now print the selected query boxes with its column name in db
                    	for (Node checkbox : fp.getChildren()) {
                    		if (checkbox instanceof CheckBox) {
                    			if (((CheckBox)checkbox).isSelected()) {
                    				if (handlingStream.equals(DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES.getUiName()) ) {
                    					// get the text from the checkbox, then get the enum, then get the db name from enum
                    					ReportCols.add(EmploymentStreamColumnQueries.fromUiName(((CheckBox)checkbox).getText()).getDbName());
                    					tableName = DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES.getDbName();
                    				} else if (handlingStream.equals(DatabaseServiceStreams.NEEDSASSESSMENT.getUiName()) ) {
                    					ReportCols.add(NeedsAssessmentsColumnQueries.fromUiName(((CheckBox)checkbox).getText()).getDbName());
                    					tableName = DatabaseServiceStreams.NEEDSASSESSMENT.getDbName();
                    				} else if (handlingStream.equals(DatabaseServiceStreams.COMMUNITYCONN.getUiName()) ) {
                    					ReportCols.add(CommunityConnectionsColumnQueries.fromUiName(((CheckBox)checkbox).getText()).getDbName());
                    					tableName = DatabaseServiceStreams.COMMUNITYCONN.getDbName();
                    				}
                    				// add else ifs as streams are supported
                    			}
                    		}
                    	}
                    	// call db handler with cols and table
                    	String report = ReportGenerator.generateSummaryReport(tableName, ReportCols, ReportDirectory.SUMMARYREPORT.getName());
                    	// write report to summary report location
                    	WriteReport.toTxt(report, ReportDirectory.SUMMARYREPORT.getName() + "report.txt");
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
						alert.setHeaderText("Reports have been successfully generated!");
						alert.setContentText("The reports can be found at " + ReportDirectory.SUMMARYREPORT.getName());
						alert.showAndWait();
                    }
                });
	    // cap the horizontal area of the checkboxes
	    fp.setMaxWidth(700);
	    // set the vgap between checkboxes
	    fp.setVgap(5);
	    // set the hgap between checkboxes
	    fp.setHgap(10);
	    
	    // create a label for the dropdown
	    Label streamContent = new Label("Service Stream:");
	    root.getChildren().add(streamContent);
	    // align the dropdown in the center and add it
	    serviceStream.setAlignment(Pos.CENTER);
		root.getChildren().add(serviceStream);
		// align the checkbox flowpane to the center and add it
		fp.setAlignment(Pos.CENTER_LEFT);
		root.getChildren().add(fp);
		// add the generate report button
		root.getChildren().add(genReport);
		// center everything inside the content
		root.setAlignment(Pos.CENTER);
		// set the spacing between nodes as 30
		root.setSpacing(30);
		this.setContent(root);
	}

	private ComboBox<String> getServiceStreamDropdown() {
		ComboBox<String> serviceStream;
		// get the service streams
		ArrayList<String> services = DatabaseHandler.getServiceStreams();
		ObservableList<String> typeOptions = FXCollections.observableArrayList(services);
		serviceStream = new ComboBox<String>(typeOptions);
		serviceStream.setPromptText("Please select a service stream:");
		return serviceStream;
	}

	private HashMap<String, ArrayList<String>> createQueries() {
		HashMap<String, ArrayList<String>> queries = new HashMap<String, ArrayList<String>>();

		// add Employment Stream
		ArrayList<String> employmentStreamQueries = new ArrayList<String>();
		for (EmploymentStreamColumnQueries query : EmploymentStreamColumnQueries.values()) {
			employmentStreamQueries.add(query.getUiName());
		}
		queries.put(DatabaseServiceStreams.EMPLOYMENTRELATEDSERVICES.getUiName(), employmentStreamQueries);
		
		// add NARS Stream
		ArrayList<String> narsStreamQueries = new ArrayList<String>();
		for (NeedsAssessmentsColumnQueries query : NeedsAssessmentsColumnQueries.values()) {
			narsStreamQueries.add(query.getUiName());
		}
		queries.put(DatabaseServiceStreams.NEEDSASSESSMENT.getUiName(), narsStreamQueries);
		
		ArrayList<String> commConnStreamQueries = new ArrayList<String>();
		for (CommunityConnectionsColumnQueries query : CommunityConnectionsColumnQueries.values()) {
			commConnStreamQueries.add(query.getUiName());
		}
		queries.put(DatabaseServiceStreams.COMMUNITYCONN.getUiName(), commConnStreamQueries);
		// add streams as desired from above template
		return queries;

	}
	
}

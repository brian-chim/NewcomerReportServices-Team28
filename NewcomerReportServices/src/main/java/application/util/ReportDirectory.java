package application.util;

public enum ReportDirectory {
	SUMMARYREPORT("../SummaryReports/report.txt");
	
	
	private String name;
	
	ReportDirectory(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}

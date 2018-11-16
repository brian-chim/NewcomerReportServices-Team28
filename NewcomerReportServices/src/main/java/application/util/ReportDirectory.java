package application.util;

public enum ReportDirectory {
  
	SUMMARYREPORT("../Summary_Reports/");

	
	
	private String name;
	
	ReportDirectory(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}

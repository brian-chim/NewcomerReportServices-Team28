package application.util;

public enum DatabaseServiceStreams {
	
	EMPLOYMENTRELATEDSERVICES("Employment Related Services"),
	LANGUAGETRAININGSERVICES("Language Training Services"),
	INFOANDORIENTATION("Information & Orientation Services"),
	COMMUNITYCONN("Community Connections Services"),
	NEEDSASSESSMENT("Needs Assessment & Referral Service (NARS)"),
	CLIENTPROFILEBULK("Client Profile Bulk");
	
	private String name;
	
	DatabaseServiceStreams(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}

package application.util;

public enum DatabaseServiceStreams {
	
	EMPLOYMENTRELATEDSERVICES("Employment Related Services", "EmploymentServiceStream", "Employment"),
	LTCLIENTENROL("Language Training Client Enrolment Services", "LanguageTrainingClientEnrol", "LT Client Enrol"),
	LTCLIENTEXIT("Language Training Client Exit Services", "LanguageTrainingClientExit", "LT Client Exit"),
	LTCOURSETUP("Language Training Course Setup Services", "LanguageTrainingCourseSetup", "LT Course Setup"),
	INFOANDORIENTATION("Information & Orientation Services", "InformationAndOrientationServiceStream", "Info&Orien"),
	COMMUNITYCONN("Community Connections Services", "CommunityConnectionsServiceStream", "Community Connections"),
	NEEDSASSESSMENT("Needs Assessment & Referral Service (NARS)", "NeedsAssesmentAndReferralsService", "Needs Assessment&Referrals"),
	CLIENTPROFILEBULK("Client Profile Bulk", "ClientProfile", "Client Profile");
	
	private String uiName;
	private String dbName;
	private String sheetName;
	
	DatabaseServiceStreams(String uiName, String dbName, String sheetName) {
		this.uiName = uiName;
		this.dbName = dbName;
		this.sheetName = sheetName;
	}
	
	public String getUiName() {
		return this.uiName;
	}
	
	public String getDbName() {
		return this.dbName;
	}
	
	public String getSheetName() {
		return this.sheetName;
	}
	
	public static DatabaseServiceStreams fromUiName(String uiName) {
		for (DatabaseServiceStreams stream : DatabaseServiceStreams.values()) {
			if(stream.getUiName().equals(uiName)) {
				return stream;
			}
		}
		return null;
	}
}

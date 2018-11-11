package application.util;

public enum EmploymentStreamColumnQueries {

	CLIENTBIRTHDATE("client_birth_dt", "Client Birth Date"),
	SERVICELANGUAGE("session_service_lang_id", "Language of Service"),
	PREFLANGUAGE("session_official_lang_id", "Preferred Language"),
	INSTITUTIONTYPE("institution_type_id", "Type of Institution for Received Services"),
	REFERREDBY("assessment_referral_id", "Referral Service"),
	REGISTERININTERVENTION("session_result_intn_ind", "Registered in Employment Intervention"),
	EMPLOYMENTSTATUS("session_employment_status_id", "Employment Status"),
	INTERVENTIONTYPE("intervention_type_id", "Intervention Type"),
	CURROCCUPATION("session_current_occupation_id", "Current Occupation"),
	INTENDEDOCCUPATION("session_intended_occupation_id", "Intended Occupation");

	private String dbName;
	private String uiName;

	EmploymentStreamColumnQueries(String dbName, String uiName) {
		this.dbName = dbName;
		this.uiName = uiName;
	}
	
	public String getDbName() {
		return this.dbName;
	}
	
	public String getUiName() {
		return this.uiName;
	}

	public static EmploymentStreamColumnQueries fromUiName(String text) {
		for (EmploymentStreamColumnQueries query : EmploymentStreamColumnQueries.values()) {
			if(query.getUiName().equals(text)) {
				return query;
			}
		}
		return null;
	}
	
	public static EmploymentStreamColumnQueries fromDbName(String text) {
		for (EmploymentStreamColumnQueries query : EmploymentStreamColumnQueries.values()) {
			if(query.getDbName().equals(text)) {
				return query;
			}
		}
		return null;
	}
}

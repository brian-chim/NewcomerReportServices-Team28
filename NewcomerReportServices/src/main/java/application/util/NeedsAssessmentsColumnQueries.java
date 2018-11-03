package application.util;

public enum NeedsAssessmentsColumnQueries {

	CLIENTBIRTHDATE("client_birth_dt", "Client Birth Date"),
	SERVICELANGUAGE("assessment_language_id", "Language of Service"),
	PREFLANGUAGE("preferred_official_language_id", "Preferred Language"),
	INSTITUTIONTYPE("institution_type_id", "Institution Type"),
	REFERREDBY("assessment_referral_id", "Referral Service"),
	FINDEMPLOYMENTNEEDS("find_employment_needs_ind", "Needs Employment Finding Services"),
	CANADIANINTENTION("intention_canadian_citizen_ind", "Intention to become Canadian Citizen"),
	TRANSPORTATIONNEED("transporation_required_ind", "Transporation needed"),
	TRANSLATIONNEED("translation_required_ind", "Translation needed"),
	HOUSINGNEED("housing_accommodation_needs_ind", "Housing needed");

	private String dbName;
	private String uiName;

	NeedsAssessmentsColumnQueries(String dbName, String uiName) {
		this.dbName = dbName;
		this.uiName = uiName;
	}
	
	public String getDbName() {
		return this.dbName;
	}
	
	public String getUiName() {
		return this.uiName;
	}
}

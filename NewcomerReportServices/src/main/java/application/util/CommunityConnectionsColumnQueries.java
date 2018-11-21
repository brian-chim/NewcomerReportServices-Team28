package application.util;

public enum CommunityConnectionsColumnQueries {

	SERVICELANGUAGE("service_lang_id", "Language of Service"),
	PREFLANGUAGE("preferred_official_lang_id", "Preferred Language"),
	INSTITUTIONTYPE("institution_type_id", "Type of Institution for Received Services"),
	REFERREDBY("assessment_referral_id", "Referral Service"),
	COMMUNITYACTIVITY("community_activity_id", "Client Service Activity"),
	SERVICETYPE("service_type_id", "Type of Service"),
	TOPICOFSERVICE("topics_service_id", "Topic of Service Received"),
	TARGETGROUPFAMILYPARENTS("target_group_families_parents_ind", "Target Group: Families and Parents"),
	TARGETGROUPREFUGEES("target_group_refugee_ind", "Target Group: Refugees"),
	TARGETGROUPCULTURE("target_group_ethnic_ind", "Target Group: Cultural");
	
	private String dbName;
	private String uiName;

	CommunityConnectionsColumnQueries(String dbName, String uiName) {
		this.dbName = dbName;
		this.uiName = uiName;
	}
	
	public String getDbName() {
		return this.dbName;
	}
	
	public String getUiName() {
		return this.uiName;
	}

	public static CommunityConnectionsColumnQueries fromUiName(String text) {
		for (CommunityConnectionsColumnQueries query : CommunityConnectionsColumnQueries.values()) {
			if(query.getUiName().equals(text)) {
				return query;
			}
		}
		return null;
	}
	
	public static CommunityConnectionsColumnQueries fromDbName(String text) {
		for (CommunityConnectionsColumnQueries query : CommunityConnectionsColumnQueries.values()) {
			if(query.getDbName().equals(text)) {
				return query;
			}
		}
		return null;
	}
}

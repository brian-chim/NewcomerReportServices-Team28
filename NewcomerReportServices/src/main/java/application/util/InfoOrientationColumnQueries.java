package application.util;

public enum InfoOrientationColumnQueries {
    CLIENTBIRTHDATE("client_birth_dt", "Client Birth Date"),
    SERVICELANGUAGE("service_language_id", "Language of Service"),
    PREFLANGUAGE("service_official_language_id", "Preferred Language"),
    INSTITUTIONTYPE("institution_type_id", "Type of Institution for Received Services"),
    REFERREDBY("service_referred_by_id", "Referral Service"),
    SERVICESRECEIVED("orientation_service_id", "Services Received"),
    ORIENTATIONLENGTH("orientation_length_id", "Total Length of Orientation"),
    ORIENTATIONGROUP("group_clients_no_id", "Number of Clients in Group"),
    CITIZENSHIPTRAINING("topic_citizenship_given_ind", "Becoming a Canadian Citizen"),
    SERVICEENDDATE("end_dttm", "Service End Date");

    private String dbName;
    private String uiName;

    InfoOrientationColumnQueries(String dbName, String uiName) {
        this.dbName = dbName;
        this.uiName = uiName;
    }

    public String getDbName() {
        return this.dbName;
    }

    public String getUiName() {
        return this.uiName;
    }

    public static InfoOrientationColumnQueries fromUiName(String text) {
        for (InfoOrientationColumnQueries query : InfoOrientationColumnQueries.values()) {
            if(query.getUiName().equals(text)) {
                return query;
            }
        }
        return null;
    }

    public static InfoOrientationColumnQueries fromDbName(String text) {
        for (InfoOrientationColumnQueries query : InfoOrientationColumnQueries.values()) {
            if(query.getDbName().equals(text)) {
                return query;
            }
        }
        return null;
    }
}

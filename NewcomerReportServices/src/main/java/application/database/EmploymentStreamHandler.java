package application.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class EmploymentStreamHandler {
    /**
     * Written by http://www.sqlitetutorial.net/sqlite-java/select/
     * Connect to the test.db database
     * @return the Connection object
     */
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:sqlite/db/newcomerService.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Insert a new row of data representing a user in the Employment Service Stream.
     *
     * @param streamDetails hashmap mapping each column in the employment service stream template to its value
     * @return boolean returns true if update was successful
     */
    public static boolean insert(HashMap<String, String> streamDetails) {
        String sql = "INSERT INTO EmploymentServiceStream(processing_details, update_record_id, client_validation_type_id, client_validation_id, client_birth_dt, postal_cd, session_result_intn_ind, session_referral_id, session_service_lang_id, session_official_lang_id, institution_type_id, assessment_referral_id, session_referral_dt, session_employment_status_id, session_education_status_id, session_current_occupation_id, session_intended_occupation_id, intervention_type_id, intervention_received_id, intervention_status_id, intervention_leave_reason_id, intervention_start_dt, intervention_end_dt, employer_size_id, placement_type_id, working_hours_id, mentoring_location_id, mentoring_hours_id, intervention_profession_id, training_received_service_ind, training_received_computer_ind, training_received_document_ind, training_received_interpersonal_ind, training_received_leadership_ind, training_received_numeracy_ind, short_term_service_1_service_received_id, short_term_service_1_entry_dt, short_term_service_2_service_received_id, short_term_service_2_entry_dt, short_term_service_3_service_received_id, short_term_service_3_entry_dt, short_term_service_4_service_received_id, short_term_service_4_entry_dt, short_term_service_5_service_received_id, short_term_service_5_entry_dt, support_received_ind, childminding_ind, childminding_NewcomerChildren_1_childminding_age_id, childminding_NewcomerChildren_1_childminding_type_id, childminding_NewcomerChildren_2_childminding_age_id, childminding_NewcomerChildren_2_childminding_type_id, childminding_NewcomerChildren_3_childminding_age_id, childminding_NewcomerChildren_3_childminding_type_id, childminding_NewcomerChildren_4_childminding_age_id, childminding_NewcomerChildren_4_childminding_type_id, childminding_NewcomerChildren_5_childminding_age_id, childminding_NewcomerChildren_5_childminding_type_id, transportation_ind, support_disability_ind, translation_ind, translation_language_from_id, translation_language_to_id, interpretation_ind, interpretation_language_from_id, interpretation_language_to_id, counselling_ind, complete_hours_spent_no, complete_minutes_spent_no, assessment_update_reason_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ArrayList<String> columnNames = new ArrayList<>(Arrays.asList("processing_details", "update_record_id", "client_validation_type_id", "client_validation_id", "client_birth_dt", "postal_cd", "session_result_intn_ind", "session_referral_id", "session_service_lang_id", "session_official_lang_id", "institution_type_id", "assessment_referral_id", "session_referral_dt", "session_employment_status_id", "session_education_status_id", "session_current_occupation_id", "session_intended_occupation_id", "intervention_type_id", "intervention_received_id", "intervention_status_id", "intervention_leave_reason_id", "intervention_start_dt", "intervention_end_dt", "employer_size_id", "placement_type_id", "working_hours_id", "mentoring_location_id", "mentoring_hours_id", "intervention_profession_id", "training_received_service_ind", "training_received_computer_ind", "training_received_document_ind", "training_received_interpersonal_ind", "training_received_leadership_ind", "training_received_numeracy_ind", "short_term_service_1_service_received_id", "short_term_service_1_entry_dt", "short_term_service_2_service_received_id", "short_term_service_2_entry_dt", "short_term_service_3_service_received_id", "short_term_service_3_entry_dt", "short_term_service_4_service_received_id", "short_term_service_4_entry_dt", "short_term_service_5_service_received_id", "short_term_service_5_entry_dt", "support_received_ind", "childminding_ind", "childminding_NewcomerChildren_1_childminding_age_id", "childminding_NewcomerChildren_1_childminding_type_id", "childminding_NewcomerChildren_2_childminding_age_id", "childminding_NewcomerChildren_2_childminding_type_id", "childminding_NewcomerChildren_3_childminding_age_id", "childminding_NewcomerChildren_3_childminding_type_id", "childminding_NewcomerChildren_4_childminding_age_id", "childminding_NewcomerChildren_4_childminding_type_id", "childminding_NewcomerChildren_5_childminding_age_id", "childminding_NewcomerChildren_5_childminding_type_id", "transportation_ind", "support_disability_ind", "translation_ind", "translation_language_from_id", "translation_language_to_id", "interpretation_ind", "interpretation_language_from_id", "interpretation_language_to_id", "counselling_ind", "complete_hours_spent_no", "complete_minutes_spent_no", "assessment_update_reason_id"));
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, streamDetails.get("processing_details"));
            pstmt.setString(2, streamDetails.get("update_record_id"));
            pstmt.setString(3, streamDetails.get("client_validation_type_id"));
            pstmt.setString(4, streamDetails.get("client_validation_id"));
            pstmt.setString(5, streamDetails.get("client_birth_dt"));
            pstmt.setString(6, streamDetails.get("postal_cd"));
            pstmt.setString(7, streamDetails.get("session_result_intn_ind"));
            pstmt.setString(8, streamDetails.get("session_referral_id"));
            pstmt.setString(9, streamDetails.get("session_service_lang_id"));
            pstmt.setString(10, streamDetails.get("session_official_lang_id"));
            pstmt.setString(11, streamDetails.get("institution_type_id"));
            pstmt.setString(12, streamDetails.get("assessment_referral_id"));
            pstmt.setString(13, streamDetails.get("session_referral_dt"));
            pstmt.setString(14, streamDetails.get("session_employment_status_id"));
            pstmt.setString(15, streamDetails.get("session_education_status_id"));
            pstmt.setString(16, streamDetails.get("session_current_occupation_id"));
            pstmt.setString(17, streamDetails.get("session_intended_occupation_id"));
            pstmt.setString(18, streamDetails.get("intervention_type_id"));
            pstmt.setString(19, streamDetails.get("intervention_received_id"));
            pstmt.setString(20, streamDetails.get("intervention_status_id"));
            pstmt.setString(21, streamDetails.get("intervention_leave_reason_id"));
            pstmt.setString(22, streamDetails.get("intervention_start_dt"));
            pstmt.setString(23, streamDetails.get("intervention_end_dt"));
            pstmt.setString(24, streamDetails.get("employer_size_id"));
            pstmt.setString(25, streamDetails.get("placement_type_id"));
            pstmt.setString(26, streamDetails.get("working_hours_id"));
            pstmt.setString(27, streamDetails.get("mentoring_location_id"));
            pstmt.setString(28, streamDetails.get("mentoring_hours_id"));
            pstmt.setString(29, streamDetails.get("intervention_profession_id"));
            pstmt.setString(30, streamDetails.get("training_received_service_ind"));
            pstmt.setString(31, streamDetails.get("training_received_computer_ind"));
            pstmt.setString(32, streamDetails.get("training_received_document_ind"));
            pstmt.setString(33, streamDetails.get("training_received_interpersonal_ind"));
            pstmt.setString(34, streamDetails.get("training_received_leadership_ind"));
            pstmt.setString(35, streamDetails.get("training_received_numeracy_ind"));
            pstmt.setString(36, streamDetails.get("short_term_service_1_service_received_id"));
            pstmt.setString(37, streamDetails.get("short_term_service_1_entry_dt"));
            pstmt.setString(38, streamDetails.get("short_term_service_2_service_received_id"));
            pstmt.setString(39, streamDetails.get("short_term_service_2_entry_dt"));
            pstmt.setString(40, streamDetails.get("short_term_service_3_service_received_id"));
            pstmt.setString(41, streamDetails.get("short_term_service_3_entry_dt"));
            pstmt.setString(42, streamDetails.get("short_term_service_4_service_received_id"));
            pstmt.setString(43, streamDetails.get("short_term_service_4_entry_dt"));
            pstmt.setString(44, streamDetails.get("short_term_service_5_service_received_id"));
            pstmt.setString(45, streamDetails.get("short_term_service_5_entry_dt"));
            pstmt.setString(46, streamDetails.get("support_received_ind"));
            pstmt.setString(47, streamDetails.get("childminding_ind"));
            pstmt.setString(48, streamDetails.get("childminding_NewcomerChildren_1_childminding_age_id"));
            pstmt.setString(49, streamDetails.get("childminding_NewcomerChildren_1_childminding_type_id"));
            pstmt.setString(50, streamDetails.get("childminding_NewcomerChildren_2_childminding_age_id"));
            pstmt.setString(51, streamDetails.get("childminding_NewcomerChildren_2_childminding_type_id"));
            pstmt.setString(52, streamDetails.get("childminding_NewcomerChildren_3_childminding_age_id"));
            pstmt.setString(53, streamDetails.get("childminding_NewcomerChildren_3_childminding_type_id"));
            pstmt.setString(54, streamDetails.get("childminding_NewcomerChildren_4_childminding_age_id"));
            pstmt.setString(55, streamDetails.get("childminding_NewcomerChildren_4_childminding_type_id"));
            pstmt.setString(56, streamDetails.get("childminding_NewcomerChildren_5_childminding_age_id"));
            pstmt.setString(57, streamDetails.get("childminding_NewcomerChildren_5_childminding_type_id"));
            pstmt.setString(58, streamDetails.get("transportation_ind"));
            pstmt.setString(59, streamDetails.get("support_disability_ind"));
            pstmt.setString(60, streamDetails.get("translation_ind"));
            pstmt.setString(61, streamDetails.get("translation_language_from_id"));
            pstmt.setString(62, streamDetails.get("translation_language_to_id"));
            pstmt.setString(63, streamDetails.get("interpretation_ind"));
            pstmt.setString(64, streamDetails.get("interpretation_language_from_id"));
            pstmt.setString(65, streamDetails.get("interpretation_language_to_id"));
            pstmt.setString(66, streamDetails.get("counselling_ind"));
            pstmt.setString(67, streamDetails.get("complete_hours_spent_no"));
            pstmt.setString(68, streamDetails.get("complete_minutes_spent_no"));
            pstmt.setString(69, streamDetails.get("assessment_update_reason_id"));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}

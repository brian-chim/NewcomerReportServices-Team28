package application.util;

import java.util.ArrayList;

public enum QueryStringCols {
	EMAIL("email_txt"),
	STREETDIRECTION("street_direction_id"),
	PROVINCE("province_id"),
	CITY("city_txt"),
	STREETTYPE("street_type_id"),
	STREETNAME("street_nme");
	
	private String col;
	
	QueryStringCols(String col) {
        this.col = col;
    }
	
	public String getCol() {
        return this.col;
    }
	
	public static ArrayList<String> getStringCols() {
		ArrayList<String> cols = new ArrayList();
		for (QueryStringCols query : QueryStringCols.values()) {
            cols.add(query.getCol());
        }
		return cols;
	}
}

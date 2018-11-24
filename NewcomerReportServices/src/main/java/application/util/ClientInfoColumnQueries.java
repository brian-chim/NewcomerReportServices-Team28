package application.util;

import java.util.ArrayList;

public enum ClientInfoColumnQueries {
	STREETNUMBER("street_no"),
	STREETNAME("street_nme"),
	STREETTYPE("street_type_id"),
	STREETDIRECTION("street_direction_id"),
	UNIT("unit_txt"),
	CITY("city_txt"),
	PROVINCE("province_id");
	
	private String col;
	
	ClientInfoColumnQueries(String col) {
        this.col = col;
    }
	
	public String getCol() {
        return this.col;
    }
	
	public static ArrayList<String> getClientAddressCols() {
		ArrayList<String> cols = new ArrayList();
		for (ClientInfoColumnQueries query : ClientInfoColumnQueries.values()) {
            cols.add(query.getCol());
        }
		return cols;
	}
}

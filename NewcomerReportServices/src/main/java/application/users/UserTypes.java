package application.users;

public enum UserTypes {
	AGENCY("Agency"),
	STAFF("TEQLIP Staff"),
	ADMIN("Admin");
	
	private String uiName;
	
	private UserTypes(String uiName) {
		this.uiName = uiName;
	}
	
	public String getUiName() {
		return this.uiName;
	}
	
	public static UserTypes fromUiName(String uiName) {
		for (UserTypes userType : UserTypes.values()) {
			if(userType.getUiName().equals(uiName)) {
				return userType;
			}
		}
		return null;
	}
}

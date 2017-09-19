package settings;

public class PersistenceSettings {
	private static String type;
	
	public void setPersistenceType(String tmpType) {
		type = tmpType;
	}
	
	public String getPersistenceType() {
		return type;
	}
}

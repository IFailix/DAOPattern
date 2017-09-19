package settings;

public class PersistanceSettings {
	private static String type;
	
	public void setPersistanceType(String tmpType) {
		type = tmpType;
	}
	
	public String getPersistanceType() {
		return type;
	}
}

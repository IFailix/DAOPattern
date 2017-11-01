package dataLayer;

import dataLayer.dataAccessObjects.sqlite.DataLayerSqlite;
import dataLayer.dataAccessObjects.xml.DataLayerXml;
import settings.SettingsManager;
import settings.PersistenceSettings;

public class DataLayerManager {
	private static DataLayerManager instance=null;
	private static IDataLayer dataLayer=null;
	
	private DataLayerManager() {};
	
	public static DataLayerManager getInstance() {
		if(instance == null) {
			instance = new DataLayerManager();
		}
		return instance;
	}
	
	public IDataLayer getDataLayer() {
		if(dataLayer == null) {
			SettingsManager settingsManager = SettingsManager.getInstance();
			PersistenceSettings persistenceSettings = settingsManager.getPersistenceSettings();
			String type = persistenceSettings.getPersistenceType();
			
			if(type.equals("sqlite")) {
				dataLayer = new DataLayerSqlite();
			}
			else {
				// persistenceType can only be xml here.
				dataLayer = new DataLayerXml();
			}
		}
		return dataLayer;
	}
}

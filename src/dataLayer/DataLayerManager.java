package dataLayer;

import settings.SettingsManager;
import settings.PersistenceSettings;

import dataLayer.businessObjects.sqlite.DataLayerSqlite;
import dataLayer.businessObjects.xml.DataLayerXml;

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
			String persistenceType = persistenceSettings.getPersistenceType();
			
			if(persistenceType.equals("sqlite")) {
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

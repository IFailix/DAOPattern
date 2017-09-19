package settings;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class SettingsManager {
	private static SettingsManager instance=null;
	private static PersistenceSettings persistenceSettings=null;
	private String settingsFileName = "settings.xml";
	
	private SettingsManager() {}
	
	public static SettingsManager getInstance() {
		if(instance == null) {
			instance = new SettingsManager();
		}
		return instance;
	}
	
	public PersistenceSettings getPersistenceSettings() {
		if(persistenceSettings == null) {
			persistenceSettings = new PersistenceSettings();
			readPersistenceSettings();
		}
		return persistenceSettings;
	}
	
	private PersistenceSettings readPersistenceSettings() {
		try {
			File settingsFile = new File(settingsFileName);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(settingsFile);
			String persistenceType = document.getElementsByTagName("persistence").item(0).getTextContent();
		
			if(!persistenceType.equals("sqlite") && !persistenceType.equals("xml")) {
				System.out.println("Der Persistenz-Wert \"" + persistenceType + "\"  aus der Datei " + settingsFileName + " ist nicht gültig.");
				System.exit(1);
			}
			
			persistenceSettings.setPersistenceType(persistenceType);
		}
		catch (IOException e) {
			System.out.println("Settings konnten nicht geladen werden. Fehlt die " + settingsFileName + " Datei?");
			System.exit(1);
		}
		catch (ParserConfigurationException e) {
			System.out.println("Der xml Parser wurde falsch konfiguriert. Fehler: " + e);
			System.exit(1);
		}
		catch (SAXException e) {
			System.out.println("Fehler beim lesen der " + settingsFileName + " Datei: " + e);
			System.exit(1);
		}
		
		return persistenceSettings;
	}
}

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
	private static PersistanceSettings persistanceSettings=null;
	private String settingsFileName = "settings.xml";
	
	private SettingsManager() {}
	
	public static SettingsManager getInstance() {
		if(instance == null) {
			instance = new SettingsManager();
		}
		return instance;
	}
	
	public PersistanceSettings getPersistanceSettings() {
		if(persistanceSettings == null) {
			persistanceSettings = new PersistanceSettings();
			readPersistanceSettings();
		}
		return persistanceSettings;
	}
	
	private PersistanceSettings readPersistanceSettings() {
		try {
			File settingsFile = new File(settingsFileName);
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(settingsFile);
			String persistanceType = document.getElementsByTagName("persistance").item(0).getTextContent();
		
			if(!persistanceType.equals("sqlite") && !persistanceType.equals("xml")) {
				System.out.println("Der Persistenz-Wert \"" + persistanceType + "\"  aus der Datei " + settingsFileName + " ist nicht gültig.");
				System.exit(1);
			}
			
			persistanceSettings.setPersistanceType(persistanceType);
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
		
		return persistanceSettings;
	}
}

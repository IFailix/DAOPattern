package dataLayer.dataAccessObjects.xml;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.ITrainerDao;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DataLayerXml implements IDataLayer{


    @Override
    public ITrainerDao getTrainerDao() {
        try {
            return new TrainerDaoXml();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

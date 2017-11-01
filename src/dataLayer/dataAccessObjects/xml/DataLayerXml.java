package dataLayer.dataAccessObjects.xml;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.ITrainerDao;

public class DataLayerXml implements IDataLayer{


    @Override
    public ITrainerDao getITrainerDao() {
        return new TrainerDaoXml();
    }
}

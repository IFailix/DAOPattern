package dataLayer;

import java.io.IOException;

import dataLayer.dataAccessObjects.ITrainerDao;

public interface IDataLayer {

    public ITrainerDao getTrainerDao() throws IOException;

}

package dataLayer.dataAccessObjects.sqlite;

import java.io.IOException;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.ITrainerDao;

public class DataLayerSqlite implements IDataLayer{

	@Override
	public ITrainerDao getTrainerDao() throws IOException {
		return new TrainerDaoSqlite();
	}
}

package dataLayer.dataAccessObjects.sqlite;

import dataLayer.IDataLayer;
import dataLayer.dataAccessObjects.ITrainerDao;

public class DataLayerSqlite implements IDataLayer{

	@Override
	public ITrainerDao getITrainerDao() {
		return new TrainerDaoSqlite();
	}
}

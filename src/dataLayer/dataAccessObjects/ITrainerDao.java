package dataLayer.dataAccessObjects;

import businessObjects.ITrainer;
import dataLayer.businessObjects.Trainer;

import java.util.ArrayList;

public interface ITrainerDao {

    public ITrainer create();

    public void delete(Trainer ITrainer);

    public ITrainer first();

    public ITrainer last();

    public ITrainer next(Trainer ITrainer);

    public ITrainer previous(Trainer ITrainer);

    public void save(Trainer ITrainer);

    public ArrayList select();

    public ITrainer select(int id);

}

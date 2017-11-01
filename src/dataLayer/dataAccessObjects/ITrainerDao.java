package dataLayer.dataAccessObjects;

import businessObjects.ITrainer;
import dataLayer.businessObjects.Trainer;
import exceptions.NoNextTrainerFoundException;
import exceptions.NoPreviousTrainerFoundException;
import exceptions.NoTrainerFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ITrainerDao {

    public ITrainer create() throws IOException;

    public void delete(ITrainer trainer) throws NoTrainerFoundException;

    public ITrainer first() throws NoTrainerFoundException;

    public ITrainer last() throws NoTrainerFoundException;

    public ITrainer next(ITrainer trainer) throws NoNextTrainerFoundException;

    public ITrainer previous(ITrainer trainer) throws NoNextTrainerFoundException, NoPreviousTrainerFoundException;

    public void save(ITrainer trainer) throws IOException;

    public List select() throws NoTrainerFoundException;

    public ITrainer select(int id) throws NoTrainerFoundException;

}

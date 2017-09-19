package dataLayer.businessObjects;

import businessObjects.ITrainer;

public class Trainer implements ITrainer{

    private int id;
    private String name;
    private int alter;
    private int erfahrung;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getAlter() {
        return alter;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    @Override
    public int getErfahrung() {
        return erfahrung;
    }

    @Override
    public void setErfahrung(int erfahrung) {
        this.erfahrung = erfahrung;
    }
}

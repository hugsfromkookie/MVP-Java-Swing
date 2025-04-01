package Model;

import Model.Enum.Species;
import Model.Enum.Types;

public class Plant {
    private int plantID;
    private String name;
    private Types type;
    private Species specie;
    private boolean carnivorous;
    private String img;

    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public Species getSpecie() {
        return specie;
    }

    public void setSpecie(Species specie) {
        this.specie = specie;
    }

    public boolean isCarnivorous() {
        return carnivorous;
    }

    public void setCarnivorous(boolean carnivorous) {
        this.carnivorous = carnivorous;
    }

    @Override
    public String toString() {
        return plantID + " " + name;
    }
}

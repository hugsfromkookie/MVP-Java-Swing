package Model;

import Model.Enum.Zones;

public class Exemplary {
    private int exemplaryID;
    private Plant plant;
    private Zones zone;
    private String img;

    public int getExemplaryID() {
        return exemplaryID;
    }

    public void setExemplaryID(int exemplaryID) {
        this.exemplaryID = exemplaryID;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Zones getZone() {
        return zone;
    }

    public void setZone(Zones zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return exemplaryID + " " + plant.getName();
    }
}

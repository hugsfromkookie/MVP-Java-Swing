package Presenter;

import java.util.List;

public interface IExemplarGUI {
    // Get methods
    String getSelectedPlant();
    String getSelectedSpecie();

    //Set methods
    void setPlants(List<String> plants);
    void setZones(List<String> zones);

    // Utility
    void showError(String message);
    void showSuccess(String message);
}

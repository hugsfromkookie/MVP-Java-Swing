package Presenter;

import java.util.List;

public interface IPlantGUI {
    // Get methods
    String getPlantName();
    String getPlantSpecie();
    String getPlantType();
    boolean getCheckBox();

    //Set methods
    void setSpecies(List<String> species);
    void setTypes(List<String> types);

    // Utility
    void showError(String message);
    void showSuccess(String message);
}

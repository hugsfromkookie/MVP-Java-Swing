package Presenter;

import java.util.List;

public interface IStartGUI {
    // Get methods
    String getSelectedPlant();
    String getSelectedExemplary();
    String getSelectedType();
    String getSelectedSpecie();
    String getSelectedSearchSpecie();
    boolean getCheckBox();
    String getFileType();
    List<String[]> getTableDataAsText();

    // Set methods
    void setPlants(List<String> plants);
    void setExemplars(List<String> exemplars);
    void setSpecies(List<String> species);
    void setTypes(List<String> types);
    void setPlantsTable(List<Object[]> data);
    void setExemplarsTable(List<Object[]> exemplars);

    // Utility
    void showError(String message);
    void showSuccess(String message);
}

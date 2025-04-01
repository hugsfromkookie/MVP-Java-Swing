package Presenter;

import Model.Connection.DatabaseConnection;
import Model.Enum.Species;
import Model.Enum.Types;
import Model.Plant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlantPresenter {
    private DatabaseConnection connection;
    private IPlantGUI plantGUI;

    public PlantPresenter(IPlantGUI plantGUI, int plantID) {
        this.connection = new DatabaseConnection();
        this.plantGUI = plantGUI;

        loadSpecies();
        loadTypes();
    }

    public void addOrEdit(int plantID) {
        if (plantID != 0) {
            updatePlant(plantID);
        } else {
            addPlant();
        }
    }

    public void updatePlant(int plantID) {
        String name = plantGUI.getPlantName();
        String specie = plantGUI.getPlantSpecie();
        String type = plantGUI.getPlantType();
        boolean carnivorous = plantGUI.getCheckBox();

        if (name == null || name.isEmpty() || specie == null || type == null) {
            plantGUI.showError("All fields must be filled.");
            return;
        }

        String query = "UPDATE plant SET name = ?, type = ?, specie = ?, carnivorous = ? WHERE idplant = ?";

        try (var conn = connection.getConnection();
             var stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, specie);
            stmt.setBoolean(4, carnivorous);
            stmt.setInt(5, plantID);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                plantGUI.showSuccess("Plant updated successfully.");
            } else {
                plantGUI.showError("No plant found with the given ID.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            plantGUI.showError("Error while updating plant.");
        }
    }

    public void addPlant() {
        String name = plantGUI.getPlantName();
        String specie = plantGUI.getPlantSpecie();
        String type = plantGUI.getPlantType();
        boolean carnivorous = plantGUI.getCheckBox();

        if (name == null || name.isEmpty() || specie == null || type == null) {
            plantGUI.showError("All fields must be filled.");
            return;
        }

        String query = "INSERT INTO plant (name, type, specie, carnivorous) VALUES (?, ?, ?, ?)";

        try (var conn = connection.getConnection();
             var stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, specie);
            stmt.setBoolean(4, carnivorous);

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                plantGUI.showSuccess("Plant added successfully.");
            } else {
                plantGUI.showError("Failed to add plant.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            plantGUI.showError("Error while adding plant.");
        }
    }

    public void loadSpecies() {
        List<String> speciesList = Arrays.stream(Species.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        plantGUI.setSpecies(speciesList);
    }

    public void loadTypes() {
        List<String> typesList = Arrays.stream(Types.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        plantGUI.setTypes(typesList);
    }

}

package Presenter;

import Model.Connection.DatabaseConnection;
import Model.Enum.Zones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExemplaryPresenter {
    private DatabaseConnection databaseConnection;
    private IExemplarGUI exemplarGUI;

    public ExemplaryPresenter(IExemplarGUI exemplarGUI) {
        this.databaseConnection = new DatabaseConnection();
        this.exemplarGUI = exemplarGUI;

        loadPlants();
        loadZones();
    }

    public void addOrEdit(int exemplarID) {
        if (exemplarID != 0) {
            updateExemplary(exemplarID);
        } else {
            addExemplary();
        }
    }

    public void addExemplary() {
        String plantString = exemplarGUI.getSelectedPlant();
        String zone = exemplarGUI.getSelectedSpecie();

        if (plantString == null || plantString.isEmpty() || zone == null || zone.isEmpty()) {
            exemplarGUI.showError("Please select both plant and zone.");
            return;
        }

        try {
            int plantID = Integer.parseInt(plantString.split(" ")[0]);

            String query = "INSERT INTO exemplary (idplant, zone) VALUES (?, ?)";

            try (Connection connection = databaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setInt(1, plantID);
                stmt.setString(2, zone);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    exemplarGUI.showSuccess("Exemplary added successfully.");
                } else {
                    exemplarGUI.showError("Failed to add exemplary.");
                }
            }
        } catch (NumberFormatException e) {
            exemplarGUI.showError("Invalid plant format. Could not extract plant ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            exemplarGUI.showError("Error adding exemplary.");
        }
    }

    public void updateExemplary(int exemplaryID) {
        String plantString = exemplarGUI.getSelectedPlant();
        String zone = exemplarGUI.getSelectedSpecie();

        if (plantString == null || plantString.isEmpty() || zone == null || zone.isEmpty()) {
            exemplarGUI.showError("Please select both plant and zone.");
            return;
        }

        try {
            int plantID = Integer.parseInt(plantString.split(" ")[0]);

            String query = "UPDATE exemplary SET idplant = ?, zone = ? WHERE idexemplary = ?";

            try (Connection connection = databaseConnection.getConnection();
                 PreparedStatement stmt = connection.prepareStatement(query)) {

                stmt.setInt(1, plantID);
                stmt.setString(2, zone);
                stmt.setInt(3, exemplaryID);

                int rowsUpdated = stmt.executeUpdate();

                if (rowsUpdated > 0) {
                    exemplarGUI.showSuccess("Exemplary updated successfully.");
                } else {
                    exemplarGUI.showError("No exemplary found with the provided ID.");
                }
            }
        } catch (NumberFormatException e) {
            exemplarGUI.showError("Invalid plant format. Could not extract plant ID.");
        } catch (SQLException e) {
            e.printStackTrace();
            exemplarGUI.showError("Error updating exemplary.");
        }
    }

    public void loadPlants() {
        List<String> plants = new ArrayList<>();
        String query = "SELECT idplant, name FROM plant";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int plantID = rs.getInt("idplant");
                String name = rs.getString("name");
                plants.add(plantID + " " + name);
            }
            exemplarGUI.setPlants(plants);
        } catch (SQLException e) {
            e.printStackTrace();
            exemplarGUI.showError("Error loading plants.");
        }
    }

    public void loadZones() {
        List<String> zonesList = Arrays.stream(Zones.values())
                .map(Enum::name)
                .toList();
        exemplarGUI.setZones(zonesList);
    }

}

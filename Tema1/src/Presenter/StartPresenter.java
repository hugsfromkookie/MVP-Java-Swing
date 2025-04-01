package Presenter;

import Model.Connection.DatabaseConnection;
import Model.Enum.Species;
import Model.Enum.Types;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StartPresenter {

    private DatabaseConnection databaseConnection;
    private IStartGUI startGUI;

    public StartPresenter(IStartGUI startGUI) {
        this.databaseConnection = new DatabaseConnection();
        this.startGUI = startGUI;
        loadData();
    }

    public void loadData() {
        loadPlants();
        loadExemplars();
        loadSpecies();
        loadTypes();
    }

    public void saveTableToFile() {
        List<String[]> data = startGUI.getTableDataAsText();
        String fileType = startGUI.getFileType();
        String fileName = "table_export." + fileType;
        String userDir = System.getProperty("user.dir");
        File file = new File(userDir, fileName);

        try (PrintWriter writer = new PrintWriter(file)) {
            if (fileType.equalsIgnoreCase("csv")) {
                for (String[] row : data) {
                    writer.println(String.join(",", row));
                }
            } else if (fileType.equalsIgnoreCase("doc")) {
                for (String[] row : data) {
                    writer.println(String.join(" | ", row));
                }
            } else {
                startGUI.showError("Unsupported file type selected.");
                return;
            }

            writer.flush();
            startGUI.showSuccess("Table saved to: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            startGUI.showError("Error saving file.");
        }
    }


    public void filterPlants() {
        List<Object[]> plantsData = new ArrayList<>();

        String selectedType = startGUI.getSelectedType();
        String selectedSpecie = startGUI.getSelectedSpecie();
        boolean isCarnivorous = startGUI.getCheckBox();

        StringBuilder queryBuilder = new StringBuilder("SELECT idplant, name, type, specie, carnivorous, img FROM plant WHERE 1=1");
        List<Object> parameters = new ArrayList<>();

        if (selectedType != null && !selectedType.equalsIgnoreCase("None") && !selectedType.isEmpty()) {
            queryBuilder.append(" AND type = ?");
            parameters.add(selectedType);
        }

        if (selectedSpecie != null && !selectedSpecie.equalsIgnoreCase("None") && !selectedSpecie.isEmpty()) {
            queryBuilder.append(" AND specie = ?");
            parameters.add(selectedSpecie);
        }

        queryBuilder.append(" AND carnivorous = ?");
        parameters.add(isCarnivorous);

        queryBuilder.append(" ORDER BY type, specie");

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("idplant");
                    String name = rs.getString("name");
                    String type = rs.getString("type");
                    String specie = rs.getString("specie");
                    boolean carnivorous = rs.getBoolean("carnivorous");
                    String imgUrl = rs.getString("img");

                    ImageIcon imageIcon = null;
                    try {
                        if (imgUrl != null && !imgUrl.isEmpty()) {
                            Image image = new ImageIcon(new URL(imgUrl)).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                            imageIcon = new ImageIcon(image);
                        } else {
                            imageIcon = new ImageIcon();
                        }
                    } catch (Exception e) {
                        System.out.println("Could not load image: " + imgUrl);
                        imageIcon = new ImageIcon(); // fallback
                    }

                    plantsData.add(new Object[]{
                            id,
                            name,
                            type,
                            specie,
                            carnivorous ? "Yes" : "No",
                            imageIcon,
                            imgUrl
                    });
                }

                startGUI.setPlantsTable(plantsData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            startGUI.showError("Error filtering plants.");
        }
    }

    public void viewPlants() {
        List<Object[]> plantsData = new ArrayList<>();
        String query = "SELECT idplant, name, type, specie, carnivorous, img FROM plant ORDER BY type, specie";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idplant");
                String name = rs.getString("name");
                String type = rs.getString("type");
                String specie = rs.getString("specie");
                boolean carnivorous = rs.getBoolean("carnivorous");
                String imgUrl = rs.getString("img");

                ImageIcon imageIcon = null;
                try {
                    if (imgUrl != null && !imgUrl.isEmpty()) {
                        Image image = new ImageIcon(new URL(imgUrl)).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(image);
                    } else {
                        imageIcon = new ImageIcon();
                    }
                } catch (Exception e) {
                    System.out.println("Could not load image from: " + imgUrl);
                    imageIcon = new ImageIcon();
                }

                plantsData.add(new Object[]{
                        id,
                        name,
                        type,
                        specie,
                        carnivorous ? "Yes" : "No",
                        imageIcon,
                        imgUrl
                });
            }

            startGUI.setPlantsTable(plantsData);
        } catch (SQLException e) {
            e.printStackTrace();
            startGUI.showError("Error loading plants.");
        }
    }


    public void viewExemplars(boolean isSearch) {
        List<Object[]> exemplarsData = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder(
                "SELECT e.idexemplary, p.name, e.zone FROM exemplary e JOIN plant p ON e.idplant = p.idplant"
        );
        List<Object> parameters = new ArrayList<>();

        if (isSearch) {
            String selectedSpecie = startGUI.getSelectedSearchSpecie();
            if (selectedSpecie != null && !selectedSpecie.equalsIgnoreCase("None") && !selectedSpecie.isEmpty()) {
                queryBuilder.append(" WHERE p.specie = ?");
                parameters.add(selectedSpecie);
            }
        }

        queryBuilder.append(" ORDER BY p.name");

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int exemplaryID = rs.getInt("idexemplary");
                    String plantName = rs.getString("name");
                    String zone = rs.getString("zone");

                    exemplarsData.add(new String[]{
                            String.valueOf(exemplaryID),
                            plantName,
                            zone
                    });
                }

                startGUI.setExemplarsTable(exemplarsData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            startGUI.showError("Error loading exemplars.");
        }
    }

    public void deleteExemplary() {
        String exemplary = startGUI.getSelectedExemplary();
        if (exemplary == null || exemplary.isEmpty()) {
            startGUI.showError("Please select a valid exemplary.");
            return;
        }

        try {
            int exemplaryID = Integer.parseInt(exemplary.split(" ")[0]);
            Connection connection = databaseConnection.getConnection();
            String query = "DELETE FROM exemplary WHERE idexemplary = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, exemplaryID);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    startGUI.showSuccess("Exemplary deleted successfully.");
                    loadExemplars();
                } else {
                    startGUI.showError("No exemplary found with the given ID.");
                }
            }
        } catch (NumberFormatException e) {
            startGUI.showError("Invalid exemplary format.");
        } catch (SQLException e) {
            e.printStackTrace();
            startGUI.showError("Error deleting exemplary.");
        }
    }

    public void deletePlant() {
        String plant = startGUI.getSelectedPlant();
        if (plant == null || plant.isEmpty()) {
            startGUI.showError("Please select a valid plant.");
            return;
        }

        try {
            int plantID = Integer.parseInt(plant.split(" ")[0]);
            Connection connection = databaseConnection.getConnection();
            String query = "DELETE FROM plant WHERE idplant = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, plantID);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    startGUI.showSuccess("Plant deleted successfully.");
                    loadPlants();
                    loadExemplars();
                } else {
                    startGUI.showError("No plant found with the given ID.");
                }
            }
        } catch (NumberFormatException e) {
            startGUI.showError("Invalid plant format.");
        } catch (SQLException e) {
            e.printStackTrace();
            startGUI.showError("Error deleting plant.");
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
            startGUI.setPlants(plants);
        } catch (SQLException e) {
            e.printStackTrace();
            startGUI.showError("Error loading plants.");
        }
    }

    public void loadExemplars() {
        List<String> exemplaries = new ArrayList<>();
        String query = "SELECT e.idexemplary, p.name FROM exemplary e " +
                "JOIN plant p ON e.idplant = p.idplant";

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int exemplaryID = rs.getInt("idexemplary");
                String plantName = rs.getString("name");
                exemplaries.add(exemplaryID + " " + plantName);
            }

            startGUI.setExemplars(exemplaries);
        } catch (SQLException e) {
            e.printStackTrace();
            startGUI.showError("Error loading exemplaries.");
        }
    }

    public void loadSpecies() {
        List<String> speciesList = Arrays.stream(Species.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        startGUI.setSpecies(speciesList);
    }

    public void loadTypes() {
        List<String> typesList = Arrays.stream(Types.values())
                .map(Enum::name)
                .collect(Collectors.toList());
        startGUI.setTypes(typesList);
    }

}

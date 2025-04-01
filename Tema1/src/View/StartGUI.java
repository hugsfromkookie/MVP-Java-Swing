package View;

import Presenter.IStartGUI;
import Presenter.StartPresenter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class StartGUI extends JDialog implements IStartGUI {
    private JPanel startJPanel;
    private JButton editPlantButton;
    private JButton addExemplaryButton;
    private JComboBox<String> plantComboBox;
    private JComboBox<String> exemplaryComboBox;
    private JButton addPlantButton;
    private JButton deletePlantButton;
    private JButton deleteExemplaryButton;
    private JButton viewExemplarsButton;
    private JButton viewPlantsButton;
    private JCheckBox carnivorousCheckBox;
    private JButton searchExemplarsButton;
    private JComboBox<String> specieSearchComboBox;
    private JComboBox<String> typeComboBox;
    private JComboBox<String> specieComboBox;
    private JButton editExemplaryButton;
    private JTable table1;
    private JTextField infoTextField1;
    private JComboBox infoComboBox1;
    private JComboBox infoComboBox2;
    private JButton saveTableAsButton;
    private JComboBox saveComboBox;
    private JCheckBox infoCarnivorous;
    private JButton filterPlantsButton;
    private StartPresenter presenter;
    public StartGUI(JFrame parent) {
        super(parent);
        setTitle("Start");
        setContentPane(startJPanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);

        presenter = new StartPresenter(this);

        populateTypeOfFile();

        addPlantButton.addActionListener(e-> openAddPlantGUI());
        addExemplaryButton.addActionListener(e-> openAddExemplaryGUI());

        editPlantButton.addActionListener(e -> openEditPlantGUI());
        editExemplaryButton.addActionListener(e -> openEditExemplaryGUI());

        deletePlantButton.addActionListener(e -> presenter.deletePlant());
        deleteExemplaryButton.addActionListener(e -> presenter.deleteExemplary());

        viewExemplarsButton.addActionListener(e -> presenter.viewExemplars(false));
        viewPlantsButton.addActionListener(e -> presenter.viewPlants());

        filterPlantsButton.addActionListener(e -> presenter.filterPlants());

        searchExemplarsButton.addActionListener(e -> presenter.viewExemplars(true));

        saveTableAsButton.addActionListener(e -> presenter.saveTableToFile());

        setVisible(true);
    }

    private void populateTypeOfFile() {
        saveComboBox.removeAllItems();
        saveComboBox.addItem("csv");
        saveComboBox.addItem("doc");

        saveTableAsButton.setVisible(false);
        saveComboBox.setVisible(false);
    }
    @Override
    public String getSelectedPlant() {
        return (String) plantComboBox.getSelectedItem();
    }

    @Override
    public String getSelectedExemplary() {
        return (String) exemplaryComboBox.getSelectedItem();
    }

    @Override
    public String getSelectedType() {
        return (String) typeComboBox.getSelectedItem();
    }

    @Override
    public String getSelectedSpecie() {
        return (String) specieComboBox.getSelectedItem();
    }

    @Override
    public String getSelectedSearchSpecie() {
        return (String) specieSearchComboBox.getSelectedItem();
    }

    @Override
    public boolean getCheckBox() {
        return carnivorousCheckBox.isSelected();
    }
    @Override
    public String getFileType() {
        return (String) saveComboBox.getSelectedItem();
    }

    public List<String[]> getTableDataAsText() {
        List<String[]> data = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) table1.getModel();

        for (int row = 0; row < model.getRowCount(); row++) {
            String[] rowData = new String[model.getColumnCount()];
            for (int col = 0; col < model.getColumnCount(); col++) {
                Object value = model.getValueAt(row, col);
                rowData[col] = (value instanceof Icon) ? "[Image]" : String.valueOf(value);
            }
            data.add(rowData);
        }

        return data;
    }

    @Override
    public void setPlants(List<String> plants) {
        plantComboBox.removeAllItems();
        for (String plant : plants) {
            plantComboBox.addItem(plant);
        }
    }

    @Override
    public void setExemplars(List<String> exemplars) {
        exemplaryComboBox.removeAllItems();
        for (String exemplary : exemplars) {
            exemplaryComboBox.addItem(exemplary);
        }
    }

    @Override
    public void setSpecies(List<String> species) {
        specieComboBox.removeAllItems();
        specieSearchComboBox.removeAllItems();
        for (String specie : species) {
            specieComboBox.addItem(specie);
            specieSearchComboBox.addItem(specie);
        }
        specieComboBox.addItem("None");
    }

    @Override
    public void setTypes(List<String> types) {
        typeComboBox.removeAllItems();
        for (String type : types) {
            typeComboBox.addItem(type);
        }
        typeComboBox.addItem("None");
    }

    @Override
    public void setPlantsTable(List<Object[]> plants) {
        String[] columnNames = {"ID", "Name", "Type", "Specie", "Carnivorous", "Image", "Image URL"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 5) return Icon.class;
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] rowData : plants) {
            tableModel.addRow(rowData);
        }

        table1.setModel(tableModel);
        table1.setRowHeight(60);

        saveTableAsButton.setVisible(true);
        saveComboBox.setVisible(true);
    }


    @Override
    public void setExemplarsTable(List<Object[]> exemplars) {
        String[] columnNames = {"ID Exemplary", "Plant Name", "Zone"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Object[] rowData : exemplars) {
            tableModel.addRow(rowData);
        }

        table1.setModel(tableModel);

        saveTableAsButton.setVisible(false);
        saveComboBox.setVisible(false);
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void openAddPlantGUI() {
        PlantGUI plantGUI = new PlantGUI(null, 0);
        plantGUI.setVisible(true);

        presenter.loadData();
    }
    public void openEditPlantGUI() {
        String selected = getSelectedPlant();
        int plantID = Integer.parseInt(selected.split(" ")[0]);
        PlantGUI plantGUI = new PlantGUI(null, plantID);
        plantGUI.setVisible(true);

        presenter.loadData();
    }

    public void openAddExemplaryGUI() {
        ExemplaryGUI exemplaryGUI = new ExemplaryGUI(null, 0);
        exemplaryGUI.setVisible(true);

        presenter.loadData();
    }
    public void openEditExemplaryGUI() {
        String selected = getSelectedExemplary();
        int exemplaryID = Integer.parseInt(selected.split(" ")[0]);
        ExemplaryGUI exemplaryGUI = new ExemplaryGUI(null, exemplaryID);
        exemplaryGUI.setVisible(true);

        presenter.loadData();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartGUI(null));
    }
}

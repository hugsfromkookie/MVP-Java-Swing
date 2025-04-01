package View;

import Presenter.IPlantGUI;
import Presenter.PlantPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class PlantGUI extends JDialog implements IPlantGUI {
    private JTextField nameTextField;
    private JComboBox typeComboBox;
    private JComboBox specieComboBox;
    private JCheckBox checkBox1;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel plantJPanel;
    private JTextField imageTextField;
    private PlantPresenter presenter;

    public PlantGUI(JFrame parent, int plantID) {
        super(parent);
        setTitle("Plant");
        setContentPane(plantJPanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);

        presenter = new PlantPresenter(this, plantID);

        saveButton.addActionListener(e -> {
            presenter.addOrEdit(plantID);
            dispose();
        });

        cancelButton.addActionListener(e-> dispose());

        setVisible(true);
    }

    @Override
    public String getPlantName() {
        return nameTextField.getText();
    }

    @Override
    public String getPlantSpecie() {
        return Objects.requireNonNull(specieComboBox.getSelectedItem()).toString();
    }

    @Override
    public String getPlantType() {
        return Objects.requireNonNull(typeComboBox.getSelectedItem()).toString();
    }

    @Override
    public boolean getCheckBox() {
        return checkBox1.isSelected();
    }

    @Override
    public void setSpecies(List<String> species) {
        specieComboBox.removeAllItems();
        for (String specie : species) {
            specieComboBox.addItem(specie);
        }
    }

    @Override
    public void setTypes(List<String> types) {
        typeComboBox.removeAllItems();
        for (String type : types) {
            typeComboBox.addItem(type);
        }
    }

    @Override
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PlantGUI(null, 16));
    }
}

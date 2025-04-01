package View;

import Presenter.ExemplaryPresenter;
import Presenter.IExemplarGUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExemplaryGUI extends JDialog implements IExemplarGUI {
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel exemplaryJPanel;
    private ExemplaryPresenter presenter;

    public ExemplaryGUI(JFrame parent, int exemplarID) {
        super(parent);
        setTitle("Exemplar");
        setContentPane(exemplaryJPanel);
        setMinimumSize(new Dimension(1280, 720));
        setModal(true);
        setLocationRelativeTo(parent);

        presenter = new ExemplaryPresenter(this);

        saveButton.addActionListener(e -> {
            presenter.addOrEdit(exemplarID);
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    @Override
    public String getSelectedPlant() {
        return (String) comboBox1.getSelectedItem();
    }

    @Override
    public String getSelectedSpecie() {
        return (String) comboBox2.getSelectedItem();
    }

    @Override
    public void setPlants(List<String> plants) {
        comboBox1.removeAllItems();
        for (String plant : plants) {
            comboBox1.addItem(plant);
        }
    }

    @Override
    public void setZones(List<String> zones) {
        comboBox2.removeAllItems();
        for (String zone : zones) {
            comboBox2.addItem(zone);
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
        SwingUtilities.invokeLater(() -> new ExemplaryGUI(null, 0));
    }
}

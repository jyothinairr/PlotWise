package admin;

import dao.PlotDAO;
import models.Plot;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UpdatePlotFrame extends JFrame {
    private final JTextField titleField, addressField, acresField, priceField, statusField;
    private final int plotId;
    private final AdminDashboard dashboard;
    private final PlotDAO dao;

    public UpdatePlotFrame(AdminDashboard dashboard, int plotId) {
        this.dashboard = dashboard;
        this.plotId = plotId;
        this.dao = new PlotDAO();

        setTitle("Update Plot");
        setSize(420, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(235, 238, 243)); // same soft gray
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panel);

        
        JLabel titleLabel = new JLabel("UPDATE PLOT DETAILS", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(0, 15, 400, 30);
        panel.add(titleLabel);

        
        Plot plot = dao.getPlotById(plotId);

        int labelX = 60, fieldX = 180, startY = 70, gapY = 40;

        JLabel lblTitle = createLabel("TITLE:", labelX, startY);
        titleField = createTextField(plot.getTitle(), fieldX, startY);
        panel.add(lblTitle); panel.add(titleField);

        JLabel lblAddress = createLabel("ADDRESS:", labelX, startY + gapY);
        addressField = createTextField(plot.getAddress(), fieldX, startY + gapY);
        panel.add(lblAddress); panel.add(addressField);

        JLabel lblAcres = createLabel("ACRES:", labelX, startY + gapY * 2);
        acresField = createTextField(String.valueOf(plot.getAcres()), fieldX, startY + gapY * 2);
        panel.add(lblAcres); panel.add(acresField);

        JLabel lblPrice = createLabel("PRICE:", labelX, startY + gapY * 3);
        priceField = createTextField(String.valueOf(plot.getPrice()), fieldX, startY + gapY * 3);
        panel.add(lblPrice); panel.add(priceField);

        JLabel lblStatus = createLabel("STATUS:", labelX, startY + gapY * 4);
        statusField = createTextField(plot.getStatus(), fieldX, startY + gapY * 4);
        panel.add(lblStatus); panel.add(statusField);

       
        JButton saveBtn = new JButton("UPDATE PLOT");
        saveBtn.setFont(new Font("Arial", Font.BOLD, 13));
        saveBtn.setBounds(140, startY + gapY * 5 + 10, 140, 35);
        saveBtn.setFocusPainted(false);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));
        panel.add(saveBtn);

        
        saveBtn.addActionListener(e -> updatePlot());

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setBounds(x, y, 110, 25);
        return lbl;
    }

    private JTextField createTextField(String value, int x, int y) {
        JTextField field = new JTextField(value);
        field.setBounds(x, y, 160, 25);
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        return field;
    }

    private void updatePlot() {
        try {
            Plot p = new Plot();
            p.setId(plotId);
            p.setTitle(titleField.getText().trim());
            p.setAddress(addressField.getText().trim());
            p.setAcres(Double.parseDouble(acresField.getText().trim()));
            p.setPrice(Double.parseDouble(priceField.getText().trim()));
            p.setStatus(statusField.getText().trim());

            if (dao.updatePlot(p)) {
                JOptionPane.showMessageDialog(this, "✅ Plot updated successfully!");
                dashboard.loadPlots("ALL");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error updating plot.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Please check your input values.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
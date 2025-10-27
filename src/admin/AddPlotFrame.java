package admin;

import dao.PlotDAO;
import models.Plot;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class AddPlotFrame extends JFrame {
    private final JTextField titleField, addressField, acresField, priceField, ownerNameField, ownerPhoneField, ownerEmailField;
    private final PlotDAO dao;
    private final AdminDashboard dashboard;

    public AddPlotFrame(AdminDashboard dashboard) {
        this.dashboard = dashboard;
        this.dao = new PlotDAO();

        setTitle("Add New Plot");
        setSize(420, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(235, 238, 243)); // same as login
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        add(panel);

        
        JLabel titleLabel = new JLabel("ADD NEW PLOT", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(0, 10, 400, 30);
        panel.add(titleLabel);

        
        int labelX = 60, fieldX = 180, startY = 70, gapY = 40;

        JLabel lblTitle = createLabel("TITLE:", labelX, startY);
        titleField = createTextField(fieldX, startY);
        panel.add(lblTitle); panel.add(titleField);

        JLabel lblAddress = createLabel("ADDRESS:", labelX, startY + gapY);
        addressField = createTextField(fieldX, startY + gapY);
        panel.add(lblAddress); panel.add(addressField);

        JLabel lblAcres = createLabel("ACRES:", labelX, startY + gapY * 2);
        acresField = createTextField(fieldX, startY + gapY * 2);
        panel.add(lblAcres); panel.add(acresField);

        JLabel lblPrice = createLabel("PRICE:", labelX, startY + gapY * 3);
        priceField = createTextField(fieldX, startY + gapY * 3);
        panel.add(lblPrice); panel.add(priceField);

        JLabel lblOwnerName = createLabel("OWNER NAME:", labelX, startY + gapY * 4);
        ownerNameField = createTextField(fieldX, startY + gapY * 4);
        panel.add(lblOwnerName); panel.add(ownerNameField);

        JLabel lblOwnerPhone = createLabel("OWNER PHONE:", labelX, startY + gapY * 5);
        ownerPhoneField = createTextField(fieldX, startY + gapY * 5);
        panel.add(lblOwnerPhone); panel.add(ownerPhoneField);

        JLabel lblOwnerEmail = createLabel("OWNER EMAIL:", labelX, startY + gapY * 6);
        ownerEmailField = createTextField(fieldX, startY + gapY * 6);
        panel.add(lblOwnerEmail); panel.add(ownerEmailField);

       
        JButton saveBtn = new JButton("SAVE PLOT");
        saveBtn.setFont(new Font("Arial", Font.BOLD, 13));
        saveBtn.setBounds(140, startY + gapY * 7 + 10, 140, 35);
        saveBtn.setFocusPainted(false);
        saveBtn.setBackground(Color.WHITE);
        saveBtn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));
        panel.add(saveBtn);

       
        saveBtn.addActionListener(e -> savePlot());

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        lbl.setBounds(x, y, 110, 25);
        return lbl;
    }

    private JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 160, 25);
        field.setFont(new Font("Arial", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true));
        return field;
    }

    private void savePlot() {
        try {
            Plot p = new Plot();
            p.setTitle(titleField.getText().trim());
            p.setAddress(addressField.getText().trim());
            p.setAcres(Double.parseDouble(acresField.getText().trim()));
            p.setPrice(Double.parseDouble(priceField.getText().trim()));
            p.setOwnerName(ownerNameField.getText().trim());
            p.setOwnerPhone(ownerPhoneField.getText().trim());
            p.setOwnerEmail(ownerEmailField.getText().trim());
            p.setStatus("AVAILABLE");

            if (dao.addPlot(p)) {
                JOptionPane.showMessageDialog(this, "✅ Plot added successfully!");
                dashboard.loadPlots("ALL");  // or dashboard.refreshPlots();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Error adding plot.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Please fill all fields correctly.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
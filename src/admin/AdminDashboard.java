package admin;

import dao.PlotDAO;
import models.Plot;
import models.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTable table;
    private PlotDAO dao;

    public AdminDashboard(User admin) {
        dao = new PlotDAO();
        setTitle("Admin Dashboard - " + admin.getFullName());
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 238, 243));
        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        topPanel.setBackground(new Color(245, 247, 250));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Admin Dashboard");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title);

        JButton addBtn = createButton("Add Plot");
        JButton editBtn = createButton("Edit Selected");
        JButton deleteBtn = createButton("Delete Selected");
        JButton refreshBtn = createButton("Refresh");
        JButton soldBtn = createButton("View Sold");
        JButton availableBtn = createButton("View Available");
        JButton logoutBtn = createButton("Logout");

        topPanel.add(addBtn);
        topPanel.add(editBtn);
        topPanel.add(deleteBtn);
        topPanel.add(refreshBtn);
        topPanel.add(soldBtn);
        topPanel.add(availableBtn);
        topPanel.add(logoutBtn);

        add(topPanel, BorderLayout.NORTH);

        
        table = new JTable();
        table.setRowHeight(28);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setGridColor(new Color(220, 220, 220));
        table.setShowHorizontalLines(true);
        table.setBackground(Color.WHITE);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 13));
        header.setBackground(new Color(245, 247, 250));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        
        addBtn.addActionListener(e -> new AddPlotFrame(this).setVisible(true));
        editBtn.addActionListener(e -> editPlot());
        deleteBtn.addActionListener(e -> deletePlot());
        refreshBtn.addActionListener(e -> loadPlots("ALL"));
        soldBtn.addActionListener(e -> loadPlots("SOLD"));
        availableBtn.addActionListener(e -> loadPlots("AVAILABLE"));
        logoutBtn.addActionListener(e -> logout());

        loadPlots("ALL");
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 12));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));
        return btn;
    }

    public void loadPlots(String filter) {
        List<Plot> list = switch (filter) {
            case "SOLD" -> dao.getPlotsByStatus("SOLD");
            case "AVAILABLE" -> dao.getPlotsByStatus("AVAILABLE");
            default -> dao.getAllPlots();
        };

        String[] cols = {"ID", "Title", "Address", "Acres", "Price", "Status"};
        Object[][] data = new Object[list.size()][cols.length];
        for (int i = 0; i < list.size(); i++) {
            Plot p = list.get(i);
            data[i][0] = i + 1;
            data[i][1] = p.getTitle();
            data[i][2] = p.getAddress();
            data[i][3] = p.getAcres();
            data[i][4] = p.getPrice();
            data[i][5] = p.getStatus();
        }
        table.setModel(new javax.swing.table.DefaultTableModel(data, cols));
    }

    private void editPlot() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a plot to edit.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        new UpdatePlotFrame(this, id).setVisible(true);
    }

    private void deletePlot() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a plot to delete.");
            return;
        }
        int id = (int) table.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete plot ID " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.deletePlot(id)) {
                JOptionPane.showMessageDialog(this, "Plot deleted.");
                loadPlots("ALL");
            }
        }
    }

    private void logout() {
        dispose();
        new user.LoginFrame().setVisible(true);
    }
}
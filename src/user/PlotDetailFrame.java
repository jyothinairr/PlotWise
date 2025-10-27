package user;

import dao.PlotDAO;
import dao.PurchaseDAO;
import models.Plot;
import models.Purchase;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PlotDetailFrame extends JFrame {
    private final Plot plot;
    private final User user;

    public PlotDetailFrame(Plot plot, User user) {
        this.plot = plot;
        this.user = user;

        setTitle("Plot Details - " + plot.getTitle());
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(235, 238, 243));
        setLayout(new BorderLayout());

        // ===== Title =====
        JLabel heading = new JLabel("PLOT DETAILS", SwingConstants.CENTER);
        heading.setFont(new Font("Arial", Font.BOLD, 18));
        heading.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(heading, BorderLayout.NORTH);

        // ===== Details =====
        JPanel detailPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        detailPanel.setBackground(new Color(235, 238, 243));
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        detailPanel.add(new JLabel("TITLE:")); detailPanel.add(new JLabel(plot.getTitle()));
        detailPanel.add(new JLabel("ADDRESS:")); detailPanel.add(new JLabel(plot.getAddress()));
        detailPanel.add(new JLabel("ACRES:")); detailPanel.add(new JLabel(String.valueOf(plot.getAcres())));
        detailPanel.add(new JLabel("PRICE:")); detailPanel.add(new JLabel("₹" + plot.getPrice()));
        detailPanel.add(new JLabel("STATUS:")); detailPanel.add(new JLabel(plot.getStatus()));

        add(detailPanel, BorderLayout.CENTER);

        // ===== Buttons =====
        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        btnPanel.setBackground(new Color(235, 238, 243));
        btnPanel.setBorder(BorderFactory.createEmptyBorder(10, 80, 20, 80));

        JButton confirmBtn = new JButton("CONFIRM PURCHASE");
        JButton closeBtn = new JButton("CLOSE");

        confirmBtn.setBackground(Color.WHITE);
        closeBtn.setBackground(Color.WHITE);
        confirmBtn.setFocusPainted(false);
        closeBtn.setFocusPainted(false);
        confirmBtn.setFont(new Font("Arial", Font.BOLD, 13));
        closeBtn.setFont(new Font("Arial", Font.PLAIN, 13));

        btnPanel.add(confirmBtn);
        btnPanel.add(closeBtn);
        add(btnPanel, BorderLayout.SOUTH);

        // ===== Button Actions =====
        closeBtn.addActionListener(e -> dispose());
        confirmBtn.addActionListener(e -> showOwnerConfirmation());
    }

    private void showOwnerConfirmation() {
        // Owner detail message
        String message = "<html><b>Owner Details:</b><br><br>" +
                "Name: " + plot.getOwnerName() + "<br>" +
                "Phone: " + plot.getOwnerPhone() + "<br>" +
                "Email: " + plot.getOwnerEmail() + "<br><br>" +
                "Do you want to proceed with the purchase?</html>";

        int choice = JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirm Purchase",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            recordPurchase();
        }
    }

    private void recordPurchase() {
        if (!"AVAILABLE".equalsIgnoreCase(plot.getStatus())) {
            JOptionPane.showMessageDialog(this, "This plot is no longer available!");
            return;
        }

        Purchase purchase = new Purchase();
        purchase.setUserId(user.getId());
        purchase.setPlotId(plot.getId());
        purchase.setPricePaid(plot.getPrice());
        purchase.setPurchasedAt(Timestamp.valueOf(LocalDateTime.now()));

        PurchaseDAO pdao = new PurchaseDAO();
        PlotDAO plotDAO = new PlotDAO();

        boolean success = pdao.recordPurchase(purchase);

        if (success) {
            plotDAO.markPlotSold(plot.getId());
            JOptionPane.showMessageDialog(this,
                    "Purchase Confirmed!\nYou have successfully bought the plot.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error recording purchase.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
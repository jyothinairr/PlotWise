package user;

import dao.PlotDAO;
import models.Plot;
import models.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class UserDashboard extends JFrame {
    private final User user;
    private final JPanel plotPanel;

    public UserDashboard(User user) {
        this.user = user;

        setTitle("User Dashboard - " + user.getFullName());
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(235, 238, 243)); 

       
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        topPanel.setBackground(new Color(245, 247, 250));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Welcome " + user.getFullName(), SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(title);

        JButton historyBtn = createButton("Purchase History");
        JButton logoutBtn = createButton("Logout");
        topPanel.add(historyBtn);
        topPanel.add(logoutBtn);
        add(topPanel, BorderLayout.NORTH);

      
        plotPanel = new JPanel();
        plotPanel.setLayout(new GridLayout(0, 4, 10, 10)); 
        plotPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        plotPanel.setBackground(new Color(235, 238, 243));

        JScrollPane scrollPane = new JScrollPane(plotPanel);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

       
        historyBtn.addActionListener(e -> new PurchaseHistoryFrame(user).setVisible(true));
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        loadPlots();
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));
        btn.setPreferredSize(new Dimension(160, 35));
        return btn;
    }

    private void loadPlots() {
        plotPanel.removeAll();
        PlotDAO dao = new PlotDAO();
        List<Plot> plots = dao.getAvailablePlots();

        for (Plot p : plots) {
            JPanel card = new JPanel(new BorderLayout());
            card.setPreferredSize(new Dimension(200, 160));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                    new EmptyBorder(10, 10, 10, 10)
            ));

            
            JLabel title = new JLabel(p.getTitle(), SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 14));
            card.add(title, BorderLayout.NORTH);

           
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new GridLayout(2, 1, 5, 5));
            infoPanel.setBackground(Color.WHITE);

            JLabel acresLabel = new JLabel("Acres: " + p.getAcres(), SwingConstants.CENTER);
            JLabel priceLabel = new JLabel("Price: ₹" + p.getPrice(), SwingConstants.CENTER);

            acresLabel.setFont(new Font("Arial", Font.PLAIN, 13));
            priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));

            infoPanel.add(acresLabel);
            infoPanel.add(priceLabel);

            card.add(infoPanel, BorderLayout.CENTER);

           
            JButton viewBtn = new JButton("View Details");
            viewBtn.setFont(new Font("Arial", Font.PLAIN, 12));
            viewBtn.setFocusPainted(false);
            viewBtn.setBackground(new Color(245, 247, 250));
            viewBtn.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1, true));
            viewBtn.addActionListener(e -> new PlotDetailFrame(p, user).setVisible(true));

            card.add(viewBtn, BorderLayout.SOUTH);

            plotPanel.add(card);
        }

        plotPanel.revalidate();
        plotPanel.repaint();
    }
}
package user;

import dao.PurchaseDAO;
import models.Purchase;
import models.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class PurchaseHistoryFrame extends JFrame {
    private JTable table;

    public PurchaseHistoryFrame(User user) {
        setTitle("Purchase History - " + user.getFullName());
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(235, 238, 243));
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Your Purchase History", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        table = new JTable();
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(245, 247, 250));
        header.setFont(new Font("Arial", Font.BOLD, 13));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        loadHistory(user);
        setVisible(true);
    }

    private void loadHistory(User user) {
        PurchaseDAO dao = new PurchaseDAO();
        List<Purchase> list = dao.getPurchasesByUser(user.getId());

        String[] cols = {"Plot Title", "Price Paid", "Purchased At", "Owner Name"};
        Object[][] data = new Object[list.size()][cols.length];

        for (int i = 0; i < list.size(); i++) {
            Purchase p = list.get(i);
            data[i][0] = p.getPlotTitle();
            data[i][1] = "₹" + p.getPricePaid();
            data[i][2] = p.getPurchasedAt();
            data[i][3] = p.getOwnerName();
        }

        table.setModel(new javax.swing.table.DefaultTableModel(data, cols));
    } 
}
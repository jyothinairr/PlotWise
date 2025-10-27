package dao;

import models.Purchase;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDAO {

    public boolean recordPurchase(Purchase p) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO purchases (user_id, plot_id, price_paid) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getUserId());
            ps.setInt(2, p.getPlotId());
            ps.setDouble(3, p.getPricePaid());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

   public List<Purchase> getPurchasesByUser(int userId) {
        List<Purchase> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = """
                SELECT p.user_id, p.plot_id, p.price_paid, p.purchased_at,
                       pl.title, pl.owner_name
                FROM purchases p
                LEFT JOIN plots pl ON p.plot_id = pl.id
                WHERE p.user_id = ?
                ORDER BY p.purchased_at DESC
            """;
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.setUserId(rs.getInt("user_id"));
                purchase.setPlotId(rs.getInt("plot_id"));
                purchase.setPricePaid(rs.getDouble("price_paid"));
                purchase.setPurchasedAt(rs.getTimestamp("purchased_at"));
                purchase.setPlotTitle(rs.getString("title"));
                purchase.setOwnerName(rs.getString("owner_name"));
                list.add(purchase);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
package dao;

import models.Plot;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlotDAO {

    public List<Plot> getAvailablePlots() {
        List<Plot> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM plots WHERE status='AVAILABLE'");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Plot p = new Plot();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setAddress(rs.getString("address"));
                p.setAcres(rs.getDouble("acres"));
                p.setPrice(rs.getDouble("price"));
                p.setStatus(rs.getString("status"));
                p.setOwnerName(rs.getString("owner_name"));
                p.setOwnerPhone(rs.getString("owner_phone"));
                p.setOwnerEmail(rs.getString("owner_email"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
        public List<Plot> getAllPlots() {
        List<Plot> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM plots");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Plot p = new Plot();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setAddress(rs.getString("address"));
                p.setAcres(rs.getDouble("acres"));
                p.setPrice(rs.getDouble("price"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Plot> getPlotsByStatus(String status) {
        List<Plot> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM plots WHERE status=?");
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Plot p = new Plot();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setAddress(rs.getString("address"));
                p.setAcres(rs.getDouble("acres"));
                p.setPrice(rs.getDouble("price"));
                p.setStatus(rs.getString("status"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addPlot(Plot p) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO plots (title,address,acres,price,status,owner_name,owner_phone,owner_email) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getAddress());
            ps.setDouble(3, p.getAcres());
            ps.setDouble(4, p.getPrice());
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getOwnerName());
            ps.setString(7, p.getOwnerPhone());
            ps.setString(8, p.getOwnerEmail());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePlot(Plot p) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE plots SET title=?, address=?, acres=?, price=?, status=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, p.getTitle());
            ps.setString(2, p.getAddress());
            ps.setDouble(3, p.getAcres());
            ps.setDouble(4, p.getPrice());
            ps.setString(5, p.getStatus());
            ps.setInt(6, p.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlot(int id) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM plots WHERE id=?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Plot getPlotById(int id) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM plots WHERE id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Plot p = new Plot();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setAddress(rs.getString("address"));
                p.setAcres(rs.getDouble("acres"));
                p.setPrice(rs.getDouble("price"));
                p.setStatus(rs.getString("status"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean markPlotSold(int id) {
        try (Connection con = DBConnection.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE plots SET status='SOLD' WHERE id=?");
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
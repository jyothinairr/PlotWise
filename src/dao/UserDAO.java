package dao;

import models.User;
import java.sql.*;
import javax.swing.JOptionPane;

public class UserDAO {

    public boolean registerUser(User u) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username,email,password_hash,full_name,phone,role) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPassword());
            ps.setString(4, u.getFullName());
            ps.setString(5, u.getPhone());
            ps.setString(6, "USER");
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    public User login(String email, String password) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE (username=? OR email=?) AND password_hash=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, email);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setFullName(rs.getString("full_name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean verifyUserInfo(String username, String email, String fullName) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE LOWER(TRIM(username))=? AND LOWER(TRIM(email))=? AND LOWER(TRIM(full_name))=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username.trim().toLowerCase());
            ps.setString(2, email.trim().toLowerCase());
            ps.setString(3, fullName.trim().toLowerCase());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePassword(String username, String newPassword) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE users SET password_hash=? WHERE LOWER(TRIM(username))=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, newPassword);
            ps.setString(2, username.trim().toLowerCase());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
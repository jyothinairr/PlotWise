package admin;

import dao.UserDAO;
import models.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminLoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn;
    private JLabel backLabel;

    public AdminLoginFrame() {
        setTitle("PlotWise - Admin Login");
        setSize(420, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(235, 238, 243)); 
        add(panel);

        
        JLabel title = new JLabel("ADMIN LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(0, 30, 420, 40);
        panel.add(title);

        
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userLabel.setBounds(80, 110, 100, 25);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 110, 160, 25);
        panel.add(usernameField);

        
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passLabel.setBounds(80, 160, 100, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 160, 160, 25);
        panel.add(passwordField);

        
        loginBtn = new JButton("Login");
        loginBtn.setBounds(160, 210, 100, 30);
        panel.add(loginBtn);

        
        backLabel = new JLabel("<html><u>Back to User Login</u></html>", SwingConstants.CENTER);
        backLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        backLabel.setForeground(new Color(0, 0, 128));
        backLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backLabel.setBounds(140, 260, 150, 30);
        panel.add(backLabel);

       
        loginBtn.addActionListener(e -> login());
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new user.LoginFrame().setVisible(true);
                dispose();
            }
        });
    }

    private void login() {
        String emailOrUser = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (emailOrUser.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO dao = new UserDAO();
        User u = dao.login(emailOrUser, pass);

        if (u != null && "ADMIN".equalsIgnoreCase(u.getRole())) {
            JOptionPane.showMessageDialog(this, "Welcome Admin " + u.getFullName());
            new AdminDashboard(u).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid admin credentials!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminLoginFrame().setVisible(true));
    }
}
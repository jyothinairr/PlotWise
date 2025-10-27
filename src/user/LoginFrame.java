package user;

import dao.UserDAO;
import models.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton submitBtn, signupBtn, adminBtn;
    private JLabel forgotLabel;

    public LoginFrame() {
        setTitle("PlotWise - Login");
        setSize(420, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(235, 238, 243)); 
        add(panel);

       
        JLabel title = new JLabel("LOGIN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(0, 30, 420, 40);
        panel.add(title);

       
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userLabel.setBounds(80, 100, 100, 25);
        panel.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 100, 160, 25);
        panel.add(usernameField);

        
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passLabel.setBounds(80, 150, 100, 25);
        panel.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 150, 160, 25);
        panel.add(passwordField);

        
        forgotLabel = new JLabel("<html><u>forgot password?</u></html>");
        forgotLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        forgotLabel.setForeground(Color.DARK_GRAY);
        forgotLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotLabel.setBounds(245, 180, 150, 25);
        panel.add(forgotLabel);

        forgotLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ForgotPasswordFrame().setVisible(true);
            }
        });

       
        submitBtn = new JButton("Submit");
        submitBtn.setBounds(160, 220, 100, 30);
        panel.add(submitBtn);

       
        signupBtn = new JButton("Don't have an account? Sign up");
        signupBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        signupBtn.setBounds(90, 270, 240, 30);
        panel.add(signupBtn);

       
        adminBtn = new JButton("Admin Login");
        adminBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        adminBtn.setBounds(160, 320, 100, 30);
        panel.add(adminBtn);

        
        submitBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> new SignupFrame().setVisible(true));
        adminBtn.addActionListener(e -> openAdminLogin());
    }

    private void login() {
        String email = usernameField.getText();
        String pass = new String(passwordField.getPassword());
        UserDAO dao = new UserDAO();
        User u = dao.login(email, pass);

        if (u != null) {
            if ("ADMIN".equals(u.getRole())) {
                JOptionPane.showMessageDialog(this, "For Admin Login, please use the admin login option.");
            } else {
                JOptionPane.showMessageDialog(this, "Welcome " + u.getFullName());
                new UserDashboard(u).setVisible(true);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!");
        }
    }

    private void openAdminLogin() {
        new admin.AdminLoginFrame().setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
package user;

import dao.UserDAO;
import models.User;
import javax.swing.*;
import java.awt.*;

public class SignupFrame extends JFrame {
    private JTextField usernameField, emailField, fullNameField, phoneField;
    private JPasswordField passwordField;

    public SignupFrame() {
        setTitle("PlotWise - Sign Up");
        setSize(420, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        getContentPane().setBackground(new Color(235, 238, 243));

        
        JLabel title = new JLabel("SIGN UP", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setBounds(0, 30, 420, 30);
        add(title);

        
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setFont(new Font("Arial", Font.BOLD, 13));
        userLabel.setBounds(70, 90, 100, 25);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(190, 90, 160, 25);
        add(usernameField);

        
        JLabel emailLabel = new JLabel("EMAIL");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 13));
        emailLabel.setBounds(70, 130, 100, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(190, 130, 160, 25);
        add(emailField);

        
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passLabel.setBounds(70, 170, 100, 25);
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(190, 170, 160, 25);
        add(passwordField);

        
        JLabel nameLabel = new JLabel("FULL NAME");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setBounds(70, 210, 100, 25);
        add(nameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(190, 210, 160, 25);
        add(fullNameField);

        
        JLabel phoneLabel = new JLabel("PHONE");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 13));
        phoneLabel.setBounds(70, 250, 100, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(190, 250, 160, 25);
        add(phoneField);

        
        JButton registerBtn = new JButton("Register");
        registerBtn.setBounds(140, 310, 140, 35);
        add(registerBtn);

        
        JButton backBtn = new JButton("Back to Login");
        backBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        backBtn.setBounds(140, 360, 140, 30);
        add(backBtn);

        
        registerBtn.addActionListener(e -> register());
        backBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        setVisible(true);
    }

    private void register() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String fullName = fullNameField.getText().trim();
        String phone = phoneField.getText().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || fullName.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullName(fullName);
        user.setPhone(phone);

        UserDAO dao = new UserDAO();
        if (dao.registerUser(user)) {
            JOptionPane.showMessageDialog(this, "✅ Registration successful!");
            dispose();
            new LoginFrame().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Registration failed. Try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignupFrame::new);
    }
}
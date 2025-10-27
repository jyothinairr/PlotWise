package user;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;

public class ForgotPasswordFrame extends JFrame {
    private JTextField usernameField, emailField, nameField;
    private JPasswordField newPassField;

    public ForgotPasswordFrame() {
        setTitle("Reset Password");
        setSize(420, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        getContentPane().setBackground(new Color(235, 238, 243));

       
        JLabel title = new JLabel("RESET PASSWORD", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
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

       
        JLabel nameLabel = new JLabel("FULL NAME");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        nameLabel.setBounds(70, 170, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(190, 170, 160, 25);
        add(nameField);

        
        JLabel passLabel = new JLabel("NEW PASSWORD");
        passLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passLabel.setBounds(70, 210, 120, 25);
        add(passLabel);

        newPassField = new JPasswordField();
        newPassField.setBounds(190, 210, 160, 25);
        add(newPassField);

        
        JButton resetBtn = new JButton("Reset Password");
        resetBtn.setBounds(140, 270, 140, 35);
        add(resetBtn);

        resetBtn.addActionListener(e -> resetPassword());

        
        setVisible(true);
    }

    private void resetPassword() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String fullName = nameField.getText().trim();
        String newPassword = new String(newPassField.getPassword()).trim();

        if (username.isEmpty() || email.isEmpty() || fullName.isEmpty() || newPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDAO dao = new UserDAO();
        if (dao.verifyUserInfo(username, email, fullName)) {
            if (dao.updatePassword(username, newPassword)) {
                JOptionPane.showMessageDialog(this, "✅ Password updated successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to update password.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid details. Please check again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ForgotPasswordFrame::new);
    }
}
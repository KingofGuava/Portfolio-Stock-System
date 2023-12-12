package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePage extends JFrame {

    public WelcomePage() {
        // Create GUI components for the welcome page
        JLabel background = new JLabel(new ImageIcon(getClass().getResource("img/stock.png")));
        background.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Welcome to Stock Portfolio Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); // Set text color to white

        JButton customerLoginButton = createStyledButton("Customer Login");
        JButton adminLoginButton = createStyledButton("Admin Login");

        // Add action listeners
        customerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to handle customer login
                //dispose();
                try {
                    new CustomerLoginRegisterPage();
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add code to handle admin login
                //dispose();
                try {
                    new AdministratorLoginPage();
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // Set up the layout using GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 30, 0); // Add space above titleLabel
        background.add(titleLabel, gbc);

        gbc.gridy++;
        background.add(customerLoginButton, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(15, 0, 0, 0); // Add space above adminLoginButton
        background.add(adminLoginButton, gbc);

        // Set up the frame
        setContentPane(background); // Use background as the content pane
        setTitle("Stock Portfolio Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 350); // Adjust the size as needed
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(60, 141, 188)); // Custom button background color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove focus border
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomePage::new);
    }
}

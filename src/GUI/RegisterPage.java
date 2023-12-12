package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.AdministratorLoginPage;
import LogInRegistration.Register;

public class RegisterPage extends JFrame {

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Check if the phone number is 11 digits and consists only of numeric characters
        return phoneNumber.matches("\\d{11}");
    }

    private boolean isValidEmail(String email) {
        // Check if the email address has the correct format
        return email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
    }

    public RegisterPage() {
        // Create GUI components for the registration page
        JLabel titleLabel = new JLabel("Customer Registration", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Form fields
        addFormField(formPanel, gbc, "Username:", new JTextField());
        addFormField(formPanel, gbc, "Password:", new JPasswordField());
        addFormField(formPanel, gbc, "Confirm Password:", new JPasswordField());
        addFormField(formPanel, gbc, "Full Name:", new JTextField());
        addFormField(formPanel, gbc, "Phone:", new JTextField());
        addFormField(formPanel, gbc, "Address:", new JTextField());
        addFormField(formPanel, gbc, "Email:", new JTextField());

        // Submit button
        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your registration logic here
                String username = getFieldText(formPanel, "Username:");
                String password = getFieldText(formPanel, "Password:");
                String confirmPassword = getFieldText(formPanel, "Confirm Password:");
                String fullName = getFieldText(formPanel, "Full Name:");
                String phone = getFieldText(formPanel, "Phone:");
                String address = getFieldText(formPanel, "Address:");
                String email = getFieldText(formPanel, "Email:");

                // Validate and process the registration data
                if (password.equals(confirmPassword)) {
                    if (isFieldEmpty(username, "Username") || isFieldEmpty(password, "Password") ||
                            isFieldEmpty(fullName, "Full Name") || isFieldEmpty(phone, "Phone") ||
                            isFieldEmpty(email, "Email") || isFieldEmpty(address, "Address")) {
                        JOptionPane.showMessageDialog(RegisterPage.this, "Please fill in all required fields.");
                    } else {
                        if (!isValidPhoneNumber(phone)) {
                            JOptionPane.showMessageDialog(RegisterPage.this, "Please enter a valid 11-digit phone number.");
                            setFieldText(formPanel, "Phone:");

                        }else if (!isValidEmail(email)) {
                            JOptionPane.showMessageDialog(RegisterPage.this, "Please enter a valid email address.");
                            setFieldText(formPanel, "Email:");
                        }else {
                            // Registration successful
                            JOptionPane.showMessageDialog(RegisterPage.this, "Registration successful!");
                            dispose();

                            runRegister(username, password, fullName, phone, address, email);

                            // Add logic to save user data to a database or perform other actions
                        }
                    }
                } else {
                    // Passwords do not match
                    JOptionPane.showMessageDialog(RegisterPage.this, "Passwords do not match!");
                    setFieldText(formPanel, "Password:");
                    setFieldText(formPanel, "Confirm Password:");
                }



            }
        });

        // Style submit button
        styleSubmitButton(submitButton);

        // Create layout using GridBagLayout
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.SOUTH);

        // Set up the frame
        setTitle("Customer Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setSize(500, 600);  // Adjust the size as needed
        setVisible(true);
    }

    public void runRegister(String name, String password, String fullname, String phone, String address, String email){
        Register register = new Register(name, password, fullname, phone, address, email);
        register.runLogReg();
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField) {
        JLabel label = new JLabel(labelText);
        styleFormField(label, textField);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(label, gbc);

        gbc.gridx = 1;
        panel.add(textField, gbc);
    }

    private String getFieldText(JPanel panel, String labelText) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().equals(labelText)) {
                    int index = panel.getComponentZOrder(label);
                    JTextField textField = (JTextField) panel.getComponent(index + 1);
                    return textField.getText();
                }
            }
        }
        return "";
    }

    private void setFieldText(JPanel panel, String labelText) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getText().equals(labelText)) {
                    int index = panel.getComponentZOrder(label);
                    JTextField textField = (JTextField) panel.getComponent(index + 1);
                    textField.setText("");
                }
            }
        }
    }

    private boolean isFieldEmpty(String value, String fieldName) {
        return value.trim().isEmpty();
    }

    private void styleFormField(JLabel label, JTextField field) {
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        field.setFont(new Font("Arial", Font.PLAIN, 18));
        field.setPreferredSize(new Dimension(250, 40));
    }

    private void styleSubmitButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(new Color(60, 141, 188));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterPage::new);
    }
}

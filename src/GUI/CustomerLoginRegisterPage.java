package GUI;

import LogInRegistration.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerLoginRegisterPage extends JFrame implements LogRegIface{
    JLabel topNameLabel = new JLabel("Customer login and registration", JLabel.CENTER);

    SpringLayout springLayout = new SpringLayout();
    JPanel centerPanel = new JPanel(springLayout);
    JLabel userNameLabel = new JLabel("Username: ");
    JTextField userNameTextField = new JTextField();
    JLabel passwordLabel = new JLabel("Password: ");
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("login");
    JButton registerButton = new JButton("register");

    public CustomerLoginRegisterPage() throws AWTException {
        super("Customer stock management system login");
        Container contentPane = getContentPane();

        // Custom icon:
        URL url = AdministratorLoginPage.class.getClassLoader().getResource("GUI/img/stock.png");
        Image image = new ImageIcon(url).getImage();
        setIconImage(image);

        // Set fonts:
        Font font = new Font("Arial", Font.PLAIN, 40);
        topNameLabel.setFont(font);
        topNameLabel.setPreferredSize(new Dimension(0, 80));

        Font font1 = new Font("Arial", Font.PLAIN, 20);
        userNameLabel.setFont(font1);
        userNameTextField.setPreferredSize(new Dimension(200, 30));
        passwordLabel.setFont(font1);
        passwordField.setPreferredSize(new Dimension(200, 30));
        loginButton.setFont(font1);
        registerButton.setFont(font1);
        styleSubmitButton(loginButton);
        styleSubmitButton(registerButton);
        // Add all components to the center panel:
        centerPanel.add(userNameLabel);
        centerPanel.add(userNameTextField);
        centerPanel.add(passwordLabel);
        centerPanel.add(passwordField);
        centerPanel.add(loginButton);
        centerPanel.add(registerButton);

        // Add the title to the north of the panel:
        contentPane.add(topNameLabel, BorderLayout.NORTH);
        // Add the center panel to the content pane:
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Set the spring layout for the content in the center panel
        // 1. Layout for userNameLabel
        Spring userNameLabelWidth = Spring.width(userNameLabel);
        Spring userNameTextFieldWidth = Spring.width(userNameTextField);
        Spring springWidth = Spring.constant(20);
        Spring childWidth = Spring.sum(Spring.sum(userNameLabelWidth, userNameTextFieldWidth), springWidth);
        int offsetX = childWidth.getValue() / 2;

        springLayout.putConstraint(SpringLayout.WEST, userNameLabel, -offsetX, SpringLayout.HORIZONTAL_CENTER, centerPanel);
        springLayout.putConstraint(SpringLayout.NORTH, userNameLabel, 20, SpringLayout.NORTH, centerPanel);

        // 2. Layout for userNameTextField
        springLayout.putConstraint(SpringLayout.WEST, userNameTextField, 5, SpringLayout.EAST, userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH, userNameTextField, 0, SpringLayout.NORTH, userNameLabel);

        // 3. Layout for passwordLabel
        springLayout.putConstraint(SpringLayout.EAST, passwordLabel, 0, SpringLayout.EAST, userNameLabel);
        springLayout.putConstraint(SpringLayout.NORTH, passwordLabel, 20, SpringLayout.SOUTH, userNameLabel);

        // 4. Layout for passwordTextField
        springLayout.putConstraint(SpringLayout.WEST, passwordField, 5, SpringLayout.EAST, passwordLabel);
        springLayout.putConstraint(SpringLayout.NORTH, passwordField, 0, SpringLayout.NORTH, passwordLabel);

        // 5. Layout for loginButton
        springLayout.putConstraint(SpringLayout.WEST, loginButton, 40, SpringLayout.WEST, passwordLabel);
        springLayout.putConstraint(SpringLayout.NORTH, loginButton, 40, SpringLayout.SOUTH, passwordLabel);

        // 6. Layout for resetButton
        springLayout.putConstraint(SpringLayout.WEST, registerButton, 60, SpringLayout.EAST, loginButton);
        springLayout.putConstraint(SpringLayout.NORTH, registerButton, 0, SpringLayout.NORTH, loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your login logic here
                String username = userNameTextField.getText();
                String password = passwordField.getText();
                // Compare the userName and Password
                if(username.equals("customer") && password.equals("123")) {
                    JOptionPane.showMessageDialog(CustomerLoginRegisterPage.this, "Login in!");
                    runLogIn(username,password);
                }
                else{
                    JOptionPane.showMessageDialog(CustomerLoginRegisterPage.this, "The username or password is incorrect!");
                    userNameTextField.setText("");
                    passwordField.setText("");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add your reset logic here
                dispose();
                new RegisterPage();
            }
        });

        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void styleSubmitButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        //button.setPreferredSize(new Dimension(150, 50));
        button.setBackground(new Color(60, 141, 188));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void runLogIn(String username, String password){
        LogIn login = new LogIn(username, password);
        login.runLogReg();
    }
    public void runRegister(String name, String password, String fullname, String phone, String address, String email){
        Register register = new Register(name, password, fullname, phone, address, email);
        register.runLogReg();
    }

    public static void main(String[] args) throws AWTException {
        new CustomerLoginRegisterPage();
    }
}

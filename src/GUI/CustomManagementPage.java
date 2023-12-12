package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomManagementPage extends JFrame {
    private JPanel menuPanel;
    private JPanel contentPanel;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModel2;
    private JTextField aTextField;
    private JTextField bTextField;
    private JTextField profitRealizedTextField;
    private JTextField profitUnrealizedTextField;
    private JTextField currentFundsTextField;
    private JTextField withdrawAmountTextField;
    private JTextField depositAmountTextField;

    private double funds;
    private JButton withdrawButton;
    private JButton depositButton;
    public CustomManagementPage() {
        setTitle("Personal stock management system");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(800, 600);
        setLocationRelativeTo(null);

        createMenuPanel();
        createContentPanel();

        add(menuPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(44, 62, 80)); // Set background color

        JButton stockButton = createMenuButton("Manage Stocks");
        JButton customerButton = createMenuButton("      Account      ");

        menuPanel.add(createMenuTitle("Customer")); // Title for the menu
        menuPanel.add(Box.createVerticalStrut(20)); // Spacing

        menuPanel.add(stockButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(customerButton);

        // Add flexible space to fill the remaining height
        menuPanel.add(Box.createVerticalGlue());

        stockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStockPanel();
            }
        });

        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCustomerPanel();
            }
        });
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBackground(new Color(52, 73, 94)); // Set button background color
        button.setForeground(Color.WHITE); // Set text color
        return button;
    }

    private JLabel createMenuTitle(String title) {
        JLabel titleLabel = new JLabel(title);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE); // Set text color
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        return titleLabel;
    }

    private void createContentPanel() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JLabel defaultLabel = new JLabel("Select an option from the menu to get started.");
        defaultLabel.setHorizontalAlignment(JLabel.CENTER);
        defaultLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        contentPanel.add(defaultLabel, BorderLayout.CENTER);
    }

    private void showStockPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new FlowLayout());
        // Sample data for the stock table (replace this with your actual data)
        String[] columnNames = {"Stock Name", "Stock Price"};
        String[][] tableValues = {{"Stock 1", "100"},{"Stock 2", "100"},{"Stock 3", "100"}};
        tableModel = new DefaultTableModel(tableValues, columnNames);
        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for the table

        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowSorter(new TableRowSorter<>(tableModel));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        table.setRowSorter(new TableRowSorter<>(tableModel));// Set the table sorter
        // Set the table selection mode to single selection
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        final Object[] oa = {null};
        final Object[] ob = {null};
        // Add a mouse event listener to the table
        table.addMouseListener(new MouseAdapter(){
            // Handle a mouse click event
            public void mouseClicked(MouseEvent e){
                // Get the index of the selected row
                int selectedRow = table.getSelectedRow();
                // Get the value of the specified cell from the table model
                oa[0] = tableModel.getValueAt(selectedRow, 0);
            }
        });
        scrollPane.setViewportView(table);
        final JPanel panel = new JPanel();
        contentPanel.add(panel, BorderLayout.SOUTH);
        panel.add(new JLabel("Purchase amount:"));
        aTextField = new JTextField("XXX", 10);
        aTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for text field
        panel.add(aTextField);
        final JButton buyButton = new JButton("buy");
        buyButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for button
        buyButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(oa[0]!=null) {
                    String money = aTextField.getText();
                    try {
                        // Attempt to convert the money string to a double
                        double moneyValue = Double.parseDouble(money);

                        // Check if the value is non-negative
                        if (moneyValue >= 0) {
                            // Valid positive double value
                            JOptionPane.showMessageDialog(CustomManagementPage.this, "You hava buyed " + oa[0].toString() + " " + money + " !");
                        } else {
                            // Handle the case when the value is negative
                            JOptionPane.showMessageDialog(CustomManagementPage.this, "Money amount must be non-negative.");
                        }
                    } catch (NumberFormatException ex) {
                        // Handle the case when the conversion fails
                        JOptionPane.showMessageDialog(CustomManagementPage.this, "Invalid money amount. Please enter a valid number.");
                    }
                }else{
                    JOptionPane.showMessageDialog(CustomManagementPage.this, "You should choose one socket at first!");
                }
            }
        });
        panel.add(buyButton);
        panel.add(new JLabel("Selling amount:"));
        bTextField = new JTextField("XXX", 10);
        bTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for text field
        panel.add(bTextField);
        // Update data to table
        // You should set your own rules on how to buy and sell stocks.
        // When you don't have enough money, you can't buy stocks.
        // When you don't have enough stocks in hand, you can't sell them
        //Delete data from table
        final JButton sellButton = new JButton("sell");
        sellButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for button
        sellButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String money = bTextField.getText();
                if(oa[0]!=null) {
                    try {
                        // Attempt to convert the money string to a double
                        double moneyValue = Double.parseDouble(money);

                        // Check if the value is non-negative
                        if (moneyValue >= 0) {
                            // Valid positive double value
                            JOptionPane.showMessageDialog(CustomManagementPage.this, "You hava selled " + oa[0].toString() + " " + money + " !");
                        } else {
                            // Handle the case when the value is negative
                            JOptionPane.showMessageDialog(CustomManagementPage.this, "Money amount must be non-negative.");
                        }
                    } catch (NumberFormatException ex) {
                        // Handle the case when the conversion fails
                        JOptionPane.showMessageDialog(CustomManagementPage.this, "Invalid money amount. Please enter a valid number.");
                    }
                }else{
                    JOptionPane.showMessageDialog(CustomManagementPage.this, "You should choose one socket at first!");
                }
            }
        });
        panel.add(sellButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showCustomerPanel() {
        //Set the Existing funds
        funds = 1000000;
        contentPanel.removeAll();

        // Labels for realized and unrealized profits
        JLabel realizedProfitLabel = new JLabel("Realized profits:");
        realizedProfitLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size

        profitRealizedTextField = new JTextField(10);
        profitRealizedTextField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        // Set the profit already obtained here
        profitRealizedTextField.setText("1000.0");
        profitRealizedTextField.setEditable(false);
        JLabel unrealizedProfitLabel = new JLabel("Unrealized profits:");
        unrealizedProfitLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size

        profitUnrealizedTextField = new JTextField(10);
        profitUnrealizedTextField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        profitUnrealizedTextField.setEditable(false);
        // Set the profit already un-obtained here
        profitUnrealizedTextField.setText("10000.0");
        // Label for current funds
        JLabel currentFundsLabel = new JLabel("Existing funds:");
        currentFundsLabel.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size

        currentFundsTextField = new JTextField(10);
        currentFundsTextField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        currentFundsTextField.setEditable(false);
        //Set initial funds
        currentFundsTextField.setText(funds+"");
        // Text fields for withdrawal and deposit with tooltips as hints
        withdrawAmountTextField = new JTextField(10);
        withdrawAmountTextField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        withdrawAmountTextField.setToolTipText("Withdrawal amount");

        depositAmountTextField = new JTextField(10);
        depositAmountTextField.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        depositAmountTextField.setToolTipText("Recharge amount");

        // Buttons for withdrawal and deposit
        withdrawButton = new JButton("Withdraw");
        withdrawButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        withdrawButton.setPreferredSize(new Dimension(150, 40)); // Increase button size

        depositButton = new JButton("Recharge");
        depositButton.setFont(new Font("Arial", Font.PLAIN, 18)); // Increase font size
        depositButton.setPreferredSize(new Dimension(150, 40)); // Increase button size

        // Action listeners for withdrawal and deposit buttons
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle withdrawal action
                String money = withdrawAmountTextField.getText(); // Increase font size
                try {
                    // Attempt to convert the money string to a double
                    double moneyValue = Double.parseDouble(money);
                    // Check if the value is non-negative
                    if (moneyValue >= 0 && moneyValue < funds) {
                        // Valid positive double value
                        JOptionPane.showMessageDialog(CustomManagementPage.this, "You hava withdrew " + money + " !");
                        funds = funds - moneyValue;
                        currentFundsTextField.setText(funds+"");
                    } else {
                        // Handle the case when the value is negative
                        JOptionPane.showMessageDialog(CustomManagementPage.this, "Money amount must be non-negative or the money larger than amount.");
                    }
                } catch (NumberFormatException ex) {
                    // Handle the case when the conversion fails
                    JOptionPane.showMessageDialog(CustomManagementPage.this, "Invalid money amount. Please enter a valid number.");
                }
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle deposit action
                String money = depositAmountTextField.getText(); // Increase font size
                try {
                    // Attempt to convert the money string to a double
                    double moneyValue = Double.parseDouble(money);
                    // Check if the value is non-negative
                    if (moneyValue >= 0) {
                        // Valid positive double value
                        JOptionPane.showMessageDialog(CustomManagementPage.this, "You hava deposited " + money + " !");
                        funds = funds + moneyValue;
                        currentFundsTextField.setText(funds+"");
                    } else {
                        // Handle the case when the value is negative
                        JOptionPane.showMessageDialog(CustomManagementPage.this, "Money amount must be non-negative.");
                    }
                } catch (NumberFormatException ex) {
                    // Handle the case when the conversion fails
                    JOptionPane.showMessageDialog(CustomManagementPage.this, "Invalid money amount. Please enter a valid number.");
                }
            }
        });

        // Set layout for the content panel
        contentPanel.setLayout(new GridLayout(6, 2, 10, 5));  // Reduced vertical gap to 5

        // Add components to the content panel
        contentPanel.add(realizedProfitLabel);
        contentPanel.add(profitRealizedTextField);
        contentPanel.add(unrealizedProfitLabel);
        contentPanel.add(profitUnrealizedTextField);
        contentPanel.add(currentFundsLabel);
        contentPanel.add(currentFundsTextField);
        contentPanel.add(withdrawAmountTextField);
        contentPanel.add(withdrawButton);
        contentPanel.add(depositAmountTextField);
        contentPanel.add(depositButton);

        // Add padding and set border
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.revalidate();
        contentPanel.repaint();
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CustomManagementPage());
    }


}

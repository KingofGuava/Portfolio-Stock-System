package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;

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

    private DefaultTableModel historyTableModel;
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void createMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(44, 62, 80)); // Set background color

        JButton stockButton = createMenuButton("Manage Stocks ");
        JButton customerButton = createMenuButton("      Account      ");
        JButton messageButton = createMenuButton("      Message     ");

        menuPanel.add(createMenuTitle("Customer")); // Title for the menu
        menuPanel.add(Box.createVerticalStrut(20)); // Spacing

        menuPanel.add(stockButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(customerButton);
        menuPanel.add(Box.createVerticalStrut(10)); // Spacing
        menuPanel.add(messageButton);

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

        messageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessagePanel();
            }
        });
    }

    private void showMessagePanel() {
        contentPanel.revalidate();
        contentPanel.repaint();
        contentPanel.removeAll();

        // Title Label
        JLabel titleLabel = new JLabel("Received Message");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        // Large JTextfield
        JTextArea messageTextArea = new JTextArea("You have obtained the qualification to trade options");
        messageTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        messageTextArea.setLineWrap(true);
        messageTextArea.setWrapStyleWord(true);
        messageTextArea.setEditable(false); // Set to non-editable

        // ScrollPane for JTextfield
        JScrollPane scrollPane = new JScrollPane(messageTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.PLAIN, 18));
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageTextArea.setText("There is currently no other message available");
            }
        });

        // Set layout for the content panel
        contentPanel.setLayout(new BorderLayout());

        // Add components to the content panel
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(okButton, BorderLayout.SOUTH);

        // Add padding and set border
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        contentPanel.revalidate();
        contentPanel.repaint();
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

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);

        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.GRAY);
                    textField.setText(placeholder);
                }
            }
        });
    }
    private void showStockPanel() {
        contentPanel.removeAll();
        contentPanel.setLayout(new GridLayout(4, 2, 10, 5)); // Adjust rows to accommodate components

        // Sample data for the stock table (replace this with your actual data)
        String[] stockColumnNames = {"Stock Name", "Stock Price"};
        String[][] stockTableValues = {{"Stock 1", "100"}, {"Stock 2", "100"}, {"Stock 3", "100"}};
        DefaultTableModel stockTableModel = new DefaultTableModel(stockTableValues, stockColumnNames);
        JTable stockTable = new JTable(stockTableModel);
        stockTable.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for the table

        JScrollPane stockScrollPane = new JScrollPane(stockTable);
        stockTable.setRowSorter(new TableRowSorter<>(stockTableModel));
        contentPanel.add(stockScrollPane);

        // History transactions table
        String[] historyColumnNames = {"Transaction Date", "Transaction Type", "Amount"};
        String[][] historyTableValues = {{"2023-01-01", "Buy", "50"}, {"2023-02-01", "Sell", "30"}};
        historyTableModel = new DefaultTableModel(historyTableValues, historyColumnNames);
        JTable historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        contentPanel.add(historyScrollPane);

        // Stock selection listener
        stockTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    String stockName = stockTableModel.getValueAt(selectedRow, 0).toString();
                    System.out.println(stockName);
                    loadHistoryTransactions(stockName);
                }
            }
        });

        // Buttons panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());

        // JTextField components
        JTextField textFieldBeforeBuy = new JTextField(15); // Adjust the size as needed
        addPlaceholder(textFieldBeforeBuy, "The amount you buy");
        buttonsPanel.add(textFieldBeforeBuy);
        JTextField textFieldBeforeSell = new JTextField(15); // Adjust the size as needed
        addPlaceholder(textFieldBeforeSell, "The amount you sell");

        // Buy button
        JButton buyButton = new JButton("Buy");
        buyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    String stockName = stockTableModel.getValueAt(selectedRow, 0).toString();
                    String amount = textFieldBeforeBuy.getText();
                    // Implement buy logic using the selected stock information
                }
            }
        });
        buttonsPanel.add(buyButton);

        // JTextField component before Sell
        buttonsPanel.add(textFieldBeforeSell);

        // Sell button
        JButton sellButton = new JButton("Sell");
        sellButton.setFont(new Font("Arial", Font.PLAIN, 16));
        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = stockTable.getSelectedRow();
                if (selectedRow != -1) {
                    String stockName = stockTableModel.getValueAt(selectedRow, 0).toString();
                    String amount = textFieldBeforeSell.getText();
                    // Implement sell logic using the selected stock information
                }
            }
        });
        buttonsPanel.add(sellButton);

        contentPanel.add(buttonsPanel);

        contentPanel.revalidate();
        contentPanel.repaint();
    }


    // Method to load and display history transactions for a selected stock
    private void loadHistoryTransactions(String stockName) {
        // Implement logic to fetch and display history transactions for the selected stock
        // Update the history table based on the selected stock
        String[] historyColumnNames = {"Transaction Date", "Transaction Type", "Amount"};
        String[][] historyTableValues = {{"2023-01-01", "Buy", "5000"}, {"2023-02-02", "Sell", "3000"}};

        historyTableModel.setRowCount(0);
        for (String[] rowData : historyTableValues) {
            historyTableModel.addRow(rowData);
        }
        JTable historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 16));

        JScrollPane historyScrollPane = new JScrollPane(historyTable);
        contentPanel.add(historyScrollPane);
    }

    private void showCustomerPanel() {
        //Set the Existing funds
        contentPanel.revalidate();
        contentPanel.repaint();
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

package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;

public class AdminManagementPage extends JFrame {
    private JPanel menuPanel;
    private JPanel contentPanel;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableModel2;
    private JTextField aTextField;
    private JTextField bTextField;
    private JTextField textField1;
    private JTextField textField2;
    public AdminManagementPage() {
        setTitle("Stock Management Application");
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

        JButton stockButton = createMenuButton("Manage Stocks");
        JButton customerButton = createMenuButton("Manage Customers");

        menuPanel.add(createMenuTitle("Admin")); // Title for the menu
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
        // Add a mouse event listener to the table
        table.addMouseListener(new MouseAdapter(){
            // Handle a mouse click event
            public void mouseClicked(MouseEvent e){
                // Get the index of the selected row
                int selectedRow = table.getSelectedRow();
                // Get the value of the specified cell from the table model
                Object oa = tableModel.getValueAt(selectedRow, 0);
                // Get the value of the specified cell from the table model
                Object ob = tableModel.getValueAt(selectedRow, 1);
                aTextField.setText(oa.toString());
                bTextField.setText(ob.toString());
            }
        });
        scrollPane.setViewportView(table);
        final JPanel panel = new JPanel();
        contentPanel.add(panel, BorderLayout.SOUTH);
        panel.add(new JLabel("Name:"));
        aTextField = new JTextField("Stock 4", 10);
        aTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for text field
        panel.add(aTextField);
        panel.add(new JLabel("Space:"));
        bTextField = new JTextField("1000", 10);
        bTextField.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for text field
        panel.add(bTextField);
        //Add data to table
        final JButton addButton = new JButton("add");
        addButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for button
        addButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String[] rowValues = {aTextField.getText(),bTextField.getText()};// Create a table array
                tableModel.addRow(rowValues);
                int rowCount = table.getRowCount() + 1;
                aTextField.setText("Stock "+rowCount);
                bTextField.setText("100");

            }
        });
        panel.add(addButton);
        //Update data to table
        final JButton updButton = new JButton("update");
        updButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for button
        updButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int selectedRow = table.getSelectedRow();
                if(selectedRow != 1){
                    tableModel.setValueAt(aTextField.getText(),selectedRow, 0);
                    tableModel.setValueAt(bTextField.getText(), selectedRow, 1);
                }
            }
        });
        panel.add(updButton);
        //Delete data from table
        final JButton delButton = new JButton("delete");
        delButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for button
        delButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int selectedRow = table.getSelectedRow();
                if(selectedRow != 1){
                    tableModel.removeRow(selectedRow);
                }
            }
        });
        panel.add(delButton);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void loadHistoryTransactions(String customer) {
        // Implement logic to fetch and display history transactions for the selected stock
        // Update the history table based on the selected stock
        String[] historyColumnNames = {"Transaction Date", "Earn or Loss", "Amount"};
        String[][] historyTableValues = {{"2023-01-01", "earn", "5000"}, {"2023-02-02", "loss", "3000"}};

        // Create a new table model for history transactions
        DefaultTableModel historyTableModel = new DefaultTableModel(historyTableValues, historyColumnNames);

        // Create a table and set its font
        JTable historyTable = new JTable(historyTableModel);
        historyTable.setFont(new Font("Arial", Font.PLAIN, 16));

        // Create a scroll pane for the table
        JScrollPane historyScrollPane = new JScrollPane(historyTable);

        // Show the history transactions in a dialog
        JOptionPane.showMessageDialog(this, historyScrollPane, "History Transactions for " + customer, JOptionPane.PLAIN_MESSAGE);
    }

    private void showCustomerPanel() {
        contentPanel.removeAll();

        // Sample data for the stock table (replace this with your actual data)
        String[] columnNames = {"customer Name", "unrealized profits", "realized profits"};
        String[][] tableValues = {{"customer 1", "100", "200"},{"customer 2", "100", "300"},{"customer 3", "100", "400"}};
        tableModel2 = new NonEditableTableModel(tableValues, columnNames);
        JTable table = new JTable(tableModel2);
        table.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for the table
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String customer = tableModel2.getValueAt(selectedRow, 0).toString();
                    System.out.println(customer);
                    loadHistoryTransactions(customer);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        table.setRowSorter(new TableRowSorter<>(tableModel2));
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        table.setRowSorter(new TableRowSorter<>(tableModel2));// Set the table sorter
        // Set the table selection mode to single selection
        scrollPane.setViewportView(table);

        final JPanel panel = new JPanel();
        contentPanel.add(panel, BorderLayout.SOUTH);
        panel.add(new JLabel("Notify all customers who have made more than 10k:"));
        final JButton notifyButton = new JButton("notify");
        notifyButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for button
        notifyButton.addActionListener(new ActionListener(){
            // Who and how should you design algorithms to send messages to
            public void actionPerformed(ActionEvent e){
                JOptionPane.showMessageDialog(AdminManagementPage.this, "You hava notified all customers who have made more than 10k!");
            }
        });
        panel.add(notifyButton);
        panel.add(new JLabel("Approve new customers:"));
        final JButton approveButton = new JButton("check");
        approveButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Increase font size for button
        approveButton.addActionListener(new ActionListener(){
            // You should design an algorithm to obtain the registered user information from here
            public void actionPerformed(ActionEvent e){
                Object[][] customerData = {
                        {"John Doe", "123-456-7890", "john.doe@example.com"},
                        {"Jane Smith", "987-654-3210", "jane.smith@example.com"},
                        // Add more rows as needed
                };

                String[] columnNames = {"Name", "Phone", "Email"};

                // Create the table model
                DefaultTableModel tableModel = new DefaultTableModel(customerData, columnNames);

                // Create the table
                JTable table = new JTable(tableModel);

                // Create the scroll pane and add the table to it
                JScrollPane scrollPane = new JScrollPane(table);
                table.addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent e){
                        int selectedRow = table.getSelectedRow();
                        Object oa = tableModel.getValueAt(selectedRow, 0);
                        Object ob = tableModel.getValueAt(selectedRow, 1);
                        textField1.setText(oa.toString());
                        textField2.setText(ob.toString());
                    }
                });
                scrollPane.setViewportView(table);
                final JPanel panel = new JPanel();
                panel.add(new JLabel("name:"));
                textField1 = new JTextField("name",10);
                panel.add(textField1);
                panel.add(new JLabel("phone:"));
                textField2 = new JTextField("phoneNumber",10);
                panel.add(textField2);
                final JButton addButton = new JButton("approve");
                addButton.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                        int selectedRow = table.getSelectedRow();
                        if(selectedRow != 1){
                            tableModel.removeRow(selectedRow);
                        }
                    }
                });
                panel.add(addButton);
                // Create the dialog and set its properties
                JDialog dialog = new JDialog();
                dialog.add(panel, BorderLayout.SOUTH);
                dialog.setTitle("Customer Approval Table");
                dialog.setSize(400, 300);
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

                // Add the scroll pane to the dialog
                dialog.add(scrollPane);

                // Make the dialog visible
                dialog.setVisible(true);
            }
        });
        panel.add(approveButton);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminManagementPage());
    }

    class NonEditableTableModel extends DefaultTableModel {

        public NonEditableTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            // 返回 false 表示表格不可编辑
            return false;
        }
    }
}
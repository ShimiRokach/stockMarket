package il.ac.hit.stockMarket2.view;

import il.ac.hit.stockMarket2.model.Stock;
import il.ac.hit.stockMarket2.model.StockMarketException;
import il.ac.hit.stockMarket2.viewmodel.IViewModel;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class View implements IView {

    private IViewModel vm;
    private ApplicationUI ui;

    @Override
    public void setViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void showMessage(String text) {
        ui.showMessage(text);
    }

    @Override
    public void showItems(List<Stock> items) {
        ui.showItems(items);
    }

    @Override
    public void setItems(List<Stock> items) {
        ui.setItems(items);
    }

    public View() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View.this.ui = new ApplicationUI();
                View.this.ui.start();
            }
        });
    }

    public class ApplicationUI {

        //create the frame
        private JFrame frame;
        //JPanel for the header
        private JPanel panelTop;
        //JPanel for the start button
        private JPanel panelLow;
        //JPanel for the logo
        private JPanel panelLogo;
        //main screen header
        private JLabel header;
        //label for the logo
        private JLabel logo;
        //create the image
        private Image icon;
        //create the button
        private JButton startAppBtn;
        //create the the main screen
        private ApplicationUI2 applicationUI2;

        //create the list of items
        private List<Stock> items;

        /**
         * Class constructor.
         */
        public ApplicationUI() {
            //init the frame and setting the CostsManager title
            frame = new JFrame("StockMarket");
            //init the top panel
            panelTop = new JPanel();
            //init the lower panel
            panelLow = new JPanel();
            //init the logo panel
            panelLogo = new JPanel();
            //init the header of the main app screen
            header = new JLabel("STOCK MARKET");
            //setting image into icon
            /*icon = new ImageIcon(Application.class.getResource("/resources/creditcard.png")).getImage().
                    getScaledInstance(200, 200, Image.SCALE_SMOOTH);*/
            //create JLabel for the logo
            logo = new JLabel("");
            //init the start button and setting the start text in it
            startAppBtn = new JButton("START");
        }

        public void start() {

            //setting different color to the top panel
            panelTop.setBackground(new Color(51, 153, 255));
            //set border to the top panel
            panelTop.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            //setting different color to the low panel
            panelLow.setBackground(new Color(51, 153, 255));
            //set border to the low panel
            panelLow.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
            //setting different color to the logo panel
            panelLogo.setBackground(new Color(51, 153, 255));

            //set font to the header label
            header.setFont(new Font("Tahoma", Font.BOLD, 28));
            //set the text color of the header
            header.setForeground(Color.BLACK);

            //set the logo image
            //logo.setIcon(new ImageIcon(icon));

            //set the size and background color of the button
            startAppBtn.setSize(100, 100);
            startAppBtn.setBackground(Color.WHITE);
            //set the font and the color of the text in the button
            startAppBtn.setForeground(Color.BLACK);
            startAppBtn.setFont(new Font("Tahoma", Font.BOLD, 20));

            //listener for start button
            startAppBtn.addActionListener(e -> {
                //load the cost item from DB to the screen
                vm.getStocks();
                //setting the local variable mainScreen of ApplicationUI
                ApplicationUI.this.applicationUI2 = new ApplicationUI2();
                //start the main screen and display it
                ApplicationUI.this.applicationUI2.start();
                //close the ApplicationUI screen
                frame.dispose();

            });

            //adding all the components to the JPanels
            panelTop.add(header);
            panelLogo.add(logo);
            panelLow.add(startAppBtn);

            //adding all the panels to the frame
            frame.add(panelTop, BorderLayout.NORTH);
            frame.add(panelLow, BorderLayout.SOUTH);
            frame.add(panelLogo, BorderLayout.CENTER);

            //setting the size of the screen
            frame.setSize(500, 400);
            //end the program when user close the screen
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //make the screen visible
            frame.setVisible(true);

        }

        public void showMessage(String text) {
            if (SwingUtilities.isEventDispatchThread()) {
                applicationUI2.tfMessage.setText(text);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        applicationUI2.tfMessage.setText(text);
                    }
                });

            }
        }

        public void showItems(List<Stock> items) {

            //clear all the existing data
            int rows = applicationUI2.tableModel.getRowCount();
            for (int i = rows - 1; i >= 0; i--) {
                applicationUI2.tableModel.removeRow(i);
            }
            //check items is not null
            if (items != null) {

                //fill the table
                for (Stock item : items) {
                    String id = String.valueOf(item.getId());
                    String name = item.getName();
                    String amount = String.valueOf(item.getAmount());
                    String price = String.valueOf(item.getPrice());

                    String[] data = {id, name, amount, price};

                    if (SwingUtilities.isEventDispatchThread()) {
                        //add the String array to the tableModel
                        applicationUI2.tableModel.addRow(data);

                    } else {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                //add the String array to the tableModel
                                applicationUI2.tableModel.addRow(data);

                            }
                        });

                    }

                }

            }

        }

        public void setItems(List<Stock> items) {

            this.items = items;

        }


        public class ApplicationUI2 //implements IView
        {

            private JFrame frame;
            private JPanel panelTop;
            private JPanel panelBottom;
            private JPanel panelMain;
            private JPanel panelMessage;
            private JTextField tfItemName;
            private JTextField tfItemAmount;
            private JTextField tfItemPrice;
            private JTextField tfMessage;
            private JButton btAddStock, btDeleteStock;
            private JScrollPane scrollPane;
            private JTextArea textArea;
            private JLabel lbItemName;
            private JLabel lbItemAmount;
            private JLabel lbItemPrice;
            private JLabel lbMessage;

            private List<Stock> items;

            //create the table that contain the items
            private JTable table;
            //create the DefaultTableModel
            private DefaultTableModel tableModel;


            public ApplicationUI2() {

                setItemsList(ApplicationUI.this.items);

                //creating the window
                frame = new JFrame("StockMarket");
                //creating the four panels
                panelMain = new JPanel();
                panelBottom = new JPanel();
                panelTop = new JPanel();
                panelMessage = new JPanel();
                //creating the main ui components
                tfItemName = new JTextField(8);
                tfItemAmount = new JTextField(8);
                tfItemPrice = new JTextField(20);
                btAddStock = new JButton("Add Stock");
                btDeleteStock = new JButton("Delete Stock");
                textArea = new JTextArea();
                lbItemName = new JLabel("Stock Name:");
                lbItemAmount = new JLabel("Stock Amount:");
                lbItemPrice = new JLabel("Stock Price:");
                //creating the messages ui components
                lbMessage = new JLabel("Message: ");
                tfMessage = new JTextField(30);

                loadCostsTable();
                scrollPane = new JScrollPane(table);
            }

            public void start() {

                //adding the components to the top panel
                panelTop.add(lbItemName);
                panelTop.add(tfItemName);
                panelTop.add(lbItemAmount);
                panelTop.add(tfItemAmount);
                panelTop.add(lbItemPrice);
                panelTop.add(tfItemPrice);
                panelTop.add(btAddStock);
                panelTop.add(btDeleteStock);

                //setting BorderLayout as the LayoutManager for panelMain
                panelMain.setLayout(new BorderLayout());

                //setting GridLayout 1x1 as the LayoutManager for panelBottom
                panelBottom.setLayout(new GridLayout(1, 1));

                //adding the components to the bottom panel
                panelBottom.add(scrollPane);

                //adding the components to the messages panel
                panelMessage.add(lbMessage);
                panelMessage.add(tfMessage);

                //setting a different color for the panel message
                panelMessage.setBackground(Color.GREEN);

                //setting the window layout manager
                frame.setLayout(new BorderLayout());

                //adding the two panels to the main panel
                //panelMain.add(panelTop);
                panelMain.add(panelBottom, BorderLayout.CENTER);

                //adding the main panel to the window
                frame.add(panelMain, BorderLayout.CENTER);

                //adding top panel to the window
                frame.add(panelTop, BorderLayout.NORTH);

                //adding the message panel to the window
                frame.add(panelMessage, BorderLayout.SOUTH);

                //handling window closing
                frame.addWindowListener(new WindowAdapter() {
                    /**
                     * Invoked when a window is in the process of being closed.
                     * The close operation can be overridden at this point.
                     *
                     * @param e
                     */
                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

                //handling cost item adding button click
                btAddStock.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            String name = tfItemName.getText();
                            if (name == null || name.length() == 0) {
                                throw new StockMarketException("name cannot be empty");
                            }
                            int amount = Integer.parseInt(tfItemAmount.getText());
                            double price = Double.parseDouble(tfItemPrice.getText());

                            Stock item = new Stock(name, amount, price);
                            vm.addStock(item);

                        } catch (NumberFormatException ex) {
                            View.this.showMessage("problem with entered data... " + ex.getMessage());
                        } catch (StockMarketException ex) {
                            View.this.showMessage("problem with entered name... " + ex.getMessage());
                        }
                    }
                });

                //handling cost item delete button click
                btDeleteStock.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {


                    }
                });

                //displaying the window
                frame.setSize(1200, 600);
                frame.setVisible(true);
            }

            public void setItemsList(List<Stock> items) {
                this.items = items;
            }

            public void loadCostsTable() {

                //set default table model
                tableModel = new DefaultTableModel();

                //create the costs table
                table = new JTable(tableModel);

                //add the columns to the table
                tableModel.addColumn("ID");
                tableModel.addColumn("NAME");
                tableModel.addColumn("AMOUNT");
                tableModel.addColumn("PRICE");

                if (items != null) {

                    for (Stock item : items) {
                        String id = String.valueOf(item.getId());
                        String name = item.getName();
                        String amount = String.valueOf(item.getAmount());
                        String price = String.valueOf(item.getPrice());

                        String[] data = {id, name, amount, price};

                        if (SwingUtilities.isEventDispatchThread()) {
                            tableModel.addRow(data);

                        } else {
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    tableModel.addRow(data);

                                }
                            });

                        }
                    }
                }
                table.setRowHeight(25);
                table.setFont(new Font("Arial", Font.PLAIN, 14));
                table.setForeground(Color.BLUE);

            }

        }

    }
}

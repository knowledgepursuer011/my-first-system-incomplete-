package finalProjectv3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class CashierUi extends ManagementUi{
	
	// LAYOUT
	// FRAME (reuse from management UI)
	// TASKBAR PANEL (reuse from management UI)
	// CATEGORIES PANEL (reuse from management UI)
	// BELOW CATEGORIES PANEL (reuse from management UI)
	// ITEM PANEL
	static JPanel itemPanel;
	// PAGE COUNTER PANEL
	static JPanel counterPanel;
	static JButton nextBtn2;
	static JButton previousBtn2;
	// CHECKOUT PANEL
	static JPanel checkoutPanel;
	static JButton calculateBtn;
	static JTextField payTextField;
	static JButton newOrderBtn;
	static JButton checkoutBtn;
	static JButton removeBtn;
	// USER PANEL
	static JPanel userPanel;
	// TABLE PANEL
	static JPanel checkoutTablePanel;
	static JScrollPane scroll2;
	static DefaultTableModel model2;
	static JTable table2;
	// table COMBO BOX
	static TableColumn column;
	// BUTTON FOR GENERATING ITEMS IN TABLE
	static String[] itemHeader2;
	// BREAKFOWN PANEL
	static JTextArea breakdown;
	// PANEL FOR GENERATING ITEM BUTTONS
	static JPanel[] itemSubPanel;
	static JButton[] itemButton;
	
	static TableColumn optionsColumn;
	static int pageCounter2 = 0;
	static int selectedCategoryIndex2 = 0;
	static final String template = "Enter payment amount here";
	static ArrayList<Transaction> cart;
	static String employee;
	
	
	public void cashierUi(String user) {
		cart = new ArrayList<>();
		employee = user;
		
		// LAYOUT INSTANTITAION
		// FRAME
		frame2 = new JFrame();
		// TASKBAR PANEL
		taskbarPanel = new JPanel();
		accountBtn = new JButton("Account");
		// BELOW CATEGORIES PANEL
		belowCategoriesPanel = new JPanel();
		nextBtn1 = new JButton("Next");
		previousBtn1 = new JButton("Previous");
		// CATEGORIES PANEL
		categoriesPanel = new JPanel();
		// ITEM PANEL
		itemPanel = new JPanel();	
		// PAGE COUNTER PANEL
		counterPanel = new JPanel();
		nextBtn2 = new JButton("Next");
		previousBtn2 = new JButton("Previous");
		// CHECKOUT PANEL
		checkoutPanel = new JPanel();
		calculateBtn = new JButton("Calculate");
		payTextField = new JTextField();
		newOrderBtn = new JButton("New Order");
		checkoutBtn = new JButton("Checkout");
		removeBtn = new JButton("Remove");
		// USER PANEL
		userPanel = new JPanel();
		// TABLE PANEL
		checkoutTablePanel = new JPanel();
		scroll2 = new JScrollPane();
		model2 = new DefaultTableModel();
		table2 = new JTable();
		// BREAKFOWN PANEL
		JPanel breakdownPanel = new JPanel();
		breakdown = new JTextArea();
		
		
		
		
		// LAYOUT SETUP
		// FRAME
		mainFrame();
		
		// TASKBAR PANEL
		setupTaskbarPanel2();
		
		
		// BELOW CATEGORIES PANEL
		setupBelowCategoeriesPanel2();
		
		// CATEGORIES PANEL
		setupCategoriesPanel();
		setupCategoryButton2();
			
		// ITEM PANEL
		itemPanel.setBounds(245, 5, 335, 455);
		itemPanel.setBackground(Color.decode("#202124"));
		itemPanel.setVisible(true);
		itemPanel.setLayout(null);
		frame2.add(itemPanel);
		
		// PAGE COUNTER PANEL
		counterPanel.setBounds(245, 465, 335, 50);
		counterPanel.setBackground(Color.decode("#202124"));
		counterPanel.setVisible(true);
		counterPanel.setLayout(null);
		frame2.add(counterPanel);
		
		// CHECKOUT PANEL
		checkoutPanel.setBounds(245, 520, 335, 160);
		checkoutPanel.setBackground(Color.decode("#202124"));
		checkoutPanel.setVisible(true);
		checkoutPanel.setLayout(null);
		frame2.add(checkoutPanel);
		
		calculateBtn.setBounds(5, 5, 160, 46);
		calculateBtn.setVisible(true);
		calculateBtn.setMargin(new Insets(5, 5, 5, 5));
		calculateBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cart.isEmpty()) {
					JOptionPane.showMessageDialog(frame2, "Please choose what to item to buy", "No item selected", JOptionPane.WARNING_MESSAGE);
				} else if (!isDouble(payTextField.getText())) {
					JOptionPane.showMessageDialog(frame2, "Please enter payment number", "Input Error", JOptionPane.WARNING_MESSAGE);
				} else if (isDouble(payTextField.getText())) {
					breakdown.setVisible(true);
					double pay =0;
					double quantity = 0;
					double price = 0;
					double amount = 0;
					double total = 0;
					double change = 0;
					pay = Double.parseDouble(payTextField.getText());
					for (int i = 0; i < table2.getRowCount(); i++) {
						Transaction tr = cart.get(i);
						quantity = tr.getQuantity();
						price = tr.getprice();
						amount = quantity * price;
						total = total + amount;
					}
					change = pay - total;
					String border1 = "********************************************************************************\n";
					String title = "                                                         JABEBEE\n";
					String border2 = "********************************************************************************\n";
					String product1 = String.format("%-89s %11s\n", "     Total", total);
					String product2  = String.format("%-89s %11s\n", "     Pay", pay);
					String overline  = String.format("%110s\n", "----------------------");
					String finaltotal  = String.format("%90s  %9s\n\n", "Change:", change);
					String border3 = "********************************************************************************\n";
					breakdown.setText(border1+title+border2+product1+product2+overline+finaltotal+border3);
					pay =0;
					quantity = 0;
					price = 0;
					amount = 0;
					total = 0;
					change= 0;
				}
			}
		});
		checkoutPanel.add(calculateBtn);
		
		payTextField.setBounds(170, 5, 160, 46);
		payTextField.setVisible(true);
		
		payTextField.setText(template);
		payTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (payTextField.getText().isEmpty()) {
					payTextField.setText(template);
                }
			}
			@Override
			public void focusGained(FocusEvent e) {
				if (payTextField.getText().equals(template)) {
					payTextField.setText("");
                }
			}
		});
		checkoutPanel.add(payTextField);
		
		newOrderBtn.setBounds(5, 56, 160, 46);
		newOrderBtn.setVisible(true);
		newOrderBtn.setMargin(new Insets(5, 5, 5, 5));
		newOrderBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				Transaction tr = new Transaction();
				cart.clear();
				tr.addTransaction();
				breakdown.setText("");
				payTextField.setText(template);
				model2 = new DefaultTableModel(create2dv2(cart), itemHeader2);
				table2.setModel(model2);
				setupTable2();
			}
		});
		checkoutPanel.add(newOrderBtn);
		
		checkoutBtn.setBounds(5, 107, 160, 46);
		checkoutBtn.setVisible(true);
		checkoutBtn.setMargin(new Insets(5, 5, 5, 5));
		checkoutBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cart.isEmpty()) {
					JOptionPane.showMessageDialog(frame2, "Please choose what to item to buy", "No item selected", JOptionPane.WARNING_MESSAGE);
				} else {
					int response = JOptionPane.showConfirmDialog(frame2, "Do you want to proceed?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						breakdown.setVisible(true);
						transactionComplete(cart);
						JOptionPane.showMessageDialog(frame2, "Transaction completed");
					} else if (response == JOptionPane.NO_OPTION) {
					}
				}
			}
		});
		checkoutPanel.add(checkoutBtn);
		
		removeBtn.setBounds(170, 56, 160, 46);
		removeBtn.setVisible(true);
		removeBtn.setMargin(new Insets(5, 5, 5, 5));
		removeBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = 0;
				if (table2.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(frame2, "Choose what item to remove by clicking the table", "No item selected", JOptionPane.WARNING_MESSAGE);
				} else {
					row = table2.getSelectedRow();
					cart.remove(row);
					model2 = new DefaultTableModel(create2dv2(cart), itemHeader2);
					table2.setModel(model2);
					setupTable2();
				}
			}
		});
		checkoutPanel.add(removeBtn);
		
		// USER PANEL
		userPanel.setBounds(585, 5, 550, 100);
		userPanel.setBackground(Color.decode("#202124"));
		userPanel.setVisible(true);
		userPanel.setLayout(null);
		frame2.add(userPanel);
		
		// TABLE PANEL
		checkoutTablePanel.setBounds(585, 110, 550, 350);
		checkoutTablePanel.setBackground(Color.decode("#202124"));
		checkoutTablePanel.setVisible(true);
		checkoutTablePanel.setLayout(null);
		frame2.add(checkoutTablePanel);
		
		// THIS WILL BE THE HEADER OF THE TABLE
		itemHeader2 = new String[] {"Quantity", "Item Name", "Item Price"};
		// THIS WILL BE THE DEFAULT LAYOUT OF THE TABLE IF THE 2D ARRAY IS EMPTY
		String[][] empty = new String[0][3];
		model2 = new DefaultTableModel(empty, itemHeader2);
		
		scroll2.setBounds(5, 5, 540, 340);
		scroll2.setVisible(false);
		checkoutTablePanel.add(scroll2);
		
		table2.setModel(model2);
		table2.setSize(780, 380);
		table2.setEnabled(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table2.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseReleased(MouseEvent e) {
	    		
	    	}
	    });
		setupTable2();
		scroll2.setViewportView(table2);
		
		// BREAKFOWN PANEL
		breakdownPanel.setBounds(585, 465, 550, 215);
		breakdownPanel.setBackground(Color.decode("#202124"));
		breakdownPanel.setVisible(true);
		breakdownPanel.setLayout(null);
		frame2.add(breakdownPanel);
		
		breakdown.setBounds(5, 5, 540, 205);
		breakdown.setVisible(false);
		breakdown.setFont(new Font("Serif", Font.PLAIN, 14));
		breakdownPanel.add(breakdown);
		
		
		
		
		
		
		frame2.setVisible(true);
	}
	
	
	
	
	
	public static void setupCategoryButton2() {
		
		// DETERMINES THE NUMBER OF PANEL BASED ON THE NUMBER OF CATEGORY
		ArrayList<String> categoryList = categoryTracker();
		double panelPerCategory = Double.valueOf(categoryList.size())/9.0;
		int numberOfPanel = 1;
		numberOfPanel = (panelPerCategory > 1.0 ?  numberOfPanel += 1 : numberOfPanel);
		// INSTANTIATE THE NUMBER OF PANEL ARRAY FOR CATEGORY
		categorySubPanel = new JPanel[numberOfPanel];	
		for (int i = 0; i < numberOfPanel; i++) {
			categorySubPanel[i] = new JPanel();
			categorySubPanel[i].setBounds(5, 1, 110, 500);
			categorySubPanel[i].setBackground(Color.decode("#202124"));
			categorySubPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			categorySubPanel[i].setVisible(true);
			categoriesPanel.add(categorySubPanel[i]);
		}
		// INSTANTIATE THE NUMBER OF BUTTON PER PANEL
		categoryButton = new JButton[categoryList.size()];
		int k = 0;
		
		for (int i = 0; i < categoryList.size(); i++) {
			categoryButton[i] = new JButton(categoryList.get(i));
			categoryButton[i].setMargin(new Insets(5, 5, 5, 5));
			categoryButton[i].setPreferredSize( new Dimension(100, 50));
			categoryButton[i].addActionListener(new ActionListener() {				
				@Override
				public void actionPerformed(ActionEvent e) {
					// GETS THE VALUE/NAME OF THE BUTTON
					String categoryName = ((JButton) e.getSource()).getText();
					// GENERATES AN ARRAYLIST OF ALL THE ITEMS UNDER THE CATEGORYNAME
					ArrayList<Transaction> itemlist = itemsByCategory(categoryName);
					// GETS THE INDEX OF THE CATEGORY BEING VIEWED FOR COMBOBOX USE
					selectedCategoryIndex2 = categoryList.indexOf(categoryName);
					if (itemlist.size() == 0) {
						JOptionPane.showMessageDialog(frame2, "Category is empty", null, JOptionPane.WARNING_MESSAGE);
					}
					itemPanel.removeAll();
					// DETERMINES THE NUMBER OF PANELS BASED ON THE NUMBER OF ITEMS PER CATEGORY
					double panelPerItems = Double.valueOf(itemlist.size())/8.0;
					int numberOfPanel = 1;					
					numberOfPanel = (panelPerItems > 1.0 ?  numberOfPanel += 1 : numberOfPanel);
					// INSTANTIATE THE NUMBER OF PANEL ARRAY FOR ITEMS
					itemSubPanel = new JPanel[numberOfPanel];
					for (int i = 0; i < numberOfPanel; i++) {
						itemSubPanel[i] = new JPanel();
						itemSubPanel[i].setBounds(0, 0, 335, 455);
						itemSubPanel[i].setBackground(Color.decode("#202124"));
						itemSubPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
						itemSubPanel[i].setVisible(true);
						itemPanel.add(itemSubPanel[i]);
					}
					
					itemButton = new JButton[itemlist.size()];
					int k = 0;
					for (int i = 0; i < itemlist.size(); i++) {
						Transaction item = itemlist.get(i);
						itemButton[i] = new JButton("<html>"+item.getname()+" - "+item.getprice()+"<html>");
						itemButton[i].setMargin(new Insets(5, 5, 5, 5));
						itemButton[i].setPreferredSize( new Dimension(160, 1075/10));
						itemButton[i].addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								scroll2.setVisible(true);
								Transaction tr = new Transaction();
//								code, name, quantity, quantity*price, date, getCurrentTransactionNumber(), category, employee
								tr.setQuantity(1.0);
								tr.setcode(item.getcode());
								tr.setname(item.getname());
								tr.setprice(item.getprice());
								tr.setDate(LocalDate.now());
								tr.setCategory(categoryName);
								tr.setEmployee(employee);
								cart.add(tr);
								model2 = new DefaultTableModel(create2dv2(cart), itemHeader2);
								table2.setModel(model2);
								setupTable2();
							}
						});
						nextBtn2.setVisible(false);
						previousBtn2.setVisible(false);
						// SERVES AS COUNTER USED TO DETERMINE WHAT PANEL NUMBER TO ADD THE CATEGORY BUTTON TO
						int[] max = {7, 15, 23, 31, 39, 47};
						if (i > max[k]) {
							nextBtn2.setVisible(true);
							previousBtn2.setVisible(true);
							k++;				
						}
						itemSubPanel[k].add(itemButton[i]);
					}
					frame2.revalidate();
					frame2.repaint();
				}
			});
			nextBtn1.setVisible(false);
			previousBtn1.setVisible(false);
			// SERVES AS COUNTER USED TO DETERMINE WHAT PANEL NUMBER TO ADD THE CATEGORY BUTTON TO
			int[] max = {8, 17, 26, 25, 44, 53};
			if (i > max[k]) {
				nextBtn1.setVisible(true);
				previousBtn1.setVisible(true);
				k++;				
			}
			categorySubPanel[k].add(categoryButton[i]);
		}
	}
	
	
	
	
	
	public static void setupTable2() {
		int tablePreferredWidth = 520;
		double[] percentages = {15, 70, 15};
	    double total = 0;
	    for (int i = 0; i < table2.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	    for (int i = 0; i < table2.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table2.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
	    }
        table2.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 3; i++) {
        	table2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        String[] options = {"1.0", "2.0", "3.0", "4.0", "5.0", "6.0", "7.0", "8.0", "9.0", "10.0"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table2.getSelectedRow();
				if (row != -1) {
                    Transaction tr = cart.get(row);
    				double quantity = Double.parseDouble(table2.getValueAt(row, 0).toString());
    				tr.setQuantity(quantity);
    				double total = quantity * tr.getprice();
    				table2.setValueAt(total, row, 2);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a row!");
                }
			}
		});
        optionsColumn = table2.getColumnModel().getColumn(0);
        optionsColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}
	
	
	
	
	
	public static void setupBelowCategoeriesPanel2() {
		belowCategoriesPanel.setBounds(120, 535, 120, 145);
		belowCategoriesPanel.setBackground(Color.decode("#202124"));
		belowCategoriesPanel.setVisible(true);
		belowCategoriesPanel.setLayout(null);
		frame2.add(belowCategoriesPanel);
		
		nextBtn2.setBounds(220, 5, 110, 40);
		nextBtn2.setMargin(new Insets(5, 5, 5, 5));
		nextBtn2.setVisible(false);
		nextBtn2.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pageCounter1 >= itemSubPanel.length-1) {
				} else {
					itemSubPanel[pageCounter2].setVisible(false);
					pageCounter2++;
					itemSubPanel[pageCounter2].setVisible(true);
				}
			}
		});
		counterPanel.add(nextBtn2);
		
		previousBtn2.setBounds(5, 5, 110, 40);
		previousBtn2.setMargin(new Insets(5, 5, 5, 5));
		previousBtn2.setVisible(false);
		previousBtn2.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pageCounter2 <= 0 ) {
				} else {
					itemSubPanel[pageCounter2].setVisible(false);
					pageCounter2--;
					itemSubPanel[pageCounter2].setVisible(true);
				}
			}
		});
		counterPanel.add(previousBtn2);
	}
	
	
	
	
	private static boolean isDouble(String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
	
	
	
	
	
	public static Object[][] create2dv2(ArrayList<Transaction> bought) {
		String[][] currentdata = new String[0][0];
		
		currentdata = new String[bought.size()][3];
		for (int i = 0; i < bought.size(); i++) {
			Transaction data = bought.get(i);
			currentdata[i][0] = Double.toString(data.getQuantity());
			currentdata[i][1] = data.getname();
			currentdata[i][2] = Double.toString(data.getprice());
		}
		return currentdata;
	}
	
	
	
	
	
	public static void transactionComplete(ArrayList<Transaction> bought) {
		try {
			fw = new FileWriter("sales.txt", true);
			for (int i = 0; i < bought.size(); i++) {
				Transaction tr = bought.get(i);
				fw.write(tr.toString()+"\n");
			}
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public static void setupTaskbarPanel2() {
		taskbarPanel.setBounds(5, 5, 110, 675);
		taskbarPanel.setBackground(Color.decode("#202124"));
		taskbarPanel.setVisible(true);
		taskbarPanel.setLayout(null);
		frame2.add(taskbarPanel);
		
		accountBtn.setBounds(5, 5, 100, 60);
		accountBtn.setMargin(new Insets(5, 5, 5, 5));
		accountBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Logout", "Stay"};
				int choice = JOptionPane.showOptionDialog(frame, "Do you want to logout?:", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		        if (choice == 0) {
		        	frame2.removeAll();
		        	frame2.setVisible(false);
		        	LoginUi l = new LoginUi();
		    		l.loginUi();
		        } else if (choice == 1) {
		        	// DO NOTHING
		        }
			}
		});
		taskbarPanel.add(accountBtn);
	}
	
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	

	
	
}

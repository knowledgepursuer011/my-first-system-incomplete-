package finalProjectv3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class ManagementUi extends LoginUi{
	// LAYOUT
	
	// FRAME
	static JFrame frame2;
	// TASKBAR PANEL
	static JPanel taskbarPanel;
	static JButton manageBtn;
	static JButton reportingBtn;
	static JButton accountBtn;
	// CATEGORIES PANEL
	static JPanel categoriesPanel;
	// BELOW CATEGORIES PANEL
	static JPanel belowCategoriesPanel;
	static JButton nextBtn1;
	static JButton previousBtn1;
	// TABLE PANEL
	static JLabel selectCategoryMessage;
	static JPanel tablePanel;
	static JScrollPane scroll;
	static DefaultTableModel model;
	static JTable table;
	// MANAGEMENT PANEL	
	static JPanel managementPanel;
	// ADD OR EDIT/DELETE CATEGORY OR ITEM BUTTON (first)
	static JButton addOrDeleteCategoryBtn;
	static JButton addOrDeleteItemBtn;
	static JButton backBtn;
	// (↓)	CATEGORY	
	// ADD OR EDIT/DELETE CATEGORY (second)
	static JButton addCategoryBtn;
	static JButton editOrDeletecategoryBtn;
	// (↓)
	// ADD CATEGORY (third)
	static JTextField categoryNameTextField;
	static JButton confirmAddCategoryBtn;
	// EDIT/DELETE CATEGORY (fourth)
	static JComboBox<String> categoriesComboBox;
	static JButton confirmDeleteCategoryBtn;
	static JButton confirmEditCategoryBtn;
	// OR
	// ADD OR EDIT/DELETE ITEM (fifth)
	static JButton addItemBtn;
	static JButton editOrDeleteItemBtn;
	// (↓) ITEM
	// ADD ITEM (sixth)
	static JTextField itemCodeTextField;
	static JTextField itemNameTextField;
	static JTextField itemPriceTextField;
	static JButton confirmAddItemBtn;
	// EDIT/DELETE ITEM (seventh)
	// reuse text field from add item
	static JLabel selectItemMessage;
	static JButton confirmDeleteItemBtn;
	static JButton confirmUpdateItemBtn;
	// BUTTON FOR GENERATING ITEMS IN TABLE
	static JPanel[] categorySubPanel;	
	static JButton[] categoryButton;
	static String[] itemHeader;
	// COUNTER FOR CATEGORY PANEL
	static int pageCounter1 = 0;
	// CATEGORY INDEX CURRENTLY BEING DISPLAYED IN TABLE
	static int selectedCategoryIndex = 0;
	// FOR METHODS
	
	
	
	public void managementUi() {
		
		
		// LAYOUT INSTANTIATION
		// FRAME
		frame2 = new JFrame();
		// TASKBAR PANEL
		taskbarPanel = new JPanel();
		manageBtn = new JButton("Manage");
		reportingBtn = new JButton("Reporting");
		accountBtn = new JButton("Account");
		// CATEGORIES PANEL
		categoriesPanel = new JPanel();
		// BELOW CATEGORIES PANEL
		belowCategoriesPanel = new JPanel();
		nextBtn1 = new JButton("Next");
		previousBtn1 = new JButton("Previous");
		// TABLE PANEL
		selectCategoryMessage = new JLabel("Click a category to display Items");
		tablePanel = new JPanel();
		scroll = new JScrollPane();
		model = new DefaultTableModel();
		table = new JTable();
		// MANAGEMENT PANEL
		managementPanel = new JPanel();
		// ADD OR EDIT/DELETE CATEGORY OR ITEM BUTTON (first)
		addOrDeleteCategoryBtn = new JButton("Manage Categories");
		addOrDeleteItemBtn = new JButton("Manage Items");
		backBtn = new JButton("Back");
		// (↓)
		// ADD OR EDIT/DELETE CATEGORY (second)
		addCategoryBtn = new JButton("Add a category");
		editOrDeletecategoryBtn = new JButton("Edit/delete a Category");
		// (↓)
		// ADD CATEGORY (third)
		categoryNameTextField = new JTextField();
		confirmAddCategoryBtn = new JButton("Add Category");
		// EDIT/DELETE CATEGORY (fourth)
		categoriesComboBox = new JComboBox<String>();
		confirmDeleteCategoryBtn = new JButton("Delete Category");
		confirmEditCategoryBtn = new JButton("Edit Category");
		// OR
		// ADD OR EDIT/DELETE ITEM (fifth)
		addItemBtn = new JButton("Add an Item");
		editOrDeleteItemBtn = new JButton("Edit/delete an Item");
		// (↓)
		// ADD ITEM (sixth)
		itemCodeTextField = new JTextField();
		itemNameTextField = new JTextField();
		itemPriceTextField = new JTextField();
		confirmAddItemBtn = new JButton("Add Item");
		// EDIT/DELETE ITEM
		// reuse text field from add item (seventh)
		selectItemMessage = new JLabel("Click table to choose what item to edit or delete");
		confirmDeleteItemBtn = new JButton("Delete Item");
		confirmUpdateItemBtn = new JButton("Edit Item");
		
		
		
		
		// LAYOUT SETUP
		// FRAME
		mainFrame();
		
		// TASKBAR PANELs
		setupTaskbarPanel();
		
		// BELOW CATEGORIES PANEL
		// execute first to setup button for the categories panel use
		setupBelowCategoeriesPanel();
				
		// CATEGORIES PANEL
		setupCategoriesPanel();
		setupCategoryButton();
		
		// TABLE PANEL		
		tablePanel.setBounds(245, 5, 850, 470);
		tablePanel.setBackground(Color.decode("#202124"));
		tablePanel.setVisible(true);
		tablePanel.setLayout(null);
		frame2.add(tablePanel);
		
		selectCategoryMessage.setBounds(310,  220,  210,  30);
		selectCategoryMessage.setVisible(true);
		selectCategoryMessage.setFont(new Font("Bold", Font.PLAIN, 14));
		selectCategoryMessage.setForeground(Color.white);
		tablePanel.add(selectCategoryMessage);
		
		// THIS WILL BE THE HEADER OF THE TABLE
		itemHeader = new String[] {"Item Code", "Item Name", "Item Price"};
		// THIS WILL BE THE DEFAULT LAYOUT OF THE TABLE IF THE 2D ARRAY IS EMPTY
		String[][] empty = new String[0][3];
		model = new DefaultTableModel(empty, itemHeader);
		
		scroll.setBounds(5, 30, 800, 400);
		scroll.setVisible(false);
		tablePanel.add(scroll);
		
		table.setModel(model);
		table.setSize(780, 380);
		table.setEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseReleased(MouseEvent e) {	
				if (!table.getSelectionModel().isSelectionEmpty()) {
					if (selectItemMessage.isVisible()) {
						selectItemMessage.setVisible(false);
					}
	    			System.out.println("1T");
	    			int row = table.getSelectedRow();
	    			String[][] data = create2d(itemsByCategory(categoryTracker().get(selectedCategoryIndex)));
	    			categoriesComboBox.setSelectedIndex(selectedCategoryIndex);
	    			selectItemMessage.setVisible(false);
					categoriesComboBox.setVisible(true);
					itemCodeTextField.setVisible(true);
					itemNameTextField.setVisible(true);
					itemPriceTextField.setVisible(true);
					confirmDeleteItemBtn.setVisible(true);
					confirmUpdateItemBtn.setVisible(true);
	    			itemCodeTextField.setText(data[row][0]);
	    			itemNameTextField.setText(data[row][1]);
	    			itemPriceTextField.setText(data[row][2]);
	    		} else if (table.getSelectionModel().isSelectionEmpty()) {	  
	    			System.out.println("2T");
	    		}
	    	}
	    });
		scroll.setViewportView(table);
		
		// MANAGEMENT PANEL
		managementPanel.setBounds(245, 480, 850, 200);
		managementPanel.setLayout(null);
		managementPanel.setVisible(true);
		managementPanel.setBackground(Color.decode("#202124"));
		frame2.add(managementPanel);
		
		backBtn.setBounds(740, 150, 100, 40);
		backBtn.setVisible(true);
		backBtn.setMargin(new Insets(5, 5, 5, 5));
		backBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (scroll.isVisible() && backBtn.isVisible() && addOrDeleteCategoryBtn.isVisible() && addOrDeleteItemBtn.isVisible()) {
					scroll.setVisible(false);												// first
					addOrDeleteCategoryBtn.setVisible(false);
					addOrDeleteItemBtn.setVisible(false);
					selectCategoryMessage.setVisible(true);
				} else if (addCategoryBtn.isVisible() && editOrDeletecategoryBtn.isVisible()) { 	// second
					addCategoryBtn.setVisible(false);
					editOrDeletecategoryBtn.setVisible(false);
					addOrDeleteCategoryBtn.setVisible(true);
					addOrDeleteItemBtn.setVisible(true);
				} else if (categoryNameTextField.isVisible() && confirmAddCategoryBtn.isVisible()) { // third
					categoryNameTextField.setVisible(false);
					categoryNameTextField.setText("");
					confirmAddCategoryBtn.setVisible(false);
					addCategoryBtn.setVisible(true);
					editOrDeletecategoryBtn.setVisible(true);
				} else if (categoriesComboBox.isVisible() && categoryNameTextField.isVisible() && confirmDeleteCategoryBtn.isVisible() && confirmEditCategoryBtn.isVisible()) {
					categoriesComboBox.setVisible(false);										// fourth
					categoriesComboBox.setSelectedIndex(-1);
					categoryNameTextField.setVisible(false);
					categoryNameTextField.setText("");
					confirmDeleteCategoryBtn.setVisible(false);
					confirmEditCategoryBtn.setVisible(false);
					addCategoryBtn.setVisible(true);
					editOrDeletecategoryBtn.setVisible(true);
				} else if (addItemBtn.isVisible() && editOrDeleteItemBtn.isVisible()) { 	// fifth
					addItemBtn.setVisible(false);
					editOrDeleteItemBtn.setVisible(false);
					addOrDeleteCategoryBtn.setVisible(true);
					addOrDeleteItemBtn.setVisible(true);
				} else if (itemCodeTextField.isVisible() && itemNameTextField.isVisible() && itemPriceTextField.isVisible() && confirmAddItemBtn.isVisible() && categoriesComboBox.isVisible()) {
					categoriesComboBox.setVisible(false);							 	// sixth
					itemCodeTextField.setVisible(false);
					itemNameTextField.setVisible(false);
					itemPriceTextField.setVisible(false);
					confirmAddItemBtn.setVisible(false);
					categoriesComboBox.setSelectedIndex(-1);
					itemCodeTextField.setText("");
					itemNameTextField.setText("");
					itemPriceTextField.setText("");
					addItemBtn.setVisible(true);
					editOrDeleteItemBtn.setVisible(true);
				} else if (itemCodeTextField.isVisible() && itemNameTextField.isVisible() && itemPriceTextField.isVisible() && confirmDeleteItemBtn.isVisible() && confirmUpdateItemBtn.isVisible() && categoriesComboBox.isVisible()) {
					categoriesComboBox.setVisible(false);
					itemCodeTextField.setVisible(false);							// seventh
					itemNameTextField.setVisible(false);
					itemPriceTextField.setVisible(false);
					confirmDeleteItemBtn.setVisible(false);
					confirmUpdateItemBtn.setVisible(false);
					table.setEnabled(false);
					table.clearSelection();
					addItemBtn.setVisible(true);
					categoriesComboBox.setSelectedIndex(-1);
					itemCodeTextField.setText("");
					itemNameTextField.setText("");
					itemPriceTextField.setText("");
					editOrDeleteItemBtn.setVisible(true);
				} else if (selectItemMessage.isVisible()) {				// if text is visible in seventh
					selectItemMessage.setVisible(false);
					table.setEnabled(false);
					table.clearSelection();
					addItemBtn.setVisible(true);
					editOrDeleteItemBtn.setVisible(true);
				}
			}
		});
		managementPanel.add(backBtn);
		
		// ADD OR EDIT/DELETE CATEGORY OR ITEM BUTTON (first)
		addOrDeleteCategoryBtn.setBounds(10, 10, 250, 40);
		addOrDeleteCategoryBtn.setVisible(false);
		addOrDeleteCategoryBtn.setMargin(new Insets(5, 5, 5, 5));
		addOrDeleteCategoryBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addOrDeleteCategoryBtn.setVisible(false);
				addOrDeleteItemBtn.setVisible(false);
				addCategoryBtn.setVisible(true);
				editOrDeletecategoryBtn.setVisible(true);
			}
		});
		managementPanel.add(addOrDeleteCategoryBtn);
		
		addOrDeleteItemBtn.setBounds(10, 60, 250, 40);
		addOrDeleteItemBtn.setVisible(false);
		addOrDeleteItemBtn.setMargin(new Insets(5, 5, 5, 5));
		addOrDeleteItemBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addOrDeleteCategoryBtn.setVisible(false);
				addOrDeleteItemBtn.setVisible(false);
				addItemBtn.setVisible(true);
				editOrDeleteItemBtn.setVisible(true);
			}
		});
		managementPanel.add(addOrDeleteItemBtn);
		
		// (↓)
		// ADD OR EDIT/DELETE CATEGORY (second)
		addCategoryBtn.setBounds(10, 10, 250, 40);
		addCategoryBtn.setVisible(false);
		addCategoryBtn.setMargin(new Insets(5, 5, 5, 5));
		addCategoryBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addCategoryBtn.setVisible(false);
				editOrDeletecategoryBtn.setVisible(false);
				categoryNameTextField.setBounds(10, 10, 250, 40);
				categoryNameTextField.setVisible(true);
				confirmAddCategoryBtn.setVisible(true);
			}
		});
		managementPanel.add(addCategoryBtn);
		
		editOrDeletecategoryBtn.setBounds(10, 60, 250, 40);
		editOrDeletecategoryBtn.setVisible(false);
		editOrDeletecategoryBtn.setMargin(new Insets(5, 5, 5, 5));
		editOrDeletecategoryBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addCategoryBtn.setVisible(false);
				editOrDeletecategoryBtn.setVisible(false);
				categoriesComboBox.setVisible(true);
				categoryNameTextField.setBounds(115, 10, 250, 40);
				categoryNameTextField.setVisible(true);
				confirmDeleteCategoryBtn.setVisible(true);
				confirmEditCategoryBtn.setVisible(true);				
			}
		});
		managementPanel.add(editOrDeletecategoryBtn);
		
		// ADD CATEGORY (third)
		categoryNameTextField.setBounds(10, 10, 250, 40);
		categoryNameTextField.setVisible(false);
		managementPanel.add(categoryNameTextField);
		
		confirmAddCategoryBtn.setBounds(10, 60, 250, 40);
		confirmAddCategoryBtn.setVisible(false);
		confirmAddCategoryBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String categoryname = "";
				if (categoryNameTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame2, "Please fill out the text box", "Input Error", JOptionPane.WARNING_MESSAGE);
				} else {
					int response = JOptionPane.showConfirmDialog(frame2, "Are you sure you want to add this as a new category?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						categoryname = categoryNameTextField.getText();
						// GENERATES A RAW DATA OF FILE. CATEGORY AND ITEMS IN ONE LINE
						ArrayList<String> alldata = rawData();
						// ADDS THE NEW CATEGORY TO THE FILE AS A NEW LINE
						alldata.add(categoryname+":");
						writer(alldata, false, 2);
						categoryNameTextField.setText("");
						// REMOVES THE COMPONENTS
						categoriesPanel.removeAll();
						managementPanel.remove(categoriesComboBox);
						// GENERATES THE COMPONENTS AGAIN WITH THE NEW DATA
						setupCategoryButton();
						setupCombobox();
						frame2.revalidate();
						frame2.repaint();
					} else if (response == JOptionPane.NO_OPTION) {
			        }
				}
			}
		});
		managementPanel.add(confirmAddCategoryBtn);
			
		// EDIT/DELETE CATEGORY (fourth)				
		setupCombobox();
		categoriesComboBox.setVisible(false);
		
		confirmDeleteCategoryBtn.setBounds(10, 60, 200, 40);
		confirmDeleteCategoryBtn.setVisible(false);
		confirmDeleteCategoryBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (categoriesComboBox.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(frame2, "Please select the category to delete from the dropdown", "Input Error", JOptionPane.WARNING_MESSAGE);
				} else {
					int response = JOptionPane.showConfirmDialog(frame2, "Are you sure you want to delete the category and all its items?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						// GENERATES A RAW DATA OF FILE. CATEGORY AND ITEMS IN ONE LINE
						ArrayList<String> alldata = rawData();
						// GETS THE INDEX OF THE CATEFORY TO BE DELETED
						int category = categoriesComboBox.getSelectedIndex();
						alldata.remove(category);
						writer(alldata, false, 2);
						// REMOVES THE COMPONENTS
						categoriesPanel.removeAll();
						managementPanel.remove(categoriesComboBox);
						// GENERATES THE COMPONENTS AGAIN WITH THE NEW DATA
						setupCombobox();
						categoriesComboBox.setSelectedIndex(-1);
						setupCategoryButton();
						frame2.revalidate();
						frame2.repaint();
					}			
				}
			}
		});
		managementPanel.add(confirmDeleteCategoryBtn);
		
		confirmEditCategoryBtn.setBounds(215, 60, 200, 40);
		confirmEditCategoryBtn.setVisible(false);
		confirmEditCategoryBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (categoryNameTextField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame2, "Please select the category to edit from the dropdown and enter the new name in the text box.", "Input Error", JOptionPane.WARNING_MESSAGE);
				} else {
					int response = JOptionPane.showConfirmDialog(frame2, "Continue renaming the category?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						// GENERATES A RAW DATA OF FILE. CATEGORY AND ITEMS IN ONE LINE
						ArrayList<String> alldata = rawData();
						// GETS THE INDEX OF THE CATEFORY TO BE DELETED
						int categoryindex = categoriesComboBox.getSelectedIndex();
						String[] str = alldata.get(categoryindex).split(":");
						// GETS THE NEW CATEGORY NAME
						String category = categoryNameTextField.getText();
						// CONCATENATES THE ITEMS BACK INTO ONE STRING
						for (int i = 1; i <str.length; i++) {
							category = category+":"+str[i];
						}
						category += ":";
						alldata.set(categoryindex, category);
						writer(alldata, false, 2);
						// REMOVES THE COMPONENTS
						categoriesPanel.removeAll();
						managementPanel.remove(categoriesComboBox);
						// GENERATES THE COMPONENTS AGAIN WITH THE NEW DATA
						setupCategoryButton();
						setupCombobox();
						frame2.revalidate();
						frame2.repaint();
						categoriesComboBox.setSelectedIndex(-1);
						categoryNameTextField.setText("");
			        }
				}			
			}
		});
		managementPanel.add(confirmEditCategoryBtn);
		
		// OR
		// ADD OR EDIT/DELETE ITEM (fifth)
		addItemBtn.setBounds(10, 10, 200, 40);
		addItemBtn.setVisible(false);
		addItemBtn.setMargin(new Insets(5, 5, 5, 5));		
		addItemBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addItemBtn.setVisible(false);
				editOrDeleteItemBtn.setVisible(false);
				categoriesComboBox.setVisible(true);
				itemCodeTextField.setVisible(true);
				itemNameTextField.setVisible(true);
				itemPriceTextField.setVisible(true);
				confirmAddItemBtn.setVisible(true);
			}
		});
		managementPanel.add(addItemBtn);
		
		editOrDeleteItemBtn.setBounds(10, 60, 200, 40);
		editOrDeleteItemBtn.setVisible(false);
		editOrDeleteItemBtn.setMargin(new Insets(5, 5, 5, 5));		
		editOrDeleteItemBtn.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				addItemBtn.setVisible(false);
				editOrDeleteItemBtn.setVisible(false);
				
				selectItemMessage.setVisible(true);
				table.setEnabled(true);
			}
		});
		managementPanel.add(editOrDeleteItemBtn);
		
		// (↓)
		// ADD ITEM (sixth)
		itemCodeTextField.setBounds(115, 10, 100, 40);
		itemCodeTextField.setVisible(false);
		managementPanel.add(itemCodeTextField);
		
		itemNameTextField.setBounds(220, 10, 300, 40);
		itemNameTextField.setVisible(false);
		managementPanel.add(itemNameTextField);
		
		itemPriceTextField.setBounds(525, 10, 200, 40);
		itemPriceTextField.setVisible(false);
		managementPanel.add(itemPriceTextField);
		
		setupCombobox();
		categoriesComboBox.setVisible(false);
		
		confirmAddItemBtn.setBounds(10, 60, 200, 40);
		confirmAddItemBtn.setVisible(false);
		confirmAddItemBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (itemCodeTextField.getText().isEmpty() || itemNameTextField.getText().isEmpty() || itemPriceTextField.getText().isEmpty() || categoriesComboBox.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(frame2, "Please fill in all three text fields and select which category from the dropdown", "Input Error", JOptionPane.WARNING_MESSAGE);
				} else {
					int response = JOptionPane.showConfirmDialog(frame2, "Are you sure you want to add the item?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						String category = (String) categoriesComboBox.getSelectedItem();
						ArrayList<Transaction> itemsbycategory = itemsByCategory(category);
						Transaction product = new Transaction();
						product.setcode(itemCodeTextField.getText());
						product.setname(itemNameTextField.getText());
						product.setprice(Double.parseDouble((String)itemPriceTextField.getText()));
						itemsbycategory.add(product);					
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < itemsbycategory.size(); i++) {
							if (i > 0) {
								sb.append(":");
							}
							sb.append(itemsbycategory.get(i).toString());
						}
						String newdata = category+":"+sb.toString();
						ArrayList<String> alldata = rawData();
						alldata.set(selectedCategoryIndex, newdata);
						writer(alldata, false, 1);
						model = new DefaultTableModel(create2d(itemsByCategory(category)), itemHeader);
						table.setModel(model);
						setupTable();
						itemCodeTextField.setText("");
						itemNameTextField.setText("");
						itemPriceTextField.setText("");
						categoriesComboBox.setSelectedIndex(-1);
					}
				}				
			}
		});
		managementPanel.add(confirmAddItemBtn);
		
		// EDIT/DELETE ITEM (seventh)
		// reuse text field from add item
		selectItemMessage.setBounds(20, 10, 400, 40);
		selectItemMessage.setFont(new Font("Bold", Font.PLAIN, 14));
		selectItemMessage.setForeground(Color.white);
		selectItemMessage.setVisible(false);
		managementPanel.add(selectItemMessage);
		
		confirmDeleteItemBtn.setBounds(10, 60, 200, 40);
		confirmDeleteItemBtn.setVisible(false);
		confirmDeleteItemBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (itemCodeTextField.getText().isEmpty() || itemNameTextField.getText().isEmpty() || itemPriceTextField.getText().isEmpty() || categoriesComboBox.getSelectedIndex() == -1) {
					JOptionPane.showMessageDialog(frame2, "Please fill in all three text fields and select which category from the dropdown", "Input Error", JOptionPane.WARNING_MESSAGE);
				} else {
					int response = JOptionPane.showConfirmDialog(frame2, "Are you sure you want to delete the item?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						int row = table.getSelectedRow();
						ArrayList<String> categories = categoryTracker();
						ArrayList<Transaction> itemsbycategory = itemsByCategory(categories.get(selectedCategoryIndex));
						itemsbycategory.remove(row);
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < itemsbycategory.size(); i++) {
							if (i >= 0) {
								sb.append(":");
							}
							sb.append(itemsbycategory.get(i).toString());
						}
						String newline = categories.get(selectedCategoryIndex)+":"+sb.toString();
						ArrayList<String> alldata = rawData();
						alldata.set(selectedCategoryIndex, newline);
						writer(alldata, false, 2);
						model = new DefaultTableModel(create2d(itemsByCategory(categories.get(selectedCategoryIndex))), itemHeader);
						table.setModel(model);
						setupTable();
						itemCodeTextField.setText("");
						itemNameTextField.setText("");
						itemPriceTextField.setText("");
						categoriesComboBox.setSelectedIndex(-1);
			        }
				}
			}
		});
		managementPanel.add(confirmDeleteItemBtn);
		
		confirmUpdateItemBtn.setBounds(215, 60, 200, 40);
		confirmUpdateItemBtn.setVisible(false);
		confirmUpdateItemBtn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (itemCodeTextField.getText().isEmpty() || itemNameTextField.getText().isEmpty() || itemPriceTextField.getText().isEmpty() || categoriesComboBox.getSelectedIndex() == -1 ) {
					JOptionPane.showMessageDialog(frame2, "Please fill in all three text fields and select which category from the dropdown", "Input Error", JOptionPane.WARNING_MESSAGE);
				} else {
					int response = JOptionPane.showConfirmDialog(frame2, "Do you want to save the following changes?", "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response == JOptionPane.YES_OPTION) {
						int row = table.getSelectedRow();
						ArrayList<String> categories = categoryTracker();
						ArrayList<Transaction> itemsbycategory = itemsByCategory(categories.get(selectedCategoryIndex));
						Transaction product = itemsbycategory.get(row);
						product.setcode(itemCodeTextField.getText());
						product.setname(itemNameTextField.getText());
						product.setprice(Double.parseDouble(itemPriceTextField.getText()));
						StringBuilder sb = new StringBuilder();
						for (int i = 0; i < itemsbycategory.size(); i++) {
							if (i >= 0) {
								sb.append(":");
							}
							sb.append(itemsbycategory.get(i).toString());
						}
						String newline = categories.get(selectedCategoryIndex)+":"+sb.toString();
						ArrayList<String> alldata = rawData();
						alldata.set(selectedCategoryIndex, newline);
						writer(alldata, false, 2);
						model = new DefaultTableModel(create2d(itemsByCategory(categories.get(selectedCategoryIndex))), itemHeader);
						table.setModel(model);
						setupTable();
						itemCodeTextField.setText("");
						itemNameTextField.setText("");
						itemPriceTextField.setText("");
						categoriesComboBox.setSelectedIndex(-1);
			        } else if (response == JOptionPane.NO_OPTION) {
			        }
				}
			}
		});
		managementPanel.add(confirmUpdateItemBtn);
	
		frame2.setVisible(true);			
	}
	
	
	
	
	
	public static void setupTaskbarPanel() {
		taskbarPanel.setBounds(5, 5, 110, 675);
		taskbarPanel.setBackground(Color.decode("#202124"));
		taskbarPanel.setVisible(true);
		taskbarPanel.setLayout(null);
		frame2.add(taskbarPanel);
		
		manageBtn.setBounds(5, 5, 100, 60);
		manageBtn.setMargin(new Insets(5, 5, 5, 5));
		taskbarPanel.add(manageBtn);
		
		reportingBtn.setBounds(5, 70, 100, 60);
		reportingBtn.setMargin(new Insets(5, 5, 5, 5));
		reportingBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				frame2.removeAll();
				frame2.dispose();
				ReportingUi r = new ReportingUi();
				r.reportingUi();
				frame2.revalidate();
				frame2.repaint();
			}
		});
		taskbarPanel.add(reportingBtn);
		
		accountBtn.setBounds(5, 135, 100, 60);
		accountBtn.setMargin(new Insets(5, 5, 5, 5));
		accountBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Logout", "Stay"};
		        int choice = JOptionPane.showOptionDialog(frame, "Do you want to logout?:", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		        if (choice == 0) {
//		        	frame2.removeAll();
//		        	frame2.setVisible(false);
		        	frame2.dispose();
		        	LoginUi l = new LoginUi();
		    		l.loginUi();
		        } else if (choice == 1) {
		        	// DO NOTHING
		        }
			}
		});
		taskbarPanel.add(accountBtn);
	}
	
	
	
	
	
	public static void setupCategoriesPanel() {
		categoriesPanel.setBounds(120, 5, 120, 525);
		categoriesPanel.setBackground(Color.decode("#202124"));
		categoriesPanel.setVisible(true);
		categoriesPanel.setLayout(null);
		frame2.add(categoriesPanel);
	}
	
	
	
	
	
	public static void setupBelowCategoeriesPanel() {
		belowCategoriesPanel.setBounds(120, 535, 120, 145);
		belowCategoriesPanel.setBackground(Color.decode("#202124"));
		belowCategoriesPanel.setVisible(true);
		belowCategoriesPanel.setLayout(null);
		frame2.add(belowCategoriesPanel);
		
		nextBtn1.setBounds(5, 5, 110, 40);
		nextBtn1.setMargin(new Insets(5, 5, 5, 5));
		nextBtn1.setVisible(false);
		nextBtn1.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pageCounter1 >= categorySubPanel.length-1) {
				} else {
					categorySubPanel[pageCounter1].setVisible(false);
					pageCounter1++;
					categorySubPanel[pageCounter1].setVisible(true);
				}
			}
		});
		belowCategoriesPanel.add(nextBtn1);
		
		previousBtn1.setBounds(5, 50, 110, 40);
		previousBtn1.setMargin(new Insets(5, 5, 5, 5));
		previousBtn1.setVisible(false);
		previousBtn1.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				if (pageCounter1 <= 0 ) {
				} else {
					categorySubPanel[pageCounter1].setVisible(false);
					pageCounter1--;
					categorySubPanel[pageCounter1].setVisible(true);
				}
			}
		});
		belowCategoriesPanel.add(previousBtn1);
	}
	
	
	
	
	
	public static void setupCategoryButton(){
		ArrayList<String> categoryList = categoryTracker();
		double panelPerCategory = Double.valueOf(categoryList.size())/9.0;
		int numberOfPanel = 1;
		
		numberOfPanel = (panelPerCategory > 1.0 ?  numberOfPanel += 1 : numberOfPanel);
		categorySubPanel = new JPanel[numberOfPanel];
		
		for (int i = 0; i < numberOfPanel; i++) {
			categorySubPanel[i] = new JPanel();
			categorySubPanel[i].setBounds(5, 1, 110, 500);
			categorySubPanel[i].setBackground(Color.decode("#202124"));
			categorySubPanel[i].setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
			categorySubPanel[i].setVisible(true);
			categoriesPanel.add(categorySubPanel[i]);
		}
				
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
					// GENERATES A 2D ARRAY OF THE ITEMS UNDER THE CATEGORYNAME
					String[][] data = create2d(itemsByCategory(categoryName));
					selectedCategoryIndex = categoryTracker().indexOf(categoryName);
					// ALL PERFORMED ACTIONS WILL BE BASED ON THE CATEGORYINDEX
					if (selectCategoryMessage.isVisible()) {
						if (data.length==0) {
							JOptionPane.showMessageDialog(frame2, "Category is empty", null, JOptionPane.WARNING_MESSAGE);
						}
						System.out.println("1B");
						selectCategoryMessage.setVisible(false);
						scroll.setVisible(true);
						addOrDeleteCategoryBtn.setVisible(true);
						addOrDeleteItemBtn.setVisible(true);
						model = new DefaultTableModel(data, itemHeader);
						table.setModel(model);
						setupTable();						
					} else if (table.isEnabled()) {
						if (data.length==0) {
							JOptionPane.showMessageDialog(frame2, "Category is empty", null, JOptionPane.WARNING_MESSAGE);
						}
						System.out.println("2B");
						categoriesComboBox.setSelectedIndex(categoryList.indexOf(categoryName));
						itemCodeTextField.setText("");
						itemNameTextField.setText("");
						itemPriceTextField.setText("");
						model = new DefaultTableModel(data, itemHeader);
						table.setModel(model);
						setupTable();
					} else if (scroll.isVisible()) {
						if (data.length==0) {
							JOptionPane.showMessageDialog(frame2, "Category is empty", null, JOptionPane.WARNING_MESSAGE);
						}
						System.out.println("3B");
						model = new DefaultTableModel(data, itemHeader);
						table.setModel(model);
						setupTable();
					}
				}
			});
			nextBtn1.setVisible(false);
			previousBtn1.setVisible(false);
			int[] max = {8, 17, 26, 25, 44, 53};
			if (i > max[k]) {
				nextBtn1.setVisible(true);
				previousBtn1.setVisible(true);
				k++;				
			}
			categorySubPanel[k].add(categoryButton[i]);
		}
	}
	
	
	
	
	
	public static void setupCombobox() {
		
		String[] categories = categoryTracker().toArray(new String[categoryTracker().size()]);
		categoriesComboBox = new JComboBox<String>(categories);
		
		categoriesComboBox.setBounds(10, 10, 100, 40);
		categoriesComboBox.setSelectedIndex(-1);
		categoriesComboBox.setVisible(true);
//		categoriesComboBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            	if (categoryNameTextField.isVisible()) {
//            		String selectedItem = (String) categoriesComboBox.getSelectedItem();
//                    categoryNameTextField.setText(selectedItem);
//                    selectedCategoryIndex = categoriesComboBox.getSelectedIndex();
//            	}
//            }
//        });
		managementPanel.add(categoriesComboBox);
	}
	
	
	
	
	
	public static ArrayList<String> rawData() {
		ArrayList<String> alldata = new ArrayList<String>();

		try {
			fr = new FileReader("categoriesAndItems.txt");
			scan = new Scanner (fr);
			
			while (scan.hasNextLine()) {
				alldata.add(scan.nextLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alldata;
	}
	
	
	
	
	
	public static ArrayList<String> categoryTracker() {
		ArrayList<String> categorieslist = new ArrayList<String>();
		try {
			fr = new FileReader("categoriesAndItems.txt");
			scan = new Scanner (fr);
			
			while (scan.hasNextLine()) {
				String[] line = scan.nextLine().split(":");
				categorieslist.add(line[0]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return categorieslist;
		
	}
	
	
	
	
	
	public static ArrayList<Transaction> itemsByCategory(String category) {
        ArrayList<Transaction> items = new ArrayList<>();
        try {
			fr = new FileReader("categoriesAndItems.txt");
			scan = new Scanner(fr);
			
			while (scan.hasNextLine()) {
	            String line = scan.nextLine();
	            // Split by colon to get category and items
	            String[] parts = line.split(":");
	            // Check if this line matches the desired category
	            if (parts[0].equals(category)) {
	                // Process each item after the category
	                for (int i = 1; i < parts.length; i++) {
	                    // Split item attributes by |
	                    String[] attributes = parts[i].split("\\|");
	                    String code = attributes[0];
                        String name = attributes[1];
                        double price = Double.parseDouble(attributes[2]);
                        // Create new Item and set attributes
                        Transaction item = new Transaction();  // Initial empty values
                        item.setcode(code);
                        item.setname(name);
                        item.setprice(price);
                        items.add(item);
	                }
	            }
	        }
			scan.close();
//			for (Products data : items) {
//	        	System.out.println(data);
//			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}
	
	
	
	
	
	public static String[][] create2d(ArrayList<Transaction> itemspercategory) {
//		ArrayList<Products> alldata = itemspercategory;
		String[][] currentdata = new String[0][0];
		
		currentdata = new String[itemspercategory.size()][3];
		for (int i = 0; i < itemspercategory.size(); i++) {
			Transaction data = itemspercategory.get(i);
			currentdata[i][0] = data.getcode();
			currentdata[i][1] = data.getname();
			currentdata[i][2] = Double.toString(data.getprice());
		}
//		for (int i = 0; i < itemspercategory.size(); i++) {
//			for (int j = 0; j < 3; j++) {
//				System.out.println(currentdata2d[i][j]);
//			}
//		}
		return currentdata;
	}
	
	
	
	
	
	public static void setupTable() {
		int tablePreferredWidth = 780;
		double[] percentages = {15, 70, 15};
	    double total = 0;
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	    for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
	    }
        table.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 3; i++) {
        	table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
	
	
	
	
	
	public static void writer(ArrayList<String> temp, boolean status, int choice) {
		try {
			fw = new FileWriter("categoriesAndItems.txt", status);
			
			if (choice == 1) {
				for (int i = 0; i < temp.size(); i++) {
					
					fw.write(temp.get(i)+"\n");
				}
			} else if (choice == 2) {
				for (int i = 0; i < temp.size(); i++) {
					
					fw.write(temp.get(i)+"\n");
				}
			}
			fw.flush();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void mainFrame() {	
		frame2.setBounds(250, 100, 1155, 775);
		frame2.getContentPane().setBackground(Color.black);
		frame2.setLayout(null);
		frame2.setResizable(false);
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}

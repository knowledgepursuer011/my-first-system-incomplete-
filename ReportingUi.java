package finalProjectv3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
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

public class ReportingUi extends CashierUi {
	// LAYOUT
	// FRAME (reuse from managementUi)
	// TASKBAR PANEL
	// MAIN REPORTING TABLE
	static JPanel reportingTablePanel;
	static JScrollPane scroll3;
	static DefaultTableModel model3;
	static JTable table3;
	static String[] reportingHeader = {"Transaction Number(TRN)", "Date", "Total", "Employee ID"};
	// MANAGE REPORTING PANEL
	static JPanel manageReportingPanel;
	static JTextField trnField;
	static JButton viewBtn;
	// SUMMARY PANEL
	static JPanel summaryPanel;
	static JTextArea transaction;
	
	static ArrayList<Transaction> bought;
	
	
	public void reportingUi() {
		bought = new ArrayList<>();
		salesToArray();
		// LAYOUT
		// FRAME (reuse from managementUi)
		frame2 = new JFrame();
		// TASKBAR PANEL
		taskbarPanel = new JPanel();
		manageBtn = new JButton("Manage");
		reportingBtn = new JButton("Reporting");
		accountBtn = new JButton("Account");
		// MAIN REPORTING TABLE
		reportingTablePanel = new JPanel();
		scroll3 = new JScrollPane();
		model3 = new DefaultTableModel();
		table3 = new JTable();
		// MANAGE REPORTING PANEL
		manageReportingPanel = new JPanel();
		trnField = new JTextField();
		viewBtn = new JButton("View Transaction");
		// SUMMARY PANEL
		summaryPanel = new JPanel();
		transaction = new JTextArea();
		
		
		
		
		// LAYOUT
		// FRAME (reuse from managementUi)
		mainFrame();
		
		// TASKBAR PANEL
		setupTaskbarPanel3();

		// MAIN REPORTING TABLE
		reportingTablePanel.setBounds(350, 5, 785, 400);
		reportingTablePanel.setBackground(Color.decode("#202124"));
		reportingTablePanel.setLayout(null);
		reportingTablePanel.setVisible(true);
		frame2.add(reportingTablePanel);
		
		scroll3.setBounds(5, 10, 775,380);
		scroll3.setVisible(true);
		reportingTablePanel.add(scroll3);
		
		model3 = new DefaultTableModel(createTableData(bought), reportingHeader);
		table3 = new JTable(model3);
		table3.setVisible(true);
		table3.setEnabled(true);
		table3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = table3.getSelectedRow();
				String TRN = table3.getValueAt(row, 0).toString();
				trnField.setText(TRN);
//				for (int i = 0; i < summary.size(); i++) {
//					System.out.println(summary.get(i));
//				}
			}
		});
		scroll3.setViewportView(table3);
		setupTable3();
		
		// MANAGE REPORTING PANEL
		manageReportingPanel.setBounds(120, 5, 225, 500);
		manageReportingPanel.setBackground(Color.decode("#202124"));
		manageReportingPanel.setLayout(null);
		manageReportingPanel.setVisible(true);
		frame2.add(manageReportingPanel);
		
		trnField.setBounds(10, 50, 200, 50);
		trnField.setVisible(true);
		manageReportingPanel.add(trnField);
		
		viewBtn.setBounds(10, 110, 150, 40);
		viewBtn.setVisible(true);
		viewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (trnField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(frame2, "Please choose what to transaction to view from the table", "No item selected", JOptionPane.WARNING_MESSAGE);
				} else {
					transaction.setVisible(true);
					int row = table3.getSelectedRow();
					String TRN = table3.getValueAt(row, 0).toString();
					trnField.setText(TRN);
					Transaction tr = new Transaction();
					ArrayList<String> summary = tr.getTransactionSummary(bought, TRN);
					String text = String.join("\n", summary);
					transaction.setText(text);
				}
			}
		});
		manageReportingPanel.add(viewBtn);
		
		// SUMMARY PANEL
		summaryPanel.setBounds(350, 410, 785, 300);
		summaryPanel.setBackground(Color.decode("#202124"));
		summaryPanel.setLayout(null);
		summaryPanel.setVisible(true);
		frame2.add(summaryPanel);
		
		transaction.setBounds(20, 10, 600, 280);
		transaction.setVisible(false);
		transaction.setFont(new Font("Serif", Font.PLAIN, 16));
		summaryPanel.add(transaction);
		
		
		
		
		
		
		frame2.setVisible(true);
	}
	
	
	
	
	
	public static ArrayList<Transaction> salesToArray() {
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("sales.txt"));
			String line;
			while ((line = br.readLine()) != null) {
				// SPLITS SALES LINE BY |
	            String[] parts = line.split("\\|");
	            Transaction tr = new Transaction();
//	            code, name, quantity, quantity*price, date, getCurrentTransactionNumber(), category, employee
				tr.setcode(parts[0]);										// code
				tr.setname(parts[1]);										// name
				tr.setQuantity(Double.parseDouble(parts[2].toString()));	// quantity
	            tr.setprice(Double.parseDouble(parts[3].toString()));		// total
	            tr.setDate(LocalDate.parse(parts[4]));						// date
	            tr.setTransactionNumber(parts[5]);							// TRN
	            tr.setCategory(parts[6]);									// category
	            tr.setEmployee(parts[7]);									// employee
	            bought.add(tr);
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
//		for (Transaction data : bought) {
//        	System.out.println(data);
//		}
		return bought;
	}
	
	
	
	
	
	public static Object[][] createTableData(ArrayList<Transaction> transactions) {
        // Group by TRN using a Map
//        Map<String, ArrayList<Transaction>> grouped = new HashMap<>();
//        for (Transaction t : transactions) {
//            String trn = t.getTransactionNumber();
//            if (!grouped.containsKey(trn)) {
//                grouped.put(trn, new ArrayList<>());
//            }
//            grouped.get(trn).add(t);
//        }
        
        Map<String, ArrayList<Transaction>> grouped = new HashMap<>();
        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            String trn = t.getTransactionNumber();
            if (!grouped.containsKey(trn)) {
                grouped.put(trn, new ArrayList<>());
            }
            grouped.get(trn).add(t);
        }

        // Build 2D array
        Object[][] data = new Object[grouped.size()][4];
        int row = 0;
        for (String trn : grouped.keySet()) {
            ArrayList<Transaction> trnItems = grouped.get(trn);
            double totalPrice = 0;
            for (Transaction t : trnItems) {
                totalPrice += t.getprice(); // Sum price directly (already price * quantity)
            }
            Transaction first = trnItems.get(0);
            data[row][0] = trn;              // TRN
            data[row][1] = first.getDate();  // Date
            data[row][2] = totalPrice;       // Total Price
            data[row][3] = first.getEmployee(); // Category (first one)
            row++;
        }
        return data;
    }
	
	
	
	
	
	public static void setupTaskbarPanel3() {
		taskbarPanel.setBounds(5, 5, 110, 675);
		taskbarPanel.setBackground(Color.decode("#202124"));
		taskbarPanel.setVisible(true);
		taskbarPanel.setLayout(null);
		frame2.add(taskbarPanel);
		
		manageBtn.setBounds(5, 5, 100, 60);
		manageBtn.setMargin(new Insets(5, 5, 5, 5));
		manageBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				frame2.removeAll();
				frame2.dispose();
				ManagementUi m = new ManagementUi();
				m.managementUi();
				frame2.revalidate();
				frame2.repaint();
			}
		});
		taskbarPanel.add(manageBtn);
		
		reportingBtn.setBounds(5, 70, 100, 60);
		reportingBtn.setMargin(new Insets(5, 5, 5, 5));
		taskbarPanel.add(reportingBtn);
		
		accountBtn.setBounds(5, 135, 100, 60);
		accountBtn.setMargin(new Insets(5, 5, 5, 5));
		accountBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object[] options = {"Logout", "Stay"};
				int choice = JOptionPane.showOptionDialog(frame, "Do you want to logout?:", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);		        if (choice == 0) {
//					frame2.removeAll();
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
	
	
	
	
	
	public static void setupTable3() {
		int tablePreferredWidth = 780;
		double[] percentages = {35, 25, 15, 25};
	    double total = 0;
	    for (int i = 0; i < table3.getColumnModel().getColumnCount(); i++) {
	        total += percentages[i];
	    }
	    for (int i = 0; i < table3.getColumnModel().getColumnCount(); i++) {
	        TableColumn column = table3.getColumnModel().getColumn(i);
	        column.setPreferredWidth((int) (tablePreferredWidth * (percentages[i] / total)));
	    }
	    table3.setRowHeight(40);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 4; i++) {
        	table3.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

package finalProjectv3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Transaction {
	
	private double quantity;
	private String code;
	private String name;
	private double price;
	private LocalDate date;
    private String category;
    private long lastTransactionNumber;
    private String transactionNumber;
    private String employee;
    
    
	public Transaction() {
		this.lastTransactionNumber = loadLastTransactionNumber();
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	
	public void setcode(String code) {
		this.code = code;
	}
	
	public void setname(String name) {
		this.name = name;
	}
	
	public void setprice(double price) {
		this.price = price;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	
	public String getEmployee() {
		return employee;
	}
	
	
	
	
	
	
	
	public double getQuantity() {
		return quantity;
	}
	
	public String getcode() {
		return this.code;
	}
	
	public String getname() {
		return this.name;
	}
	
	public double getprice() {
		return this.price;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public String getCategory() {
		return category;
	}
	
	public String getTransactionNumber() {
		return transactionNumber;
	}
	
	public String getCurrentTransactionNumber() {
		return String.format("TRN%09d", lastTransactionNumber);
	}
	
	public void setEmployee(String employee) {
		this.employee = employee;
	}
	
	
	
	
	
	private long loadLastTransactionNumber() {
		lastTransactionNumber = 0;
		try {
			FileReader fr = new FileReader("last_trn.txt");
			Scanner scan = new Scanner(fr);
			String line = scan.nextLine();
			lastTransactionNumber = Long.parseLong(line.substring(3));
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lastTransactionNumber;
	}
	
	
	
	
	
	private String getNextTransactionNumber() {
		lastTransactionNumber++;
		return String.format("TRN%09d", lastTransactionNumber);
	}
	
	
	
	
	
	public void addTransaction() {
        String trn = getNextTransactionNumber(); 
        try {
			FileWriter fw = new FileWriter("last_trn.txt", false);
			fw.write(trn);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	
	
	
	
	public ArrayList<String> getTransactionSummary(ArrayList<Transaction> bought, String TRN) {
		ArrayList<String> summary = new ArrayList<>();
		String employee = "";
		summary.add("Category | Code | Name | Quantity | Price");
		for (int i = 0; i < bought.size(); i++) {
			Transaction tr = bought.get(i);
			if (TRN.equalsIgnoreCase(tr.getTransactionNumber())) {
				
				String line = tr.getCategory()+" | "+tr.getcode()+" | "+tr.getname()+" | "+tr.getQuantity()+" | "+tr.getprice();
				summary.add(line);
				employee = tr.getEmployee();
			}
		}
		summary.add("Total: "+Double.toString(getTotal(bought, TRN)));
		summary.add("Emlpoyee: "+employee);
		return summary;
	}
	
	
	
	
	
	private double getTotal(ArrayList<Transaction> bought, String TRN) {
		double total = 0;
		for (int i = 0; i < bought.size(); i++) {
			Transaction tr = bought.get(i);
			if (TRN.equalsIgnoreCase(tr.getTransactionNumber())) {
				total += tr.getprice();
			}
		}
		return total;
	}
	
	
	
	
	
	@Override
    public String toString() {
        return String.format("%s|%s|%.2f|%.2f|%s|%s|%s|%s", code, name, quantity, quantity*price, date, getCurrentTransactionNumber(), category, employee);
    }

	

	

	

	



	



	
    
	
	
	
	
}

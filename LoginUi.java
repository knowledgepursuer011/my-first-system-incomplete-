package finalProjectv3;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginUi {
    
	static JFrame frame;
	static JLabel username;
	static JLabel password;
	static JTextField usernameField;
	static JPasswordField passwordField;
	static JButton login;
	static JButton register;
	
	static FileWriter fw;
	static FileReader fr;
	static Scanner scan;
	
	public void loginUi() {
		
		frame = new JFrame();
		username = new JLabel("Username:");
		password = new JLabel("Password:");
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		login = new JButton("Login");
		register = new JButton("Register");
		
		
		
		frame.setBounds(700, 300, 500, 300);
		frame.setResizable(false);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		username.setBounds(20, 10, 70, 40);
        frame.add(username);
		
        password.setBounds(20, 60, 70, 40);
        frame.add(password);
		
        usernameField.setBounds(100, 10, 200, 40);
        frame.add(usernameField);
        
        passwordField.setBounds(100, 60, 200, 40);
        frame.add(passwordField);
		
        login.setBounds(20, 120, 100, 30);
        login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
        frame.add(login);
        
        register.setBounds(140, 120, 100, 30);
        register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				register();
			}
		});
        frame.add(register);
        
        
        
        
		frame.setVisible(true);
	}
	
	
	
	
	
	public static void register() {
		String access = "";
		String username = usernameField.getText().trim();
		String password = new String(passwordField.getPassword()).trim();
		if (username.isEmpty() || password.isEmpty()) {
			access = "blankField"; // blank either user or pass
		} else {
			try {
				fr = new FileReader("login.txt");
				scan = new Scanner(fr);
				while (scan.hasNextLine()) {
					String[] line = scan.nextLine().split("\\|");
					if (line[0].equals(username)) {
						access = "Exists";
						break;
					} else {
						access = "registered";
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (access.equals("blankField")) {
			JOptionPane.showMessageDialog(frame, "Username and Password fields are required", "Register Input Error", JOptionPane.WARNING_MESSAGE);
		} else if (access.equals("Exists")) {
			JOptionPane.showMessageDialog(frame, "Username already exists", "Register Invalid Input", JOptionPane.WARNING_MESSAGE);
		} else if (access.equalsIgnoreCase("") || access.equals("registered")) {
			Object[] options = {"Employee", "Administrator"};
	        int choice = JOptionPane.showOptionDialog(frame, "Select your role:", "Role Selection", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	        try {
	        	fw = new FileWriter("login.txt", true);
	        	if (choice == 0) {
	        		fw.write(username+"|"+password+"Employee"+"\n");
	        		JOptionPane.showMessageDialog(frame, "Registered Successfully");
					usernameField.setText("");
					passwordField.setText("");
		        } else if (choice == 1) {
		        	fw.write(username+"|"+password+"Administrator"+"\n");
		        	JOptionPane.showMessageDialog(frame, "Registered Successfully");
					usernameField.setText("");
					passwordField.setText("");
		        }
				fw.flush();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	
	
	
	
	public static void login() {
		String access = "";
		String username = usernameField.getText().trim();
		String password = new String(passwordField.getPassword()).trim();
		if (username.isEmpty() || password.isEmpty()) {
			access = "blankField"; // blank either user or pass
		} else {
			try {
				fr = new FileReader("login.txt");
				scan = new Scanner(fr);
				while (scan.hasNextLine()) {
					String[] line = scan.nextLine().split("\\|");
					if (line[0].equals(username)) {
						if (line[1].equals(password)) {
							access = line[2]; // logged in successfully
							break;
						} else {
							access = "incorrectPasswword"; // password is incorrect
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		if (access.equals("")) {
			JOptionPane.showMessageDialog(frame, "Username does not exist", "Login Invalid Input", JOptionPane.WARNING_MESSAGE);
		} else if (access.equalsIgnoreCase("blankField")) {
			JOptionPane.showMessageDialog(frame, "Username and Password fields are required", "Login Input Error", JOptionPane.WARNING_MESSAGE);
		} else if (access.equalsIgnoreCase("incorrectPasswword")) {
			JOptionPane.showMessageDialog(frame, "Password is Incorrect", "Login Invalid Input", JOptionPane.WARNING_MESSAGE);
		} else if (access.equalsIgnoreCase("Employee")) {
//			frame.setVisible(false);
			frame.dispose();
			CashierUi c = new CashierUi();
			c.cashierUi(username);
		} else if (access.equalsIgnoreCase("Administrator")) {
			frame.dispose();
//			frame.setVisible(false);
			ManagementUi m = new ManagementUi();
			m.managementUi();
		}
		
	}
	
	
	
	
	
	public static void main(String[] args) {
		LoginUi l = new LoginUi();
		l.loginUi();
	}
	
	
	
	
	
	
	
	
	
	
	
}
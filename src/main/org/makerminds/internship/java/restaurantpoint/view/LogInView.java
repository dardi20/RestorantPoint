package org.makerminds.internship.java.restaurantpoint.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;

import org.makerminds.internship.java.restaurantpoint.controller.LoginController;
import org.makerminds.internship.java.restaurantpoint.model.User;
import org.makerminds.internship.java.restaurantpoint.model.UserRole;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class LogInView {

	private JFrame loginFrame;
	private JTextField usernameTextField;
	private JPasswordField passwordTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LogInView window = new LogInView();
					window.loginFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LogInView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		JPanel panel = initializeLogInFrame();

		createUsernameComponent(panel);

		createPasswordFieldComponent(panel);

		createLoginComponent(panel);

	}

	private void createLoginComponent(JPanel panel) {
		JButton loginButton = new JButton("LogIn");
		loginButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		loginButton.setBounds(101, 185, 89, 23);
		panel.add(loginButton);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logInWithProvidedUserCredentials();
			}

		});

	}

	private void logInWithProvidedUserCredentials() {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		LoginController loginController = LoginController.getInstance();
		if (isCredentialProvided(username, password)) {
			loginController.logInUser(username, password);
			User user = loginController.getLoggedInUser();
			handleWrongUserCredentials(user);
		}
	}
	
	private void handleWrongUserCredentials(User loggedInUser) {
		if(loggedInUser == null) {
			JOptionPane.showMessageDialog(null, "Wrong credentials!", "Error", JOptionPane.ERROR_MESSAGE);
			passwordTextField.setText("");
			usernameTextField.setText("");
		}else {
			new MainView();
			loginFrame.setVisible(false);
		}
	}
	private boolean isCredentialProvided(String username, String password) {
		LoginController logInController = LoginController.getInstance();
		if(logInController.isStringNullOrBlank(username)) {
			JOptionPane.showMessageDialog(null, "Please input a username", "Info", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}else if(logInController.isStringNullOrBlank(password)) {
			JOptionPane.showMessageDialog(null, "Please input a password", "Info", JOptionPane.INFORMATION_MESSAGE);
			return false;
		}
		return true;
	}
	private void createPasswordFieldComponent(JPanel panel) {
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordLabel.setBounds(39, 112, 96, 23);
		passwordTextField = new JPasswordField();
		passwordTextField.setBounds(161, 115, 96, 23);
		passwordTextField.setColumns(10);
		panel.add(passwordLabel);
		panel.add(passwordTextField);
	}

	private void createUsernameComponent(JPanel panel) {
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		usernameLabel.setBounds(39, 48, 96, 23);
		usernameTextField = new JTextField();
		usernameTextField.setBounds(161, 51, 96, 23);
		usernameTextField.setColumns(10);
		panel.add(usernameLabel);
		panel.add(usernameTextField);
	}

	private JPanel initializeLogInFrame() {
		loginFrame = new JFrame();
		loginFrame.setBounds(100, 100, 300, 300);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.getContentPane().setLayout(null);
		loginFrame.setLocationRelativeTo(null);
		loginFrame.setResizable(false);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 286, 263);
		panel.setBorder(BorderFactory.createTitledBorder("Restoraint Point"));
		loginFrame.getContentPane().add(panel);
		panel.setLayout(null);
		return panel;
	}
}

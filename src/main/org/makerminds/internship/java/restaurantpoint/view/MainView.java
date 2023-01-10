package org.makerminds.internship.java.restaurantpoint.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.plaf.basic.BasicBorders;

public class MainView {
	private JFrame frame;
	private JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView window = new MainView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */

	public MainView() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 984, 619);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Task 1 create navigation bar panel
		JPanel navigationBar = createNavigationBarPanel();
		// Task 2 create contentpanel
		contentPanel = createContentPanel();
		// Task 3 create comp to contain contentpanel
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.add(contentPanel);
		frame.getContentPane().revalidate();
		frame.getContentPane().add(navigationBar);
		frame.getContentPane().add(layeredPane);
	}

	private JPanel createContentPanel() {

		contentPanel.setBounds(266, 0, 700, 582);
		TitledBorder contentPanelTitledBorder = BorderFactory.createTitledBorder("Content Panel");
		contentPanel.setBorder(contentPanelTitledBorder);
		JLabel contentLabel = new JLabel("Welcome to RestorantPoint !");
		contentPanel.add(contentLabel);
		return contentPanel;
	}

	private JPanel createNavigationBarPanel() {
		JPanel navigationBar = new JPanel();
		navigationBar.setBounds(0, 0, 266, 582);
		TitledBorder navigationBarTitledBorder = BorderFactory.createTitledBorder("Navigation Bar");
		navigationBar.setBorder(navigationBarTitledBorder);
		navigationBar.setLayout(null);

		List<JPanel> navigationBarButtons = createNavigationBarPanelButtons();
		for (JPanel navigationBarPanelButton : navigationBarButtons) {
			navigationBar.add(navigationBarPanelButton);
		}

		return navigationBar;
	}

	private List<JPanel> createNavigationBarPanelButtons() {
		List<JPanel> navigationBarPanelButtons = new ArrayList<>();
		JPanel navigationBarPanelButton = null;
		int navigationItemVerticalPosition = 0;
		int navigationItemSpacing = 70;
		for (int i = 1; i < 6; i++) {
			navigationBarPanelButton = new JPanel();
			navigationBarPanelButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			navigationItemVerticalPosition += navigationItemSpacing;
			navigationBarPanelButton.setBounds(7, navigationItemVerticalPosition, 250, 48);
			JLabel navigationBarItemLabel = new JLabel();
			switch (i) {
			case 1:
				navigationBarItemLabel.setText("Restaurant Manager");
				break;
			case 2:
				navigationBarItemLabel.setText("Menu Manager");
				break;
			case 3:
				navigationBarItemLabel.setText("Menu Item Manager");
				break;
			case 4:
				navigationBarItemLabel.setText("Table Manager");
				break;
			case 5:
				navigationBarItemLabel.setText("Sign out");
				break;
			}
			navigationBarPanelButton.add(navigationBarItemLabel);
			JLabel contentLabel = new JLabel("" + i);
			JPanel buttonContentPanel = null;
			buttonContentPanel = assignViewBasedOnContent(contentLabel);

			navigationBarPanelButtons.add(navigationBarPanelButton);
			prepareNavigationBarItemMouseListener(buttonContentPanel, navigationBarPanelButton);
		}
		return navigationBarPanelButtons;
	}

	private JPanel assignViewBasedOnContent(JLabel contentLabel) {
		switch (contentLabel.getText()) {
		case "1":
			return new RestaurantManagerView().createRestaurantManagerPanel();
		case "2":
			return new MenuManagerView().createMenuManagerPanel();
		case "3":
			return new MenuItemManagerView().createMenuItemManagerPanel();
		case "4":
			return new MenuManagerView().createManagementPanel();
		default:
			return null;
		}
	}

	private void prepareNavigationBarItemMouseListener(JPanel buttonContentPanel, JPanel navigationBarPanelButton) {
		navigationBarPanelButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//TODO create a if statement to implement signout
				buttonContentPanel.setBounds(5, 20, 690, 550);
				contentPanel.setLayout(null);
				contentPanel.removeAll();
				contentPanel.add(buttonContentPanel);
				contentPanel.repaint();
				contentPanel.revalidate();
			}
		});
	}

}

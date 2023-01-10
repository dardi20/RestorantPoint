package org.makerminds.internship.java.restaurantpoint.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.makerminds.internship.java.restaurantpoint.controller.MenuManagerController;
import org.makerminds.internship.java.restaurantpoint.model.Menu;

public class MenuManagerView {

	private JFrame frame;
	private JPanel contentPanel;
	private MenuManagerController menuManagerController = new MenuManagerController();
	private JList menuJList = new JList<>();
	private String selectedRestaurant = null;
	private Menu selectedMenu = null;
	private JTextField menuNameField;
	private JTextField menuItemIdField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuManagerView window = new MenuManagerView();
//					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MenuManagerView() {
//		super();
		// initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		contentPanel = new JPanel();
//		contentPanel.setBounds(280, 5, 699, 568);
		createManagementPanel();
//		super.getFrame().add(contentPanel);

	}

	public JPanel createMenuManagerPanel() {
		JPanel menuManagerPanel = new JPanel();
		menuManagerPanel.setLayout(null);

		JPanel restaurantSelectionPanel = createRestaurantSelectionPanel();
		restaurantSelectionPanel.setBounds(310, 30, 310, 110);
		menuManagerPanel.add(restaurantSelectionPanel);
		JPanel menuListPanel = createMenuListPanel();
		menuListPanel.setBounds(310, 150, 350, 250);
		menuManagerPanel.add(menuListPanel);
		JPanel managmentPanel = createManagementPanel();
		managmentPanel.setBounds(30, 30, 300, 550);
		menuManagerPanel.add(managmentPanel);
		return menuManagerPanel;
	}

	private JPanel createMenuListPanel() {
		JPanel menuListPanel = new JPanel();
		TitledBorder menuListTitledBorder = new TitledBorder("Menu List");
		menuListPanel.setBorder(menuListTitledBorder);
		
		menuJList.setBounds(8, 15, 334, 230);
		prepareListItemListener(menuJList);
		menuListPanel.add(menuJList);
		menuListPanel.setLayout(null);
		return menuListPanel;
	}

	private void prepareListItemListener(JList menuJListLocal) {
		menuJList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				String selectedMenuAsString = (String) menuJListLocal.getSelectedValue();
				selectedMenu = menuManagerController.getMenuObjectFromMenuName(selectedMenuAsString);
				menuNameField.setText(selectedMenu.getMenuName());
			}
		});
	}

	private JPanel createRestaurantSelectionPanel() {
		JPanel restaurantSelectionPanel = new JPanel();
		TitledBorder restaurantSelectionTitledBorder = BorderFactory.createTitledBorder("Restaurants");
		restaurantSelectionPanel.setBorder(restaurantSelectionTitledBorder);
//		String[] restaurantArray = menuManagerController.getRestaurantsIntoArray();
		JComboBox<String> restaurantComboBox = new JComboBox<>();
		fillRestaurantComboBox(restaurantComboBox);
		prepareItemListener(restaurantComboBox);
		restaurantComboBox.setBounds(5, 40, 300, 60);
		restaurantSelectionPanel.add(restaurantComboBox);
		restaurantSelectionPanel.setLayout(null);
		return restaurantSelectionPanel;
	}

	private void fillRestaurantComboBox(JComboBox<String> restaurantComboBox) {
		List<String> allRestaurants = menuManagerController.getRestaurantsAsString();
		for (String restaurant : allRestaurants) {
			restaurantComboBox.addItem(restaurant);
		}

	}

	private void prepareItemListener(JComboBox<String> restaurantComboBox) {
		restaurantComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				selectedRestaurant = (String) restaurantComboBox.getSelectedItem();
				String[] menuListAsArray = menuManagerController.getMenuListAsArray(selectedRestaurant);
				menuJList.removeAll();
				menuJList.setListData(menuListAsArray);
			}
		});
	}

	public JPanel createManagementPanel() {

		JPanel managmentPanel = new JPanel();
		managmentPanel.setLayout(null);
		JLabel menuItemNameLabel = new JLabel("Menu Item Name");
		menuItemNameLabel.setBounds(10, 20, 150, 40);
		managmentPanel.add(menuItemNameLabel);
		menuNameField = new JTextField();
		menuNameField.setBounds(10, 60, 150, 40);
		managmentPanel.add(menuNameField);
		JButton createMenuButton = new JButton("Create");
		createMenuButton.setBounds(5, 390, 70, 40);
		prepareCreateMenuButtonActionListener(createMenuButton);
		managmentPanel.add(createMenuButton);
		JButton updateMenuButton = new JButton("Update");
		updateMenuButton.setBounds(80, 390, 70, 40);
		prepareUpdateButtonActionListener(updateMenuButton);
		managmentPanel.add(updateMenuButton);
		JButton deleteMenuButton = new JButton("Delete");
		deleteMenuButton.setBounds(155, 390, 70, 40);
		prepareDeleteMenuButtonActionListener(deleteMenuButton);
		managmentPanel.add(deleteMenuButton);

		return managmentPanel;
	}

	private void prepareDeleteMenuButtonActionListener(JButton deleteMenuButton) {
		deleteMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedRestaurant != null && selectedMenu != null) {
					menuManagerController.editMenuData("Delete", menuNameField.getText(), selectedMenu,
							selectedRestaurant);
					String[] menuListAsArray = menuManagerController.getMenuListAsArray(selectedRestaurant);
					menuJList.removeAll();
					menuJList.setListData(menuListAsArray);
				}
			}
		});
	}

	private void prepareUpdateButtonActionListener(JButton updateMenuButton) {
		updateMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedRestaurant != null && selectedMenu != null && menuNameField.getText() != null) {
					menuManagerController.editMenuData("Update", menuNameField.getText(), selectedMenu,
							selectedRestaurant);
					String[] menuListAsArray = menuManagerController.getMenuListAsArray(selectedRestaurant);
					menuJList.removeAll();
					menuJList.setListData(menuListAsArray);
				}
			}
		});
	}

	private void prepareCreateMenuButtonActionListener(JButton createMenuButton) {
		createMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedRestaurant != null && menuNameField.getText() != null) {
					menuManagerController.editMenuData("Create", menuNameField.getText(), selectedMenu,
							selectedRestaurant);
					String[] menuListAsArray = menuManagerController.getMenuListAsArray(selectedRestaurant);
					menuJList.removeAll();
					menuJList.setListData(menuListAsArray);
				}
			}
		});
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}


}

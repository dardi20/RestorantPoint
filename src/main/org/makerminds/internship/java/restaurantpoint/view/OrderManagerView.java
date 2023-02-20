package org.makerminds.internship.java.restaurantpoint.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.makerminds.internship.java.restaurantpoint.controller.MenuManagerController;
import org.makerminds.internship.java.restaurantpoint.controller.OrderManagerController;
import org.makerminds.internship.java.restaurantpoint.model.Menu;
import org.makerminds.internship.java.restaurantpoint.model.Restaurant;
import org.makerminds.internship.java.restaurantpoint.model.Table;

public class OrderManagerView {
	private JFrame frame;
	private JPanel contentPanel;
	private MenuManagerController menuManagerController = new MenuManagerController();
	private JList menuJList = new JList<>();
	private Restaurant selectedRestaurant = new Restaurant();
	private Menu selectedMenu = null;
	private JTextField menuNameField;
	private JTextField menuItemIdField;
	private String selectedRestaurantName;
	private JComboBox<String> restaurantComboBox= new JComboBox<>();
	private OrderManagerController orderManagerController= new OrderManagerController();
	private String selectedTable;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderManagerView window = new OrderManagerView();
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
	public OrderManagerView() {
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		contentPanel = new JPanel();
//		contentPanel.setBounds(280, 5, 699, 568);
		createOrderManagerPanel();
//		super.getFrame().add(contentPanel);

	}

	public JPanel createOrderManagerPanel() {
		JPanel orderManagerPanel = new JPanel();
		orderManagerPanel.setLayout(null);

		JPanel tableSelectionPanel = createTableSelectionPanel();
		tableSelectionPanel.setBounds(30, 30, 310, 110);
		orderManagerPanel.add(tableSelectionPanel);
//		JPanel menuListPanel = createMenuListPanel();
//		menuListPanel.setBounds(310, 150, 350, 250);
//		orderManagerPanel.add(menuListPanel);
//		JPanel managmentPanel = createManagementPanel();
//		managmentPanel.setBounds(30, 30, 300, 550);
//		orderManagerPanel.add(managmentPanel);
		return orderManagerPanel;
	}

//	private JPanel createMenuListPanel() {
//		JPanel menuListPanel = new JPanel();
//		TitledBorder menuListTitledBorder = new TitledBorder("Menu List");
//		menuListPanel.setBorder(menuListTitledBorder);
//		
//		menuJList.setBounds(8, 15, 334, 230);
//		prepareListItemListener(menuJList);
//		menuListPanel.add(menuJList);
//		menuListPanel.setLayout(null);
//		return menuListPanel;
//	}

//	private void prepareListItemListener(JList menuJListLocal) {
//		menuJList.addMouseListener(new MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				String selectedMenuAsString = (String) menuJListLocal.getSelectedValue();
//				selectedMenu = menuManagerController.getMenuObjectFromMenuName(selectedMenuAsString);
//				menuNameField.setText(selectedMenu.getMenuName());
//			}
//		});
//	}

	private JPanel createTableSelectionPanel() {
		JPanel restaurantSelectionPanel = new JPanel();
		TitledBorder restaurantSelectionTitledBorder = BorderFactory.createTitledBorder("Tables");
		restaurantSelectionPanel.setBorder(restaurantSelectionTitledBorder);
//		String[] restaurantArray = menuManagerController.getRestaurantsIntoArray();
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		fillListModel(listModel);
		JList tablesList = new JList(listModel);
		prepareItemListener(tablesList);
		tablesList.setBounds(5, 40, 300, 60);
		restaurantSelectionPanel.add(tablesList);
		restaurantSelectionPanel.setLayout(null);
		return restaurantSelectionPanel;
	}

	private void prepareItemListener(JList tablesList) {
		tablesList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedTable = (String) e.getSource();
				
			}
			
		});
		
	}

	private void fillListModel(DefaultListModel<String> listModel) {
	// TODO Get Restaurant for that specific waiter.
		Restaurant logedInUserRestaurant = new Restaurant();
		List<Table>tablesAsList = orderManagerController.getTablesForRestaurant(logedInUserRestaurant);
		int i=0;
		for(Table table: tablesAsList) {
			listModel.add(i, table.getTable_id()+"");
			i++;
		}
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
				selectedRestaurantName = (String) restaurantComboBox.getSelectedItem();
				selectedRestaurant = menuManagerController.getRestaurantFromRestaurantName(selectedRestaurantName);
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

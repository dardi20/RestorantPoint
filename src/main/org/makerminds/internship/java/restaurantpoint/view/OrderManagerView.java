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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.makerminds.internship.java.restaurantpoint.controller.LoginController;
import org.makerminds.internship.java.restaurantpoint.controller.MenuItemManagerController;
import org.makerminds.internship.java.restaurantpoint.controller.MenuManagerController;
import org.makerminds.internship.java.restaurantpoint.controller.OrderManagerController;
import org.makerminds.internship.java.restaurantpoint.model.Menu;
import org.makerminds.internship.java.restaurantpoint.model.Product;
import org.makerminds.internship.java.restaurantpoint.model.Restaurant;
import org.makerminds.internship.java.restaurantpoint.model.Table;
import org.makerminds.internship.java.restaurantpoint.model.User;

public class OrderManagerView {
	private JFrame frame;
	private JPanel contentPanel;
	private MenuManagerController menuManagerController = new MenuManagerController();
	private JList menuJList = new JList<>();
	private String selectedRestaurantId;
	private Product selectedProduct = new Product();
	private String selectedMenu;
	private JTextField menuNameField;
	private JTextField menuItemIdField;
	private String selectedRestaurantName;
	private String[] columnNames = { "Product", "Price" };
	private String[][] menuItemData;
	private DefaultTableModel menuItemsTableModel = new DefaultTableModel(menuItemData, columnNames);
	private JTable menuItemsTable = new JTable(menuItemsTableModel);
	private JComboBox<String> restaurantComboBox = new JComboBox<>();
	private OrderManagerController orderManagerController = new OrderManagerController();
	private MenuItemManagerController menuItemManagerController = new MenuItemManagerController();
	private String selectedTable;
	private JPanel orderManagerPanel;
	private JPanel orderOverviewPanel;

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
		User loggedInUser = LoginController.getInstance().getLoggedInUser();
		this.selectedRestaurantId = loggedInUser.getRestaurant();
		this.orderManagerPanel = createOrderManagerPanel();
		this.orderOverviewPanel = createOrderOverviewPanel();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
//		contentPanel = new JPanel();
//		contentPanel.setBounds(280, 5, 699, 568);
		this.orderManagerPanel = createOrderManagerPanel();
//		super.getFrame().add(contentPanel);

	}

	public JPanel createOrderManagerPanel() {
		JPanel orderManagerPanel = new JPanel();
		orderManagerPanel.setLayout(null);

		JPanel tableSelectionPanel = createTableSelectionPanel();
		tableSelectionPanel.setBounds(30, 30, 310, 110);
		orderManagerPanel.add(tableSelectionPanel);

		// JPanel menuListPanel = createMenuListPanel();
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
		/*
		 * TODO implement method to set false current order manager panel and create a
		 * new order overview panel with the following elements: a menu combo box
		 * (filter menulistr from the selected restaurant) a menu Items JTable (Product
		 * name, price) a order overview table (Product name , quantity, price) JText
		 * field subtotal,vat,total Order JButton That will confirm the Order.
		 * 
		 **/
		tablesList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				// filters unmapped clicks
				if (e.getClickCount() == 2 && e.getSource() != null) {
					selectedTable=(String)e.getSource();
					orderManagerPanel.setVisible(false);
					orderOverviewPanel.setVisible(true);
				}
			}
		});

//		addListSelectionListener(new ListSelectionListener() {
//
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				selectedTable = (String) e.getSource();
//
//			}
//
//		});

	}

	private void fillListModel(DefaultListModel<String> listModel) {
		List<Table> tablesAsList = orderManagerController.getTablesForRestaurant(selectedRestaurantId);
		int i = 0;
		for (Table table : tablesAsList) {
			listModel.add(i, table.getTable_id() + "");
			i++;
		}
	}

//	private void prepareItemListener(JComboBox<String> restaurantComboBox) {
//		restaurantComboBox.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				selectedRestaurantName = (String) restaurantComboBox.getSelectedItem();
//				selectedRestaurant = menuManagerController.getRestaurantFromRestaurantName(selectedRestaurantName);
//				String[] menuListAsArray = menuManagerController.getMenuListAsArray(selectedRestaurant);
//				menuJList.removeAll();
//				menuJList.setListData(menuListAsArray);
//			}
//		});
//	}

	public JPanel createOrderOverviewPanel() {

		JPanel orderOverviewPanel = new JPanel();
		TitledBorder orderOverviewBorder = BorderFactory.createTitledBorder("Table Order Details");
		orderOverviewPanel.setBorder(orderOverviewBorder);

		orderOverviewPanel.setBounds(30, 30, 310, 110);

		orderOverviewPanel.setLayout(null);

		JComboBox<String> menuSelectionComboBox = new JComboBox<>();
		fillMenuSelectionComboBox(menuSelectionComboBox);
		menuSelectionComboBox.setBounds(10, 20, 150, 40);
		orderOverviewPanel.add(menuSelectionComboBox);
		prepareMenuSelectionActionListener(menuSelectionComboBox);

	    // menu item table get filled after clicking a menu from Combo BOX
		//		fillMenuItemTableModel(menuItemsTableModel);
		// TODO verify correct bounds
		menuItemsTable.setBounds(0, 0, 0, 0);
		orderOverviewPanel.add(menuItemsTable);

		menuNameField = new JTextField();
		menuNameField.setBounds(10, 60, 150, 40);
		orderOverviewPanel.add(menuNameField);

		JButton addProductButton = new JButton("Create");
		addProductButton.setBounds(5, 390, 70, 40);
		prepareAddProductButtonActionListener(addProductButton);
		orderOverviewPanel.add(addProductButton);

		JButton deleteProductButton = new JButton("Delete");
		deleteProductButton.setBounds(80, 390, 70, 40);
		prepareDeleteProductButtonActionListener(deleteProductButton);
		orderOverviewPanel.add(deleteProductButton);

		JButton printInvoiceButton = new JButton();
		printInvoiceButton.setBounds(155, 390, 70, 40);
		orderOverviewPanel.add(printInvoiceButton);

		orderOverviewPanel.setVisible(false);

		return orderOverviewPanel;
	}
	private void prepareMenuSelectionActionListener(JComboBox<String> menuSelectionComboBox) {
		menuSelectionComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedMenu = (String)e.getSource();
				fillMenuItemTableModel(menuItemsTableModel);
			}
		});
		
	}

	private void fillMenuSelectionComboBox(JComboBox<String> menuSelecttionComboBox) {
		String[] menusAsString = menuManagerController.getMenuListAsArray(new Restaurant(Integer.valueOf(selectedRestaurantId)));
		for (String menu : menusAsString) {
			menuSelecttionComboBox.addItem(menu);
		}
		
	}

	private void fillMenuItemTableModel(DefaultTableModel menuItemsTableModel) {
		Menu selectedMenuObject = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenu);
		menuItemData = menuItemManagerController.getMenuDataAsMatrix(selectedMenuObject);
		menuItemsTableModel.setDataVector(menuItemData, columnNames);
		menuItemsTableModel.fireTableDataChanged();
	}


	private void prepareDeleteProductButtonActionListener(JButton deleteProductButton) {
		deleteProductButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (selectedProduct != null) {
					orderManagerController.editOrderOverview("Delete", selectedProduct, selectedRestaurantId);
					String[] orderOverviewAsArray = orderManagerController.getOrderOverviewAsArray(selectedRestaurantId);
					menuJList.removeAll();
					menuJList.setListData(orderOverviewAsArray);
				}
			}
		});
	}

	private void prepareAddProductButtonActionListener(JButton addProductButton) {
		addProductButton.addActionListener(new ActionListener() {
			@Override

			// TODO check method one more time after configuring database

			public void actionPerformed(ActionEvent e) {
				if (selectedProduct != null) {
					orderManagerController.editOrderOverview("Create", selectedProduct, selectedRestaurantId);
					String[] orderOverviewAsArray = orderManagerController.getOrderOverviewAsArray(selectedRestaurantId);
					menuJList.removeAll();
					menuJList.setListData(orderOverviewAsArray);
				}
//				if (selectedRestaurant != null && menuNameField.getText() != null) {
//					menuManagerController.editMenuData("Create", menuNameField.getText(), selectedMenu,
//							selectedRestaurant);
//					String[] menuListAsArray = menuManagerController.getMenuListAsArray(selectedRestaurant);
//					menuJList.removeAll();
//					menuJList.setListData(menuListAsArray);
//				}
			}
		});
	}
//TODO add acion listener for order button which should set the table order status to occupied if free,
	// add action listener for print invoice to open a dialog box showing the total order and set table to free when occupied.
	
	public JPanel getContentPanel() {
		return contentPanel;
	}
}

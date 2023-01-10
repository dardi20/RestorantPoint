package org.makerminds.internship.java.restaurantpoint.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.makerminds.internship.java.restaurantpoint.controller.MenuItemManagerController;
import org.makerminds.internship.java.restaurantpoint.controller.MenuManagerController;
import org.makerminds.internship.java.restaurantpoint.model.Drink;
import org.makerminds.internship.java.restaurantpoint.model.Meal;
import org.makerminds.internship.java.restaurantpoint.model.Menu;
import org.makerminds.internship.java.restaurantpoint.model.Product;

public class MenuItemManagerView {

	private JFrame frame;
	private JPanel contentPanel;
	private MenuItemManagerController menuItemManagerController = new MenuItemManagerController();
	private MenuManagerController menuManagerController = new MenuManagerController();
	private String selectedRestaurant = null;
	private Menu selectedMenu = null;
	private JTextField menuItemNameField;
	private JTextField menuItemIdField;
	private JTextField menuItemPriceField;
	private JComboBox<String> menuComboBox;
	private Product selectedProduct;
	private final String[] columnNames = { "Id", "Name", "Price" };
	private String[][] menuDataAsMatrix;
	private DefaultTableModel defaultMenuItemTableModel = new DefaultTableModel(menuDataAsMatrix,columnNames);
	private JTable menuJTable = new JTable(defaultMenuItemTableModel);
	private JRadioButton mealRadioButton;
	private JRadioButton drinkRadioButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuItemManagerView window = new MenuItemManagerView();
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
	public MenuItemManagerView() {
//		super();
		// initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		createManagementPanel();

	}

	public JPanel createMenuItemManagerPanel() {
		JPanel menuManagerPanel = new JPanel();
		menuManagerPanel.setLayout(null);

		JPanel restaurantSelectionPanel = createRestaurantSelectionPanel();
		restaurantSelectionPanel.setBounds(310, 10, 310, 110);
		menuManagerPanel.add(restaurantSelectionPanel);
		JPanel menuSelectionPanel = createMenuSelectionPanel();
		menuSelectionPanel.setBounds(310, 130, 310, 110);
		menuManagerPanel.add(menuSelectionPanel);
		JPanel menuItemListPanel = createMenuItemTablePanel();
		menuItemListPanel.setBounds(310, 255, 310, 275);
		menuManagerPanel.add(menuItemListPanel);
		JPanel managmentPanel = createManagementPanel();
		managmentPanel.setBounds(30, 30, 300, 550);
		menuManagerPanel.add(managmentPanel);
		return menuManagerPanel;
	}

	private JPanel createMenuItemTablePanel() {
		JPanel menuItemListPanel = new JPanel();
		TitledBorder menuItemListTitledBorder = BorderFactory.createTitledBorder("Menu Item List");
		menuItemListPanel.setBorder(menuItemListTitledBorder);
		
		JScrollPane menuItemListScrollPane = new JScrollPane(menuJTable);
		menuItemListScrollPane.setBounds(5, 20, 300, 250);
		
		prepareListItemListener(menuJTable);
		
		menuItemListPanel.add(menuItemListScrollPane);
		
		menuItemListPanel.setLayout(null);
		return menuItemListPanel;
	}

	private void prepareListItemListener(JTable menuJTable) {
		menuJTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedMenuItemRow = menuJTable.getSelectedRow();
				if (selectedMenuItemRow != -1) {
					menuItemIdField.setText(menuDataAsMatrix[selectedMenuItemRow][0]);
					menuItemNameField.setText(menuDataAsMatrix[selectedMenuItemRow][1]);
					menuItemPriceField.setText(menuDataAsMatrix[selectedMenuItemRow][2]);
					if (Integer.valueOf(menuItemIdField.getText()) < 200) {
						mealRadioButton.setSelected(true);
						selectedProduct = new Meal(menuItemNameField.getText(),
								Integer.valueOf(menuItemIdField.getText()),
								Double.valueOf(menuItemPriceField.getText()));
					} else {
						drinkRadioButton.setSelected(true);
						selectedProduct = new Drink(menuItemNameField.getText(),
								Integer.valueOf(menuItemIdField.getText()),
								Double.valueOf(menuItemPriceField.getText()));
					}
				}
			}
		});
	}

	private JPanel createRestaurantSelectionPanel() {
		JPanel restaurantSelectionPanel = new JPanel();
		TitledBorder restaurantSelectionTitledBorder = BorderFactory.createTitledBorder("Restaurant Selection");
		restaurantSelectionPanel.setBorder(restaurantSelectionTitledBorder);
		JComboBox<String> restaurantComboBox = new JComboBox<>();
		fillRestaurantComboBox(restaurantComboBox);
		prepareItemListener(restaurantComboBox);
		restaurantComboBox.setBounds(5, 40, 300, 60);
		restaurantSelectionPanel.add(restaurantComboBox);
		restaurantSelectionPanel.setLayout(null);
		return restaurantSelectionPanel;
	}

	private JPanel createMenuSelectionPanel() {
		JPanel menuSelectionPanel = new JPanel();
		TitledBorder restaurantSelectionTitledBorder = BorderFactory.createTitledBorder("Menu Selection");
		menuSelectionPanel.setBorder(restaurantSelectionTitledBorder);
		menuComboBox = new JComboBox<>();
		prepareMenuItemSelectionListener(menuComboBox);
		menuComboBox.setBounds(5, 40, 300, 60);
		menuSelectionPanel.add(menuComboBox);
		menuSelectionPanel.setLayout(null);
		return menuSelectionPanel;
	}

	private void prepareMenuItemSelectionListener(JComboBox<String> menuComboBox) {
		menuComboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedMenuName = (String) menuComboBox.getSelectedItem();
				if (selectedMenuName != null) {
					selectedMenu = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenuName);
					HashMap<Integer, Product> menuItems = selectedMenu.getMenuItems();
					menuDataAsMatrix = menuItemManagerController.getMenuDataAsMatrix(selectedMenu);
					defaultMenuItemTableModel.setDataVector(menuDataAsMatrix, columnNames);
				}
			}
		});
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
				menuComboBox.removeAllItems();
				selectedRestaurant = (String) restaurantComboBox.getSelectedItem();
				String[] menuListAsArray = menuManagerController.getMenuListAsArray(selectedRestaurant);
				for (int i = 0; i < menuListAsArray.length; i++) {
					menuComboBox.addItem(menuListAsArray[i]);
				}
			}
		});
	}

	public JPanel createManagementPanel() {

		JPanel managmentPanel = new JPanel();
		managmentPanel.setLayout(null);
		JLabel menuItemIdLabel = new JLabel("Menu Item Id");
		managmentPanel.add(menuItemIdLabel);
		menuItemIdLabel.setBounds(10, 20, 150, 40);
		menuItemIdField = new JTextField();
		menuItemIdField.setBounds(10, 60, 150, 40);
		managmentPanel.add(menuItemIdField);
		JLabel menuItemNameLabel = new JLabel("Menu Item Name");
		menuItemNameLabel.setBounds(10, 100, 150, 40);
		managmentPanel.add(menuItemNameLabel);
		menuItemNameField = new JTextField();
		menuItemNameField.setBounds(10, 140, 150, 40);
		managmentPanel.add(menuItemNameField);
		JLabel menuItemPriceLabel = new JLabel("Menu Item Price");
		menuItemPriceLabel.setBounds(10, 180, 150, 40);
		managmentPanel.add(menuItemPriceLabel);
		menuItemPriceField = new JTextField();
		menuItemPriceField.setBounds(10, 220, 150, 40);
		managmentPanel.add(menuItemPriceField);

		mealRadioButton = new JRadioButton("Meal");
		mealRadioButton.setBounds(10, 280, 80, 40);
		drinkRadioButton = new JRadioButton("Drink");
		drinkRadioButton.setBounds(10, 320, 80, 40);
		ButtonGroup itemType = new ButtonGroup();
		itemType.add(mealRadioButton);
		itemType.add(drinkRadioButton);
		managmentPanel.add(mealRadioButton);
		managmentPanel.add(drinkRadioButton);
		JButton createMenuButton = new JButton("Create");
		createMenuButton.setBounds(5, 390, 70, 40);
		prepareMenuItemCreateButtonActionListener(createMenuButton);
		managmentPanel.add(createMenuButton);
		JButton updateMenuButton = new JButton("Update");
		updateMenuButton.setBounds(80, 390, 70, 40);
		prepareMenuItemUpdateButtonActionListener(updateMenuButton);
		managmentPanel.add(updateMenuButton);
		JButton deleteMenuButton = new JButton("Delete");
		deleteMenuButton.setBounds(155, 390, 70, 40);
		prepareDeleteMenuItemButtonActionListener(deleteMenuButton);
		managmentPanel.add(deleteMenuButton);

		return managmentPanel;
	}

	private void prepareDeleteMenuItemButtonActionListener(JButton deleteMenuButton) {
		deleteMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (menuItemIdField.getText() != null) {
					selectedMenu = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenu.getMenuName());
					menuItemManagerController.editMenuData("Delete", selectedProduct, selectedMenu, null);
					selectedMenu = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenu.getMenuName());
					menuDataAsMatrix = menuItemManagerController.getMenuDataAsMatrix(selectedMenu);
					defaultMenuItemTableModel.setDataVector(menuDataAsMatrix, columnNames);
					defaultMenuItemTableModel.fireTableDataChanged();
				}

			}
		});

	}

	private void prepareMenuItemUpdateButtonActionListener(JButton updateMenuButton) {
		updateMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (menuItemIdField.getText() != null) {
					Product updatedProduct = new Product(menuItemNameField.getText(),
							Integer.valueOf(menuItemIdField.getText()), Double.valueOf(menuItemPriceField.getText()));
					selectedMenu = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenu.getMenuName());
					menuItemManagerController.editMenuData("Update", selectedProduct, selectedMenu, updatedProduct);
					selectedMenu = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenu.getMenuName());
					menuDataAsMatrix = menuItemManagerController.getMenuDataAsMatrix(selectedMenu);
					defaultMenuItemTableModel.setDataVector(menuDataAsMatrix, columnNames);
					defaultMenuItemTableModel.fireTableDataChanged();

				}

			}
		});

	}

	private void prepareMenuItemCreateButtonActionListener(JButton createMenuButton) {
		createMenuButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (menuItemIdField.getText() != null) {
					Product newProduct = new Product(menuItemNameField.getText(),
							Integer.valueOf(menuItemIdField.getText()), Double.valueOf(menuItemPriceField.getText()));
					selectedMenu = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenu.getMenuName());
					menuItemManagerController.editMenuData("Create", selectedProduct, selectedMenu, newProduct);
					selectedMenu = menuItemManagerController.getMenuFromSelectedMenuName(selectedMenu.getMenuName());
					menuDataAsMatrix = menuItemManagerController.getMenuDataAsMatrix(selectedMenu);
					defaultMenuItemTableModel.setDataVector(menuDataAsMatrix, columnNames);
					defaultMenuItemTableModel.fireTableDataChanged();

				}
			}
		});
	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

}

package org.makerminds.internship.java.restaurantpoint.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.makerminds.internship.java.restaurantpoint.controller.RestaurantManagerController;
import org.makerminds.internship.java.restaurantpoint.model.Drink;
import org.makerminds.internship.java.restaurantpoint.model.Meal;
import org.makerminds.internship.java.restaurantpoint.model.Restaurant;

public class RestaurantManagerView {

	private JFrame frame;
	private JTextField restaurantNameField;
	private JTextField restaurantAddressField;
	private RestaurantManagerController restaurantManagerController = new RestaurantManagerController();
	private Restaurant selectedRestaurant = new Restaurant();
	private DefaultTableModel defaultTableModel;
	private String[] columnNames = { "Restaurant Name", "Restaurant Address" };
	private String[][] restaurantTableData; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RestaurantManagerView window = new RestaurantManagerView();
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
	public RestaurantManagerView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JPanel createRestaurantManagerPanel() {
		JPanel restaurantManagerPanel = new JPanel();
		restaurantManagerPanel.setLayout(null);

		JPanel restaurantTablePanel = createRestaurantTablePanel();
		restaurantTablePanel.setBounds(310, 60, 350, 250);
		restaurantManagerPanel.add(restaurantTablePanel);

		JPanel managmentPanel = createManagmentPanel();
		managmentPanel.setBounds(30, 30, 300, 550);
		restaurantManagerPanel.add(managmentPanel);
		return restaurantManagerPanel;
	}

	private JPanel createManagmentPanel() {

		JPanel managmentPanel = new JPanel();
		managmentPanel.setLayout(null);

		JLabel restaurantNameLabel = new JLabel("Restaurant Name");
		restaurantNameLabel.setBounds(10, 20, 150, 40);
		managmentPanel.add(restaurantNameLabel);

		restaurantNameField = new JTextField();
		restaurantNameField.setBounds(10, 60, 150, 40);
		managmentPanel.add(restaurantNameField);

		JLabel restaurantAddressLabel = new JLabel("Restaurant Address");
		restaurantAddressLabel.setBounds(10, 100, 150, 40);
		managmentPanel.add(restaurantAddressLabel);

		restaurantAddressField = new JTextField();
		restaurantAddressField.setBounds(10, 140, 150, 40);
		managmentPanel.add(restaurantAddressField);

		JButton createRestaurantButton = new JButton("Create");
		createRestaurantButton.setBounds(5, 390, 70, 40);
		prepareCreateRestaurantButtonActionListener(createRestaurantButton);
		managmentPanel.add(createRestaurantButton);
		JButton updateRestaurantButton = new JButton("Update");
		updateRestaurantButton.setBounds(80, 390, 70, 40);
		prepareUpdateRestaurantButtonActionListener(updateRestaurantButton);
		managmentPanel.add(updateRestaurantButton);
		JButton deleteRestaurantButton = new JButton("Delete");
		deleteRestaurantButton.setBounds(155, 390, 70, 40);
		prepareDeleteRestaurantButtonActionListener(deleteRestaurantButton);
		managmentPanel.add(deleteRestaurantButton);

		return managmentPanel;
	}

	private JPanel createRestaurantTablePanel() {
		JPanel restaurantTablePanel = new JPanel();
		TitledBorder restaurantTableBorder = new TitledBorder("Restaurants");
		restaurantTablePanel.setBorder(restaurantTableBorder);

		restaurantTablePanel.setLayout(null);

		JScrollPane restaurantTableDetailsScrollPane = createRestaurantTableScrollPane();

		restaurantTablePanel.add(restaurantTableDetailsScrollPane);

		return restaurantTablePanel;
	}

	private JScrollPane createRestaurantTableScrollPane() {
		restaurantTableData = restaurantManagerController.getRestaurantTableDataAsMatrix();

		defaultTableModel = new DefaultTableModel(restaurantTableData, columnNames);
		JTable restaurantTableDetails = new JTable(defaultTableModel);
		prepareTableItemListener(restaurantTableDetails);
		JScrollPane restaurantTableDetailsScrollPane = new JScrollPane(restaurantTableDetails);
		restaurantTableDetailsScrollPane.setBounds(8, 20, 334, 220);
		return restaurantTableDetailsScrollPane;
	}

	// TODO refresh the table after cruds operations
	private void prepareDeleteRestaurantButtonActionListener(JButton deleteRestaurantButton) {
		deleteRestaurantButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String restaurantName = selectedRestaurant.getName();
				restaurantManagerController.editRestaurantData("Delete", restaurantName, null);
				restaurantTableData = restaurantManagerController.getRestaurantTableDataAsMatrix();
				defaultTableModel.setDataVector(restaurantTableData, columnNames);
				defaultTableModel.fireTableDataChanged();

			}
		});

	}

	private void prepareUpdateRestaurantButtonActionListener(JButton updateRestaurantButton) {
		updateRestaurantButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String restaurantName = selectedRestaurant.getName();
				Restaurant newRestaurant = new Restaurant(restaurantNameField.getText(),
						restaurantAddressField.getText());
				restaurantManagerController.editRestaurantData("Update", restaurantName, newRestaurant);
				restaurantTableData = restaurantManagerController.getRestaurantTableDataAsMatrix();
				defaultTableModel.setDataVector(restaurantTableData, columnNames);
				defaultTableModel.fireTableDataChanged();
			}
		});
	}

	private void prepareCreateRestaurantButtonActionListener(JButton createRestaurantButton) {
		createRestaurantButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Restaurant newRestaurant = new Restaurant(restaurantNameField.getText(),
						restaurantAddressField.getText());
				restaurantManagerController.editRestaurantData("Create", null, newRestaurant);
				restaurantTableData = restaurantManagerController.getRestaurantTableDataAsMatrix();
				defaultTableModel.setDataVector(restaurantTableData, columnNames);
				defaultTableModel.fireTableDataChanged();
			}
		});
	}
	private void prepareTableItemListener(JTable restaurantJTable) {
		restaurantJTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRestaurantItemRow = restaurantJTable.getSelectedRow();
				if (selectedRestaurantItemRow != -1) {
					restaurantNameField.setText(restaurantTableData[selectedRestaurantItemRow][0]);
					selectedRestaurant.setName(restaurantTableData[selectedRestaurantItemRow][0]);
					restaurantAddressField.setText(restaurantTableData[selectedRestaurantItemRow][1]);
					selectedRestaurant.setAdress(restaurantTableData[selectedRestaurantItemRow][1]);
				}
			}
		});
	}

}

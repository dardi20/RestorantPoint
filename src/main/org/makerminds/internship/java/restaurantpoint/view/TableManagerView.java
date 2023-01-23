package org.makerminds.internship.java.restaurantpoint.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.makerminds.internship.java.restaurantpoint.controller.MenuManagerController;
import org.makerminds.internship.java.restaurantpoint.controller.RestaurantManagerController;
import org.makerminds.internship.java.restaurantpoint.controller.TableManagerController;
import org.makerminds.internship.java.restaurantpoint.model.Restaurant;
import org.makerminds.internship.java.restaurantpoint.model.Table;

public class TableManagerView {
	private JTextField tableSeatsNumberField;
	private JTextField tableIdField;
	private String[][] tableSeatsTableData;
	private String[] columnNames = { "Id", "Seats" };
	private JFrame frame;
	private Table selectedTable;
	private DefaultTableModel defaultTableModel;
	private Restaurant selectedRestaurant = new Restaurant();
	// private RestaurantManagerController restaurantManagerController = new
	// RestaurantManagerController();
	private MenuManagerController menuManagerController = new MenuManagerController();
	private TableManagerController tableManagerController = new TableManagerController();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableManagerView window = new TableManagerView();
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
	public TableManagerView() {
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

	public JPanel createTableManagerPanel() {
		JPanel restaurantManagerPanel = new JPanel();
		restaurantManagerPanel.setLayout(null);

		JPanel restaurantSelectionPanel = createRestaurantSelectionPanel();
		restaurantSelectionPanel.setBounds(310, 30, 310, 110);
		restaurantManagerPanel.add(restaurantSelectionPanel);

		JPanel tableListPanel = createRestaurantTablePanel();
		tableListPanel.setBounds(310, 150, 350, 250);
		restaurantManagerPanel.add(tableListPanel);

		JPanel managmentPanel = createManagmentPanel();
		managmentPanel.setBounds(30, 30, 300, 550);
		restaurantManagerPanel.add(managmentPanel);
		return restaurantManagerPanel;
	}

	private JPanel createManagmentPanel() {

		JPanel managmentPanel = new JPanel();
		managmentPanel.setLayout(null);

		JLabel tableIdLabel = new JLabel("Table Id");
		tableIdLabel.setBounds(10, 20, 150, 40);
		managmentPanel.add(tableIdLabel);

		tableIdField = new JTextField();
		tableIdField.setBounds(10, 60, 150, 40);
		managmentPanel.add(tableIdField);

		JLabel tableSeatsNumberLabel = new JLabel("Seats");
		tableSeatsNumberLabel.setBounds(10, 100, 150, 40);
		managmentPanel.add(tableSeatsNumberLabel);

		tableSeatsNumberField = new JTextField();
		tableSeatsNumberField.setBounds(10, 140, 150, 40);
		managmentPanel.add(tableSeatsNumberField);

		JButton createTableButton = new JButton("Create");
		createTableButton.setBounds(5, 390, 70, 40);
		prepareCreateTableButtonActionListener(createTableButton);
		managmentPanel.add(createTableButton);
		JButton updateTableButton = new JButton("Update");
		updateTableButton.setBounds(80, 390, 70, 40);
		prepareUpdateTableButtonActionListener(updateTableButton);
		managmentPanel.add(updateTableButton);
		JButton deleteTableButton = new JButton("Delete");
		deleteTableButton.setBounds(155, 390, 70, 40);
		prepareDeleteTableButtonActionListener(deleteTableButton);
		managmentPanel.add(deleteTableButton);

		return managmentPanel;
	}

	private JPanel createRestaurantTablePanel() {
		JPanel restaurantTablePanel = new JPanel();
		TitledBorder restaurantTableBorder = new TitledBorder("Seats");
		restaurantTablePanel.setBorder(restaurantTableBorder);

		restaurantTablePanel.setLayout(null);

		JScrollPane restaurantTableDetailsScrollPane = createRestaurantTableScrollPane();

		restaurantTablePanel.add(restaurantTableDetailsScrollPane);

		return restaurantTablePanel;
	}

	private JScrollPane createRestaurantTableScrollPane() {
		tableSeatsTableData = tableManagerController.getTableDataAsMatrix(selectedRestaurant);

		defaultTableModel = new DefaultTableModel(tableSeatsTableData, columnNames);
		JTable restaurantTableSeatsTable = new JTable(defaultTableModel);
		prepareTableItemListener(restaurantTableSeatsTable);
		JScrollPane restaurantTableSeatsScrollPane = new JScrollPane(restaurantTableSeatsTable);
		restaurantTableSeatsScrollPane.setBounds(8, 20, 334, 220);
		return restaurantTableSeatsScrollPane;
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
				String selectedRestaurantId = (String) restaurantComboBox.getSelectedItem();
				selectedRestaurant = tableManagerController.getRestaurant(selectedRestaurantId);
				tableSeatsTableData = tableManagerController.getTableDataAsMatrix(selectedRestaurant);
				defaultTableModel.setDataVector(tableSeatsTableData, columnNames);
				defaultTableModel.fireTableDataChanged();
			}
		});
	}

	// TODO refresh the table after cruds operations
	private void prepareDeleteTableButtonActionListener(JButton deleteRestaurantButton) {
		deleteRestaurantButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tableId = selectedTable.getTable_id() + "";
				tableManagerController.editTableData("Delete", tableId, null, selectedRestaurant.getId() + "");
				tableSeatsTableData = tableManagerController.getTableDataAsMatrix(selectedRestaurant);
				defaultTableModel.setDataVector(tableSeatsTableData, columnNames);
				defaultTableModel.fireTableDataChanged();

			}
		});

	}

	private void prepareUpdateTableButtonActionListener(JButton updateRestaurantButton) {
		updateRestaurantButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String tableId = selectedTable.getTable_id() + "";
				Table newTable = new Table(Integer.valueOf(tableIdField.getText()),
						Integer.valueOf(tableSeatsNumberField.getText()));
				tableManagerController.editTableData("Update", tableId, newTable, selectedRestaurant.getId() + "");
				tableSeatsTableData = tableManagerController.getTableDataAsMatrix(selectedRestaurant);
				defaultTableModel.setDataVector(tableSeatsTableData, columnNames);
				defaultTableModel.fireTableDataChanged();
			}
		});
	}

	private void prepareCreateTableButtonActionListener(JButton createRestaurantButton) {
		createRestaurantButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Table newTable = new Table(Integer.valueOf(tableIdField.getText()),
						Integer.valueOf(tableSeatsNumberField.getText()));
				tableManagerController.editTableData("Create", null, newTable, selectedRestaurant.getId() + "");
				tableSeatsTableData = tableManagerController.getTableDataAsMatrix(selectedRestaurant);
				defaultTableModel.setDataVector(tableSeatsTableData, columnNames);
				defaultTableModel.fireTableDataChanged();
			}
		});
	}

	private void prepareTableItemListener(JTable restaurantJTable) {
		restaurantJTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRestaurantItemRow = restaurantJTable.getSelectedRow();
				if (selectedRestaurantItemRow != -1) {
					tableIdField.setText(tableSeatsTableData[selectedRestaurantItemRow][0]);
					selectedRestaurant.setName(tableSeatsTableData[selectedRestaurantItemRow][0]);
					tableSeatsNumberField.setText(tableSeatsTableData[selectedRestaurantItemRow][1]);
					selectedRestaurant.setAdress(tableSeatsTableData[selectedRestaurantItemRow][1]);
					selectedTable = new Table(Integer.valueOf(tableIdField.getText()),
							Integer.valueOf(tableSeatsNumberField.getText()));
				}
			}
		});
	}
}

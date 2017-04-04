package com.jaspreetdhanjan.screen;

import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.jaspreetdhanjan.menu.*;
import com.jaspreetdhanjan.menu.item.*;
import com.jaspreetdhanjan.session.*;
import com.jaspreetdhanjan.util.Format;

public class SessionScreen extends Screen {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 450;
	private static final String TITLE = BUSINESS_NAME + " Order System";

	private Menu menu;
	private Session session;
	private Order selectedOrder;

	private MenuScreen menuScreen;

	private JButton newOrderButton = new JButton("New order");
	private JButton removeOrderButton = new JButton("Remove order");
	private JButton renameOrderButton = new JButton("Rename order");
	private JButton payOrderButton = new JButton("Pay order");
	private JButton addItemButton = new JButton("Add item");
	private JButton removeItemButton = new JButton("Remove item");
	private JButton clearOrderButton = new JButton("Clear");
	private JButton luckyDipButton = new JButton("Lucky Dip");
	private JButton submitButton = new JButton("Complete");
	private JButton cancelButton = new JButton("Cancel");

	private JTextArea orderSummaryArea = new JTextArea();

	private JList<Order> orderList = new JList<Order>();
	private JList<MenuItem> itemList = new JList<MenuItem>();

	private DefaultListModel<Order> orderListModel = new DefaultListModel<Order>();
	private DefaultListModel<MenuItem> itemListModel = new DefaultListModel<MenuItem>();

	public SessionScreen(String tableNumber) {
		super(TITLE, WIDTH, HEIGHT);
		cp.setLayout(null);
		session = new Session(tableNumber);

		init();

		orderList.setModel(orderListModel);
		itemList.setModel(itemListModel);
		update(true);

		try {
			menu = MenuIO.load(MenuIO.MAIN_SAVE, false);
			menuScreen = new MenuScreen(this, menu);
		} catch (IOException e) {
			System.err.println("Failed to load the menu.");
			System.exit(0);
		}

		createWindow();
	}

	private void init() {
		int listWidth = 200;
		int bWidth = 120;
		int bHeight = 30;
		int bSpace = 30;
		int xs = WIDTH / 2;
		int pad = 25;
		int xEdge = 25;
		// int yEdge = 50;

		JLabel title = new JLabel("Build your orders for Table: " + session.getTableNumber(), SwingConstants.CENTER);
		title.setBounds(xs - 150, pad / 2, 300, 15);

		orderList.setBounds(xEdge, 50, listWidth, 125);
		JScrollPane orderScrollPane = new JScrollPane(orderList);
		orderScrollPane.setBounds(xEdge, 50, listWidth, 125);

		itemList.setBounds(WIDTH - bWidth - listWidth - pad * 2, 50, listWidth, 125);
		JScrollPane itemScrollPane = new JScrollPane(itemList);
		itemScrollPane.setBounds(WIDTH - bWidth - listWidth - pad * 2, 50, listWidth, 125);

		newOrderButton.setBounds(xEdge * 2 + listWidth, 50 + bSpace * 0, bWidth, bHeight);
		removeOrderButton.setBounds(xEdge * 2 + listWidth, 50 + bSpace * 1, bWidth, bHeight);
		renameOrderButton.setBounds(xEdge * 2 + listWidth, 50 + bSpace * 2, bWidth, bHeight);
		payOrderButton.setBounds(xEdge * 2 + listWidth, 50 + bSpace * 3, bWidth, bHeight);

		addItemButton.setBounds(WIDTH - bWidth - xEdge, 50 + bSpace * 0, bWidth, bHeight);
		removeItemButton.setBounds(WIDTH - bWidth - xEdge, 50 + bSpace * 1, bWidth, bHeight);
		luckyDipButton.setBounds(WIDTH - bWidth - xEdge, 50 + bSpace * 2, bWidth, bHeight);
		clearOrderButton.setBounds(WIDTH - bWidth - xEdge, 50 + bSpace * 3, bWidth, bHeight);

		orderSummaryArea.setBounds(xEdge, bSpace * 7, WIDTH - xEdge * 2, 140);
		orderSummaryArea.setEditable(false);

		submitButton.setBounds(xs - bWidth, 385, 117, 29);
		cancelButton.setBounds(xs, 385, 117, 29);

		addActionListeners();

		addComponent(title, orderScrollPane, itemScrollPane, newOrderButton, //
				removeOrderButton, renameOrderButton, payOrderButton, addItemButton, removeItemButton, luckyDipButton, //
				clearOrderButton, orderSummaryArea, submitButton, cancelButton);
	}

	private void addActionListeners() {
		orderList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				int index = orderList.getSelectedIndex();
				if (index != -1) {
					selectedOrder = session.getOrders().get(index);
					update(false);
				}
			}
		});

		newOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String orderPlacedBy = JOptionPane.showInputDialog(SessionScreen.this, "Give the name of order holder");
				if (orderPlacedBy == null) {
					return;
				} else if (orderPlacedBy.trim().isEmpty()) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please enter a valid name!");
					return;
				}

				session.addOrder(new Order(orderPlacedBy));
				update(true);
			}
		});

		removeOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please select an order to remove!");
					return;
				}

				session.removeOrder(selectedOrder);
				selectedOrder = null;
				update(true);
			}
		});

		renameOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please select an order to rename!");
					return;
				}

				String newOrderName = JOptionPane.showInputDialog("Enter the new name of the order holder");
				if (newOrderName == null) {
					return;
				} else if (newOrderName.trim().isEmpty()) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Incorrect. Please enter a valid name!");
					return;
				}

				selectedOrder.setOrderPlacedBy(newOrderName);
				update(true);
			}
		});

		payOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please select an order to pay for!");
					return;
				} else if (selectedOrder.isPaid()) {
					JOptionPane.showMessageDialog(SessionScreen.this, "This order has already been paid for.");
					return;
				}

				JOptionPane.showMessageDialog(SessionScreen.this, "A waiter or waitress will come and collect your payment. Please be ready.");
				selectedOrder.setPaid(true);
			}
		});

		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please select an order to add an item to!");
					return;
				}

				menuScreen.setVisible(true);
			}
		});

		removeItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuItem selectedItem = itemList.getSelectedValue();
				if (selectedItem == null) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please select an item to remove!");
					return;
				}

				selectedOrder.removeItem(selectedItem);
				selectedItem = null;
				update(true);
			}
		});

		luckyDipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please select an order to add a lucky dip to!");
					return;
				}

				MenuItem[] randomItems = menu.getRandomItems(new Random(), 4);
				for (int i = 0; i < randomItems.length; i++) {
					addToSelectedOrder(randomItems[i]);
				}
			}
		});

		clearOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedOrder == null) {
					JOptionPane.showMessageDialog(SessionScreen.this, "Please select an order to clear!");
					return;
				}

				selectedOrder.getItems().clear();
				update(true);
			}
		});

		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Order> unpaidOrders = session.getUnpaidOrders();

				// All people paid. Continue from here.
				if (unpaidOrders == null) {
					if (JOptionPane.showConfirmDialog(SessionScreen.this, "Are you sure you would like to confirm?", "Confirm orders", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(SessionScreen.this, "Thank you for ordering. Your orders should arrive soon. Reciept will now be printed.");

						final String reciept = session.getReciept();

						JTextArea textArea = new JTextArea();
						textArea.setFont(Format.recieptFont);
						textArea.setEditable(false);
						textArea.append(reciept);
						JOptionPane.showMessageDialog(SessionScreen.this, new JScrollPane(textArea));

						setScreen(new TitleScreen());
					}
				} else {
					int numberOfUnpaidOrders = unpaidOrders.size();

					// Notify that everyone did not pay. Otherwise shame them.
					if (numberOfUnpaidOrders == session.getOrders().size()) {
						JOptionPane.showMessageDialog(SessionScreen.this, "Please pay all orders before continuing.");
					} else {
						String names = "";
						for (int i = 0; i < numberOfUnpaidOrders; i++) {
							String name = unpaidOrders.get(i).getOrderPlacedBy();

							String connective = "";
							if (numberOfUnpaidOrders == 2 && i != numberOfUnpaidOrders - 1) connective = " and ";
							if (numberOfUnpaidOrders != 2 && i != numberOfUnpaidOrders - 1) connective = ", ";

							names += name + connective;
						}

						String ext = numberOfUnpaidOrders == 1 ? " has paid." : " have paid.";
						JOptionPane.showMessageDialog(SessionScreen.this, "Please make sure that " + names + ext);
					}
				}
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setScreen(new TitleScreen());
			}
		});
	}

	public void addToSelectedOrder(MenuItem item) {
		if (selectedOrder == null) return;
		selectedOrder.addItem(item);
		update(true);
	}

	/**
	 * Updates the boundary class (SessionScreen.java) to reflect the changes made to the control or entity classes.
	 * 
	 * @param updateOrders
	 *            update the Order List. Usually should be left as true.
	 */
	private void update(boolean updateOrders) {
		if (updateOrders) {
			orderListModel.clear();
			for (Order order : session.getOrders()) {
				orderListModel.addElement(order);
			}
		}

		itemListModel.clear();
		if (selectedOrder != null) {
			for (MenuItem item : selectedOrder.getItems()) {
				itemListModel.addElement(item);
			}
		}

		orderSummaryArea.setText(selectedOrder == null ? "" : selectedOrder.getSummary());
	}
}
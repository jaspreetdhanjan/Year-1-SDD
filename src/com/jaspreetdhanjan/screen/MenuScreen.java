package com.jaspreetdhanjan.screen;

import javax.swing.*;
import javax.swing.event.*;

import com.apple.eawt.Application;
import com.jaspreetdhanjan.menu.*;
import com.jaspreetdhanjan.menu.item.*;
import com.jaspreetdhanjan.util.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuScreen extends Screen {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 250;
	private static final String TITLE = BUSINESS_NAME + " Menu";

	private Menu menu;
	private MenuItem currentItem = null;

	private SessionScreen sessionScreen;

	private JPanel northPanel = new JPanel();
	private JPanel eastPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();
	private JPanel westPanel = new JPanel();

	private JButton addButton = new JButton("Add");
	private JButton cancelButton = new JButton("Cancel");
	private JButton searchButton = new JButton("Search");

	private JList<MenuItem> itemList = new JList<MenuItem>();
	private DefaultListModel<MenuItem> itemListModel = new DefaultListModel<MenuItem>();
	private final MenuIcon defaultIcon = ResourceLoader.loadIcon("/img/default.png");
	private JLabel imgPreviewLabel = new JLabel(defaultIcon);
	private JLabel title = new JLabel("Menu");
	private JTextArea itemDescriptionField = new JTextArea();

	private JCheckBox vegetarianCheck = new JCheckBox("Vegetarian");
	private JCheckBox glutenCheck = new JCheckBox("Gluten-Free");

	public MenuScreen(SessionScreen sessionScreen, Menu menu) {
		super(TITLE, WIDTH, HEIGHT);
		this.sessionScreen = sessionScreen;
		this.menu = menu;
		init();

		createWindow();
	}

	protected void createWindow() {
		Dimension d = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setResizable(false);
		setTitle(TITLE);
		setVisible(false);
		setIconImage(ResourceLoader.loadImage("/img/icon.png"));
		Application.getApplication().setDockIconImage(getIconImage());
	}

	private void init() {
		cp.setLayout(new BorderLayout(1, 1));

		menu.addItems(itemListModel, null);
		itemList.setModel(itemListModel);

		searchButton.setPreferredSize(new Dimension(220, 30));
		itemDescriptionField.setEditable(false);
		itemDescriptionField.setPreferredSize(new Dimension(220, 90));

		addListeners();

		northPanel.add(title);
		eastPanel.add(imgPreviewLabel);
		centerPanel.add(glutenCheck);
		centerPanel.add(vegetarianCheck);
		centerPanel.add(searchButton);
		centerPanel.add(Format.addToScrollPane(itemDescriptionField, new Dimension(220, 90)));
		southPanel.add(addButton);
		southPanel.add(cancelButton);
		westPanel.add(Format.addToScrollPane(itemList, new Dimension(200, 150)));

		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(eastPanel, BorderLayout.EAST);
		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);
		cp.add(westPanel, BorderLayout.WEST);
	}

	private void addListeners() {
		itemList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				MenuItem tmp = itemList.getSelectedValue();
				currentItem = tmp;
				if (tmp != null) {
					imgPreviewLabel.setIcon(tmp.getImage());
					itemDescriptionField.setText(tmp.getSummary());
				}
			}
		});

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshModel();
			}

			private void refreshModel() {
				itemListModel.clear();
				menu.addItems(itemListModel, new MenuSort(glutenCheck.isSelected(), vegetarianCheck.isSelected()));
			}
		});

		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (currentItem == null) {
					JOptionPane.showMessageDialog(MenuScreen.this, "Please select an order to add an item to!");
					return;
				}

				sessionScreen.addToSelectedOrder(currentItem);
				setVisible(false);
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}

	public void setVisible(final boolean visible) {
		super.setVisible(visible);
		if (visible) {
			toFront();
		}
	}
}
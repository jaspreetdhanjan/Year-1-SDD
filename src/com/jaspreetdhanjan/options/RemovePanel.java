package com.jaspreetdhanjan.options;

import com.jaspreetdhanjan.menu.*;
import com.jaspreetdhanjan.menu.item.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

public class RemovePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private Menu menu;
	private List<MenuItem> removedItems = new ArrayList<MenuItem>();

	private JList<MenuItem> itemList = new JList<MenuItem>();
	private DefaultListModel<MenuItem> menuItems = new DefaultListModel<MenuItem>();

	private JButton removeButton = new JButton("Remove");
	private JButton saveButton = new JButton("Save");
	private JLabel imgPreviewLabel = new JLabel();
	private JProgressBar progressBar = new JProgressBar();

	public RemovePanel() {
		super();
		setLayout(null);

		int xs = 400;
		int xd = 120;
		int yd = 30;
		int s = 150;

		JLabel titleLabel = new JLabel("Remove an item from the menu");
		JScrollPane itemScrollPane = new JScrollPane(itemList);
		itemScrollPane.setBounds(s, 110, 300, 150);

		titleLabel.setBounds(301, 6, 197, 16);
		itemList.setBounds(s, 110, 300, 150);
		saveButton.setBounds(xs, 350, xd, yd);
		removeButton.setBounds(xs - xd, 350, xd, yd);
		imgPreviewLabel.setBounds(xs + xs - s - s, 110, 150, 150);

		imgPreviewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		setActionListeners();

		add(titleLabel);
		add(itemScrollPane);
		add(saveButton);
		add(removeButton);
		add(imgPreviewLabel);

		progressBar.setBounds(xs - 75, 320, 150, 50);
		add(progressBar);
	}

	public void loadAll() {
		try {
			menu = MenuIO.load(MenuIO.MAIN_SAVE, false);
		} catch (IOException e) {
			e.printStackTrace();
		}

		menuItems.clear();
		menu.addItems(menuItems, null);
		itemList.setModel(menuItems);
	}

	private void setActionListeners() {
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);
				int index = itemList.getSelectedIndex();

				if (index != -1) {
					removedItems.add(menuItems.getElementAt(index));
					menuItems.remove(index);
				} else {
					JOptionPane.showMessageDialog(RemovePanel.this, "Please select a menu item to remove");
				}
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(40);
				for (MenuItem item : removedItems) {
					menu.remove(item);
				}
				removedItems.clear();

				trySave();
				progressBar.setValue(100);
			}
		});

		itemList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				MenuItem selected = itemList.getSelectedValue();
				if (selected != null) {
					imgPreviewLabel.setIcon(selected.getImage());
				}
			}
		});
	}

	private void trySave() {
		try {
			MenuIO.save(MenuIO.MAIN_SAVE, false, menu);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
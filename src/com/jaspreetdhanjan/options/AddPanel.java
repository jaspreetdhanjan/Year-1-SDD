package com.jaspreetdhanjan.options;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import com.jaspreetdhanjan.menu.item.*;
import com.jaspreetdhanjan.menu.*;
import com.jaspreetdhanjan.util.*;

public class AddPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private JComboBox<String> comboBox = new JComboBox<String>();

	private JTextField nameField = new JTextField();
	private JTextField costField = new JTextField();
	private JTextField caloriesField = new JTextField();
	private JTextField amountField = new JTextField();

	private JCheckBox vegetarianCheck = new JCheckBox("Is vegetarian?");
	private JCheckBox isGlutenFreeCheck = new JCheckBox("Is gluten-free?");

	private final MenuIcon defaultIcon = ResourceLoader.loadIcon("/img/default.png");
	private MenuIcon imgIcon = defaultIcon;
	private JLabel imgPreviewLabel = new JLabel(imgIcon);

	private JButton chooseImgLabel = new JButton("Choose");
	private JButton addMenuItemButton = new JButton("Add");
	private JButton clearMenuItemButton = new JButton("Clear");

	private JProgressBar progressBar = new JProgressBar();

	public AddPanel() {
		super();
		setLayout(null);

		JLabel titleLabel = new JLabel("Add an item to the menu");
		JLabel menuTypeLabel = new JLabel("Menu item type:");
		JLabel nameLabel = new JLabel("Name:");
		JLabel costLabel = new JLabel("Cost (£):");
		JLabel caloriesLabel = new JLabel("Caloric content:");
		JLabel amountLabel = new JLabel("Amount (in ml):");
		JLabel otherLabel = new JLabel("Other:");
		JLabel selectImageLabel = new JLabel("Select image:");
		JLabel infoLabel = new JLabel("Note: Copy image files to the img folder in the res directory before attatching.");

		titleLabel.setBounds(322, 6, 156, 16);

		menuTypeLabel.setBounds(38, 58, 106, 16);
		nameLabel.setBounds(99, 86, 45, 16);
		costLabel.setBounds(85, 114, 59, 16);
		caloriesLabel.setBounds(38, 142, 106, 16);
		amountLabel.setBounds(38, 170, 106, 16);
		otherLabel.setBounds(99, 198, 45, 16);
		selectImageLabel.setBounds(55, 265, 89, 16);
		infoLabel.setBounds(55, 296, 500, 16);

		add(titleLabel);
		add(menuTypeLabel);
		add(nameLabel);
		add(costLabel);
		add(caloriesLabel);
		add(amountLabel);
		add(otherLabel);
		add(selectImageLabel);
		add(infoLabel);

		comboBox.setBounds(200, 54, 100, 27);
		for (int i = 0; i < 4; i++) {
			comboBox.addItem(MenuItem.getTypeFromID(i));
		}

		nameField.setBounds(200, 81, 130, 26);
		nameField.setColumns(10);

		costField.setBounds(200, 109, 130, 26);
		costField.setColumns(10);

		caloriesField.setBounds(200, 137, 130, 26);
		caloriesField.setColumns(10);

		amountField.setBounds(200, 165, 130, 26);
		amountField.setEditable(false);
		amountField.setColumns(10);

		vegetarianCheck.setBounds(202, 198, 128, 23);
		isGlutenFreeCheck.setBounds(202, 225, 128, 23);

		imgPreviewLabel.setBounds(525, 86, 150, 149);
		chooseImgLabel.setBounds(200, 260, 117, 29);

		imgPreviewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

		int xs = 400;
		int xd = 120;
		int yd = 30;
		addMenuItemButton.setBounds(xs - xd, 350, xd, yd);
		clearMenuItemButton.setBounds(xs, 350, xd, yd);

		setActionListeners();

		add(comboBox);
		add(nameField);
		add(costField);
		add(caloriesField);
		add(amountField);
		add(vegetarianCheck);
		add(isGlutenFreeCheck);
		add(imgPreviewLabel);
		add(chooseImgLabel);
		add(addMenuItemButton);
		add(clearMenuItemButton);

		progressBar.setBounds(xs - 75, 320, 150, 50);
		add(progressBar);
	}

	private void setActionListeners() {
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (comboBox.getSelectedIndex() == MenuItem.DRINKS_ITEM) {
					amountField.setEditable(true);
				} else {
					amountField.setEditable(false);
				}
			}
		});

		chooseImgLabel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(new File("res/img"));

				int result = fc.showOpenDialog(AddPanel.this);
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					loadImg(selectedFile);
				}
			}

			private void loadImg(File file) {
				imgIcon = ResourceLoader.loadIcon("/img/" + file.getName());
				imgPreviewLabel.setIcon(imgIcon);
			}
		});

		addMenuItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				String costString = costField.getText();
				String caloriesString = caloriesField.getText();
				String amountString = amountField.getText();

				double cost = -1;
				int calories = -1;
				int amount = -1;
				boolean isVegetarian = vegetarianCheck.isSelected();
				boolean isGlutenFree = isGlutenFreeCheck.isSelected();

				try {
					cost = Double.parseDouble(costString);
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(AddPanel.this, "Please enter a numerical value for the cost.");
				}
				int icost = (int) (cost * 100);

				try {
					calories = Integer.parseInt(caloriesString);
				} catch (NumberFormatException exception) {
					JOptionPane.showMessageDialog(AddPanel.this, "Please enter a numerical value for the calories.");
				}

				if (amountField.isEditable()) {
					try {
						amount = Integer.parseInt(amountString);
					} catch (NumberFormatException exception) {
						JOptionPane.showMessageDialog(AddPanel.this, "Please enter a numerical value for the amount.");
					}
				}

				if (name.isEmpty() || costString.isEmpty() || caloriesString.isEmpty() || (amountString.isEmpty() && amountField.isEditable())) {
					JOptionPane.showMessageDialog(AddPanel.this, "Please fill empty parameters!");
					return;
				}

				MenuItem newItem = MenuItem.create(comboBox.getSelectedIndex(), name, icost, calories, isVegetarian, isGlutenFree, imgIcon, amount);
				if (newItem != null) {
					trySave(newItem);
				}
			}
		});

		clearMenuItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				progressBar.setValue(0);

				comboBox.setSelectedIndex(0);
				nameField.setText("");
				costField.setText("");
				caloriesField.setText("");
				isGlutenFreeCheck.setSelected(false);
				vegetarianCheck.setSelected(false);
				imgIcon = defaultIcon;
				imgPreviewLabel.setIcon(defaultIcon);
			}
		});
	}

	private void trySave(MenuItem newItem) {
		progressBar.setValue(40);

		try {
			Menu menu = MenuIO.load(MenuIO.MAIN_SAVE, false);
			menu.add(newItem);
			MenuIO.save(MenuIO.MAIN_SAVE, false, menu);
		} catch (IOException e) {
			e.printStackTrace();
		}

		progressBar.setValue(100);
	}
}
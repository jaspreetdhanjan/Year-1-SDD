package com.jaspreetdhanjan.screen;

import java.awt.*;
import com.jaspreetdhanjan.util.ResourceLoader;
import java.awt.event.*;

import javax.swing.*;

import com.jaspreetdhanjan.options.*;

public class TitleScreen extends Screen {
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 450;
	private static final String TITLE = BUSINESS_NAME + " Launcher";

	private JPanel northPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel southPanel = new JPanel();

	private JLabel logoLabel = new JLabel(new ImageIcon(ResourceLoader.loadImage("/img/logo.png")));
	private JTextArea informationLabel = new JTextArea(//
			"Welcome to Frank's Pizzeria.\n" + //
					"If you would like to build your order, please click the start button now.\n" + //
					"If you are an employee and would like to ammend the menu please click the options button." //
	);

	private JButton startButton = new JButton("Start");
	private JButton optionsButton = new JButton("Options");
	private JButton closeButton = new JButton("Close");

	public TitleScreen() {
		super(TITLE, WIDTH, HEIGHT);
		cp.setLayout(new BorderLayout(1, 1));
		informationLabel.setEditable(false);

		setActionListeners();

		northPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		northPanel.add(logoLabel);
		centerPanel.add(informationLabel);
		southPanel.add(startButton);
		southPanel.add(optionsButton);
		southPanel.add(closeButton);

		cp.add(northPanel, BorderLayout.NORTH);
		cp.add(centerPanel, BorderLayout.CENTER);
		cp.add(southPanel, BorderLayout.SOUTH);

		createWindow();
	}

	private void setActionListeners() {
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tableNumber = JOptionPane.showInputDialog("Please enter your table number");

				if (tableNumber == null) {
					return;
				} else if (tableNumber.trim().isEmpty()) {
					JOptionPane.showMessageDialog(TitleScreen.this, "Please enter a valid table number!");
					return;
				}

				setScreen(new SessionScreen(tableNumber));
			}
		});

		optionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPasswordField passwordField = new JPasswordField();
				int correct = JOptionPane.showConfirmDialog(TitleScreen.this, passwordField, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (correct == JOptionPane.OK_OPTION) {
					boolean success = new OptionsValidator().checkPassword(passwordField.getPassword());

					if (success) setScreen(new OptionsScreen());
					if (!success) JOptionPane.showMessageDialog(TitleScreen.this, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
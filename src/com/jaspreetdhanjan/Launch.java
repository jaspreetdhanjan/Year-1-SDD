package com.jaspreetdhanjan;

import javax.swing.*;

import com.jaspreetdhanjan.screen.*;

public class Launch {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		new TitleScreen();
	}
}
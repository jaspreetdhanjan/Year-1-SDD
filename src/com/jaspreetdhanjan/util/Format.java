package com.jaspreetdhanjan.util;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.*;

public class Format {
	public static final Font recieptFont = new Font("Monospaced", Font.PLAIN, 10);

	public static String getYes(boolean b, boolean capitalised) {
		return capitalised ? (b ? "Yes" : "No") : (b ? "yes" : "no");
	}

	public static String getCostString(int cost) {
		float costInPounds = cost / 100f;
		return "£" + costInPounds;
	}

	public static JComponent addToScrollPane(JComponent c, Dimension d) {
		JScrollPane scrollPane = new JScrollPane(c);
		scrollPane.setPreferredSize(d);
		return scrollPane;
	}
}
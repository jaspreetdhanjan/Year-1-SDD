package com.jaspreetdhanjan.menu;

import java.io.*;

import com.jaspreetdhanjan.menu.item.*;
import com.jaspreetdhanjan.util.ResourceLoader;

public class MenuIO {
	public static final String MAIN_SAVE = "save.dat";
	public static final String DEBUG_SAVE = "debug.dat";

	public static void save(String path, boolean verbose, Menu menu) throws IOException {
		if (verbose) System.out.println("Saving menu to -> " + path);

		DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File(path)));
		for (MenuItem item : menu.getItems()) {
			item.outputAttributes(dos);
		}

		dos.writeByte(-1);
		dos.close();
	}

	public static Menu load(String path, boolean verbose) throws IOException {
		if (verbose) System.out.println("Loading menu from -> " + path);
		Menu menu = new Menu();

		DataInputStream dis = new DataInputStream(new FileInputStream(new File(path)));
		int type = 0;
		while ((type = dis.readByte()) > -1) {
			if (type == MenuItem.STARTER_ITEM) {
				String name = dis.readUTF();
				int cost = dis.readInt();
				int calories = dis.readInt();
				boolean isVegetarian = dis.readBoolean();
				boolean isGlutenFree = dis.readBoolean();
				String imgPath = dis.readUTF();

				menu.add(new StarterItem(name, cost, calories, isVegetarian, isGlutenFree, ResourceLoader.loadIcon(imgPath)));
			} else if (type == MenuItem.MAIN_ITEM) {
				String name = dis.readUTF();
				int cost = dis.readInt();
				int calories = dis.readInt();
				boolean isVegetarian = dis.readBoolean();
				boolean isGlutenFree = dis.readBoolean();
				String imgPath = dis.readUTF();

				menu.add(new MainItem(name, cost, calories, isVegetarian, isGlutenFree, ResourceLoader.loadIcon(imgPath)));
			} else if (type == MenuItem.DESSERT_ITEM) {
				String name = dis.readUTF();
				int cost = dis.readInt();
				int calories = dis.readInt();
				boolean isVegetarian = dis.readBoolean();
				boolean isGlutenFree = dis.readBoolean();
				String imgPath = dis.readUTF();

				menu.add(new DessertItem(name, cost, calories, isVegetarian, isGlutenFree, ResourceLoader.loadIcon(imgPath)));
			} else if (type == MenuItem.DRINKS_ITEM) {
				String name = dis.readUTF();
				int cost = dis.readInt();
				int calories = dis.readInt();
				boolean isVegetarian = dis.readBoolean();
				boolean isGlutenFree = dis.readBoolean();
				String imgPath = dis.readUTF();
				int amount = dis.readInt();

				menu.add(new DrinksItem(name, cost, calories, isVegetarian, isGlutenFree, ResourceLoader.loadIcon(imgPath), amount));
			}
		}
		dis.close();
		return menu;
	}
}
package com.jaspreetdhanjan.menu.item;

import java.io.DataOutputStream;
import java.io.IOException;

import com.jaspreetdhanjan.util.Format;
import com.jaspreetdhanjan.util.MenuIcon;
import com.jaspreetdhanjan.util.ResourceLoader;

public abstract class MenuItem {
	public static final int STARTER_ITEM = 0;
	public static final int MAIN_ITEM = 1;
	public static final int DESSERT_ITEM = 2;
	public static final int DRINKS_ITEM = 3;

	private final int ID;

	private String name;
	private int cost;
	private int calories;
	private boolean isVegetarian;
	private boolean isGlutenFree;
	private MenuIcon image;

	public MenuItem(int ID, String name, int cost, int calories, boolean isVegetarian, boolean isGlutenFree, MenuIcon image) {
		this.ID = ID;
		this.name = name;
		this.cost = cost;
		this.calories = calories;
		this.isVegetarian = isVegetarian;
		this.isGlutenFree = isGlutenFree;

		if (image == null) {
			this.image = ResourceLoader.loadIcon("/img/default.png");
		} else {
			this.image = image;
		}
	}

	public void outputAttributes(DataOutputStream dos) throws IOException {
		dos.writeByte(getID());
		dos.writeUTF(getName());
		dos.writeInt(getCost());
		dos.writeInt(getCalories());
		dos.writeBoolean(isVegetarian());
		dos.writeBoolean(isGlutenFree());
		dos.writeUTF(getImage().getPath());
	}

	public String getSummary() {
		String result = "Item: " + toString() + "\n";
		result += "Calories: " + getCalories() + "\n";
		result += "Cost: " + Format.getCostString(getCost()) + "\n";
		result += "Vegetarian : " + Format.getYes(isVegetarian(), false) + "\n";
		result += "Gluten-Free: " + Format.getYes(isGlutenFree(), false);
		return result;
	}

	public final int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public int getCost() {
		return cost;
	}

	public int getCalories() {
		return calories;
	}

	public boolean isVegetarian() {
		return isVegetarian;
	}

	public boolean isGlutenFree() {
		return isGlutenFree;
	}

	public MenuIcon getImage() {
		return image;
	}

	public String toString() {
		return getTypeFromID(ID) + " - " + name;
	}

	public static final String getTypeFromID(int ID) {
		if (ID == STARTER_ITEM) return "Starter";
		if (ID == MAIN_ITEM) return "Main";
		if (ID == DESSERT_ITEM) return "Dessert";
		if (ID == DRINKS_ITEM) return "Drinks";
		return null;
	}

	public static final MenuItem create(int ID, String name, int cost, int calories, boolean isVegetarian, boolean isGlutenFree, MenuIcon image, int amount) {
		if (ID == MenuItem.STARTER_ITEM) return new StarterItem(name, cost, calories, isVegetarian, isGlutenFree, image);
		if (ID == MenuItem.MAIN_ITEM) return new MainItem(name, cost, calories, isVegetarian, isGlutenFree, image);
		if (ID == MenuItem.DESSERT_ITEM) return new DessertItem(name, cost, calories, isVegetarian, isGlutenFree, image);
		if (ID == MenuItem.DRINKS_ITEM) return new DrinksItem(name, cost, calories, isVegetarian, isGlutenFree, image, amount);
		return null;
	}
}
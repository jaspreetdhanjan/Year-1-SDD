package com.jaspreetdhanjan.menu.item;

import java.io.DataOutputStream;
import java.io.IOException;

import com.jaspreetdhanjan.util.Format;
import com.jaspreetdhanjan.util.MenuIcon;

public class DrinksItem extends MenuItem {
	private int amount;

	public DrinksItem(String name, int cost, int calories, boolean isVegetarian, boolean isGlutenFree, MenuIcon image, int amount) {
		super(DRINKS_ITEM, name, cost, calories, isVegetarian, isGlutenFree, image);
		this.amount = amount;
	}

	public void outputAttributes(DataOutputStream dos) throws IOException {
		super.outputAttributes(dos);
		dos.writeInt(getAmount());
	}

	public String getSummary() {
		String result = "Item: " + toString() + "\n";
		result += "Amount: " + getAmount() + "ml\n";
		result += "Calories: " + getCalories() + "\n";
		result += "Cost: " + Format.getCostString(getCost()) + "\n";
		result += "Vegetarian : " + Format.getYes(isVegetarian(), false) + "\n";
		result += "Gluten-Free: " + Format.getYes(isGlutenFree(), false);
		return result;
	}

	public int getAmount() {
		return amount;
	}
}
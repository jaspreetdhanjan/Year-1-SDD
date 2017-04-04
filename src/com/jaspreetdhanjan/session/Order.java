package com.jaspreetdhanjan.session;

import java.util.*;

import com.jaspreetdhanjan.menu.item.*;
import com.jaspreetdhanjan.util.Format;

public class Order {
	private String orderPlacedBy;

	private List<MenuItem> items = new ArrayList<MenuItem>();
	private boolean hasPaid = false;

	public Order(String orderPlacedBy) {
		this.orderPlacedBy = orderPlacedBy;
	}

	public void addItem(MenuItem item) {
		items.add(item);
	}

	public void removeItem(MenuItem item) {
		items.remove(item);
	}

	public String getSummary() {
		String result = toString() + " Summary\n";
		result += "\n-Total calories: " + getTotalCalories();
		result += "\n-Total cost: " + Format.getCostString(getTotalCost());
		result += isAllVegetarian() ? "\n-All items are vegetarian" : "\n-Some items are not vegetarian";
		result += isAllGlutenFree() ? "\n-All items are gluten-free" : "\n-Some items may contain gluten";
		result += isPaid() ? "\n-This order has been paid for" : "\n-This order has not been paid for";
		return result;
	}

	public int getTotalCalories() {
		int result = 0;
		for (MenuItem item : items) {
			result += item.getCalories();
		}
		return result;
	}

	public int getTotalCost() {
		int result = 0;
		for (MenuItem item : items) {
			result += item.getCost();
		}
		return result;
	}

	private boolean isAllVegetarian() {
		boolean b = true;
		for (MenuItem item : items) {
			b &= item.isVegetarian();
		}
		return b;
	}

	private boolean isAllGlutenFree() {
		boolean b = true;
		for (MenuItem item : items) {
			b &= item.isGlutenFree();
		}
		return b;
	}

	public void setOrderPlacedBy(String orderPlacedBy) {
		this.orderPlacedBy = orderPlacedBy;
	}

	public String getOrderPlacedBy() {
		return orderPlacedBy;
	}

	public List<MenuItem> getItems() {
		return items;
	}

	public boolean isPaid() {
		return hasPaid;
	}

	public void setPaid(boolean hasPaid) {
		this.hasPaid = hasPaid;
	}

	public String toString() {
		boolean endApostrophe = orderPlacedBy.substring(orderPlacedBy.length() - 1).equals("s");
		String result = endApostrophe ? orderPlacedBy + "' Order" : orderPlacedBy + "'s Order";
		if (isPaid()) result += " [PAID]";
		return result;
	}
}
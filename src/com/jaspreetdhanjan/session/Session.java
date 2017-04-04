package com.jaspreetdhanjan.session;

import java.time.LocalDateTime;
import java.util.*;

import com.jaspreetdhanjan.menu.item.MenuItem;
import com.jaspreetdhanjan.util.Format;

public class Session {
	private String tableNumber;
	private List<Order> orders = new ArrayList<Order>();

	public Session(String tableNumber) {
		this.tableNumber = tableNumber;
	}

	public void addOrder(Order order) {
		orders.add(order);
	}

	public void removeOrder(Order order) {
		orders.remove(order);
	}

	public String getReciept() {
		String text = "";
		text += "Reciept for Session at table: " + tableNumber + "\n";
		text += LocalDateTime.now().toString() + "\n\n";

		for (Order order : orders) {
			text += (order + "\n");
			for (MenuItem item : order.getItems()) {
				text += String.format("%-40s%s%n", item, Format.getCostString(item.getCost()));
			}
			text += "Order total: " + Format.getCostString(order.getTotalCost()) + "\n\n";
		}
		return text;
	}

	/**
	 * Gets paid orders.
	 * 
	 * @return a list of paid orders in the session. If no orders were paid, it returns null.
	 */

	public List<Order> getUnpaidOrders() {
		List<Order> tmp = new ArrayList<Order>();
		for (Order order : orders) {
			if (!order.isPaid()) tmp.add(order);
		}
		return tmp.isEmpty() ? (tmp = null) : tmp;
	}

	public String getTableNumber() {
		return tableNumber;
	}

	public List<Order> getOrders() {
		return orders;
	}
}
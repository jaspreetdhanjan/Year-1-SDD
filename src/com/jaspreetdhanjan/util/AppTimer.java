package com.jaspreetdhanjan.util;

public class AppTimer {
	private static long startTime, endTime, diff;

	public static void startTimer() {
		startTime = System.currentTimeMillis();
		endTime = diff = 0;
	}

	public static void endTimer() {
		endTime = System.currentTimeMillis();
		diff = endTime - startTime;

		System.out.println("The timer took " + diff + " milliseconds");
	}
}
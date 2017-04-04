package com.jaspreetdhanjan.options;

public class OptionsValidator {
	private static final String TEST_PASSWORD = "hello123";

	public boolean checkPassword(char[] p) {
		return String.valueOf(p).equals(TEST_PASSWORD);
	}
}
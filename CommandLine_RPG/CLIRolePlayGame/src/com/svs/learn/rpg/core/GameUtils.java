package com.svs.learn.rpg.core;

public class GameUtils {

	public static boolean isEmpty(String str) {
		return str == null || str.trim().equals("");
	}

	public static String displayText(String str) {
		return str == null ? "" : str.trim();
	}

	public static String displayText(Integer i) {
		return i == null ? "" : i.toString();
	}
}

package com.svs.learn.rpg.core;

import java.util.Scanner;

public class ConsoleManager {

	GameContext gameCtx;

	public ConsoleManager(GameContext ctx) {
		this.gameCtx = ctx;
	}

	public void refreshScreen() {

		clearScreenPlatform();

		for (char[] line : gameCtx.getTextBuffer()) {
			System.out.println(line);
		}
	}

	private void clearScreenPlatform() {

		try {
			clearCommand.start().waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * For windows
	 */
	private ProcessBuilder clearCommand = new ProcessBuilder().inheritIO().command("cmd", "/c", "cls");

	private static Scanner sc = new Scanner(System.in);

	public void readInput() {
		gameCtx.setConsoleInput(sc.nextLine());
	}

}

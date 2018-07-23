package com.svs.learn.rpg.worker;

import com.svs.learn.rpg.core.GameContext;
import com.svs.learn.rpg.core.GameUtils;
import com.svs.learn.rpg.state.GameCharacter;

public class CharacterDefineWorker implements GameWorker {

	GameCharacter gameChar = new GameCharacter();

	boolean savePrompt;

	@Override
	public void execute(GameContext ctx) {

		ctx.getTextBuffer().clear();

		String input = ctx.getConsoleInput();

		if (!GameUtils.isEmpty(input)) {

			if (GameUtils.isEmpty(gameChar.getName())) {
				if (validName(input)) {
					gameChar.setName(input);
				} else {
					ctx.getTextBuffer().insert(9, "Error: Name is not acceptable.");
				}
			} else if (gameChar.getBirthYear() == null) {
				if (validBirthYear(input)) {
					gameChar.setBirthYear(Integer.parseInt(input));
				} else {
					ctx.getTextBuffer().insert(9, "Error: Birth year is not acceptable.");
				}

			} else if (GameUtils.isEmpty(gameChar.getGender())) {

				if (validGender(input)) {
					gameChar.setGender(input.toUpperCase());
				} else {
					ctx.getTextBuffer().insert(9, "Error: Gender is not acceptable.");
				}
			}
		}

		if (GameUtils.isEmpty(gameChar.getName())) {
			ctx.getTextBuffer().insert(10, "Enter Name  (50)         : ");
		} else if (gameChar.getBirthYear() == null) {
			ctx.getTextBuffer().insert(10, "Enter Birth Year (yyyy)  :");
		} else if (GameUtils.isEmpty(gameChar.getGender())) {
			ctx.getTextBuffer().insert(10, "Enter Gender (M/F)       :");
		} else if (!savePrompt) {
			ctx.getTextBuffer().insert(10, "Save ? [Y/N]             :");
			savePrompt = true;
		} else {

			if ("Y".equalsIgnoreCase(input)) {
				ctx.setStateAttr(GameCharacter.NAME, gameChar);
			}
			ctx.getFlowCtrl().setOffLoad(true);
		}

		disp(gameChar, ctx);

	}

	private void disp(GameCharacter gameChar, GameContext ctx) {
		ctx.getTextBuffer().insert(3, "	Define Game Character");
		ctx.getTextBuffer().insert(5, "	Name       : " + GameUtils.displayText(gameChar.getName()));
		ctx.getTextBuffer().insert(6, "	Birth Year : " + GameUtils.displayText(gameChar.getBirthYear()));
		ctx.getTextBuffer().insert(7, "	Gender     : " + GameUtils.displayText(gameChar.getGender()));

	}

	private boolean validGender(String n) {
		return n.matches("^[M|F|m|f]$");
	}

	private boolean validBirthYear(String n) {
		return n.matches("^\\d{4,4}$");
	}

	private boolean validName(String n) {
		return n.length() < 50;
	}
}

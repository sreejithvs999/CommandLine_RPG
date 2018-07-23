package com.svs.learn.rpg.worker;

import com.svs.learn.rpg.core.GameContext;
import com.svs.learn.rpg.core.GameUtils;
import com.svs.learn.rpg.state.GameCharacter;

public class MainStartWorker implements GameWorker {

	boolean option;

	String preCommand;

	@Override
	public void execute(GameContext ctx) {

		ctx.getTextBuffer().clear();

		if ("START".equalsIgnoreCase(preCommand)) {
			preCommand = null;
			ctx.getFlowCtrl().setNextWorker(new EntryGateWorker());
			return;
		}

		String input = ctx.getConsoleInput();

		GameCharacter character = ctx.getStateAttr(GameCharacter.NAME, GameCharacter.class);
		if (character == null) {
			ctx.getFlowCtrl().setNextWorker(new CharacterDefineWorker());

		} else {
			disp(character, ctx);
		}

		if ("START".equalsIgnoreCase(input)) {

			ctx.getTextBuffer().clear();
			ctx.getTextBuffer().fill(5, 5,
					"You are going to start the journey. You will be soon taken to the entry gate"
							+ ". From there you will get instruction on what to do next. You may have choices sometimes. "
							+ "What you choose can change your life. Good luck !!" + "\r\n\r\n\r\n\r\n"
							+ "-- Press any key to continue ...");
			preCommand = "START";

		} else if ("BACK".equalsIgnoreCase(input)) {
			ctx.getFlowCtrl().setOffLoad(true);
		}

	}

	private void disp(GameCharacter gameChar, GameContext ctx) {
		ctx.getTextBuffer().insert(3, "Start game with character ");
		ctx.getTextBuffer().insert(5, "Name       : " + GameUtils.displayText(gameChar.getName()));
		ctx.getTextBuffer().insert(6, "Birth Year : " + GameUtils.displayText(gameChar.getBirthYear()));
		ctx.getTextBuffer().insert(7, "Gender     : " + GameUtils.displayText(gameChar.getGender()));
		ctx.getTextBuffer().insert(10, "____________________________________________________________");
		ctx.getTextBuffer().insert(13, "-- Commands --");
		ctx.getTextBuffer().insert(14, "START   - Start Journey.");
		ctx.getTextBuffer().insert(15, "BACK    - Go back.");

	}
}

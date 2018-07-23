package com.svs.learn.rpg.worker;

import com.svs.learn.rpg.core.GameContext;
import com.svs.learn.rpg.core.GameTextBuffer;
import com.svs.learn.rpg.state.EntryGate;
import com.svs.learn.rpg.state.GameCharacter;

public class EntryGateWorker implements GameWorker {

	String preCommand;
	boolean enterMenu;
	boolean playerIn;

	@Override
	public void execute(GameContext ctx) {

		ctx.getTextBuffer().clear();

		String input = ctx.getConsoleInput();

		EntryGate entryGate = ctx.getStateAttr(EntryGate.class);
		if (entryGate == null) {
			entryGate = ctx.createAndGet(EntryGate.class);
		}

		GameCharacter player = ctx.getStateAttr(GameCharacter.class);
		if (player == null) {
			ctx.getFlowCtrl().setOffLoad(true);
			return;
		}

		if (!playerIn && "EAT".equalsIgnoreCase(input)) {// player can try eat

			if (player.feed(3)) {
				ctx.getTextBuffer().insert(30, "Got energy (3) by Pizza.");
			} else {
				ctx.getTextBuffer().insert(30, "Cant eat, not enough money");
			}
		} else if (!playerIn && "DRINK".equalsIgnoreCase(input)) { // player can try drink

			if (player.feed(2)) {
				ctx.getTextBuffer().insert(30, "Got energy (2) by apple juice");
			} else {
				ctx.getTextBuffer().insert(30, "Can't eat, not enough money");
			}
		} else if ("BACK".equalsIgnoreCase(input)) { // player can always go back

			if (playerIn) {// back to out of gate
				playerIn = false;
			} else { // back to previous stage
				ctx.getFlowCtrl().setOffLoad(true);
				return;
			}

		} else if (!playerIn && enterMenu && "ENTER".equalsIgnoreCase(input)) {// try to enter the gate

			if (player.work(5)) {
				playerIn = true;
			} else {
				ctx.getTextBuffer().insert(30, "Can't enter, more energy required.");
			}

		} else if (playerIn && "START".equalsIgnoreCase(input)) { // start journey inside the gate
			ctx.getFlowCtrl().setNextWorker(new JourneyWorker());
			return;
		}

		enterMenu = player.getEnergy() >= 5;
		ctx.getTextBuffer().drawMode();
		if (!playerIn) {// player is outside
			ctx.getTextBuffer().set(3, 4, player.getMyFigure());
		} else {
			ctx.getTextBuffer().set(27, 4, player.getMyFigure());
		}
		ctx.getTextBuffer().set(25, 3, entryGate.getFigure());
		ctx.getTextBuffer().overwriteMode();

		drawMenu(ctx.getTextBuffer());

	}

	void drawMenu(GameTextBuffer buff) {

		buff.insert(35, " -- Commands--");

		if (!playerIn) { // player is not inside the gate
			buff.insert(36, " EAT     -- Eats Pizza (Price $3).");
			buff.insert(37, " DRINK   -- Drinks Apple Juice (Price $2).");
		} else {
			buff.insert(37, " START   -- Start inside journey.");
		}
		buff.insert(38, " BACK    -- Go back.");

		if (!playerIn && enterMenu) {// player can enter the gate
			buff.insert(39, " ENTER   -- Open the gate and get in (Energy *5).");
		}
	}
}

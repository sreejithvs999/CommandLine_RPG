package com.svs.learn.rpg.worker;

import java.util.Random;

import com.svs.learn.rpg.core.GameContext;
import com.svs.learn.rpg.core.GameTextBuffer;
import com.svs.learn.rpg.state.EntryGate;
import com.svs.learn.rpg.state.GameCharacter;
import com.svs.learn.rpg.state.Monster;

public class JourneyWorker implements GameWorker {

	Integer playerPos = 0;

	@Override
	public void execute(GameContext ctx) {

		ctx.getTextBuffer().clear();
		String input = ctx.getConsoleInput();

		EntryGate entryGate = ctx.getStateAttr(EntryGate.class);
		if (entryGate == null) {
			ctx.getFlowCtrl().setOffLoad(true);
			return;
		}

		GameCharacter player = ctx.getStateAttr(GameCharacter.class);
		if (player == null) {
			ctx.getFlowCtrl().setOffLoad(true);
			return;
		}

		Monster monster = ctx.getStateAttr(Monster.class);
		if (monster == null) {
			monster = ctx.createAndGet(Monster.class);
		}

		ctx.getTextBuffer().insert(1, "Monster is there, fight or run");

		if ("BACK".equalsIgnoreCase(input)) {
			ctx.getFlowCtrl().setOffLoad(true);

		} else if ("RUN".equalsIgnoreCase(input)) {
			if (player.run(1)) {
				playerPos = new Random().nextInt(5);

				ctx.getTextBuffer().insert(2, "Running.. " + playerPos + "meters.");
			} else {
				ctx.getTextBuffer().insert(2, " No enough energy to run. Go back and eat/ drinks something.");
			}

		} else if ("ATTACK".equalsIgnoreCase(input)) {
			if (player.work(4)) {
				playerPos = (new Random()).nextInt(5);
				ctx.getTextBuffer().insert(2, "Attacking at position : " + playerPos);

				monster.moveZone();
				if (monster.hit(playerPos)) {
					ctx.getTextBuffer().insert(3, "Hooooy, You hit monster. Get Energy *25 at (" + playerPos + ")");
					player.reward(25);
				} else {
					ctx.getTextBuffer().insert(3, "Oooops, You missed ! at (" + playerPos + ")");
				}

			} else {
				ctx.getTextBuffer().insert(3, " No enough energy to fight. Go back and eat/ drinks something.");
			}
		}

		ctx.getTextBuffer().drawMode();

		ctx.getTextBuffer().set(3, 5, monster.getMyFigure());
		ctx.getTextBuffer().set(3 + (15 * playerPos), 15, player.getMyFigure());
		ctx.getTextBuffer().overwriteMode();

		drawMenu(ctx.getTextBuffer());

	}

	void drawMenu(GameTextBuffer buff) {

		buff.insert(35, " -- Commands -- ");
		buff.insert(37, " RUN        -- Run from monster ( spend Energy *1 and receive Money $1 ). ");
		buff.insert(38, " ATTACK     -- Attack monster ( spend Energy *4 and recieve Energy *25 if win). ");
		buff.insert(39, " BACK       -- Step backward.");
	}
}

package com.svs.learn.rpg.worker;

import com.svs.learn.rpg.core.GameContext;
import com.svs.learn.rpg.core.GameUtils;
import com.svs.learn.rpg.core.PersistanceManager;

public class MainWorker implements GameWorker {

	PersistanceManager persistanceMgr = new PersistanceManager();

	public MainWorker() {

	}

	@Override
	public void execute(GameContext ctx) {

		ctx.getTextBuffer().clear();

		String input = ctx.getConsoleInput();
		if (!GameUtils.isEmpty(input)) {
			if ("1".equals(input) || "start".equalsIgnoreCase(input)) {

				ctx.getFlowCtrl().setNextWorker(new MainStartWorker());

			} else if ("2".equals(input) || "load".equalsIgnoreCase(input)) {

				persistanceMgr.loadState(ctx);
				ctx.getTextBuffer().insert(10, "Loaded game states.");

			} else if ("3".equals(input) || "about".equalsIgnoreCase(input)) {

				ctx.getTextBuffer().insert(10, "About page Yet to implement");

			} else if ("4".equals(input) || "quit".equalsIgnoreCase(input)) {

				persistanceMgr.saveState(ctx);
				ctx.getFlowCtrl().setOffLoad(true);

			} else {
				ctx.getTextBuffer().insert(6, "Error: Wrong choice, please try again. ");
			}
		}
		drawMenu(ctx);

	}

	private void drawMenu(GameContext ctx) {
		String heading = "		Main Screen			";
		String optLine1 = "		1. Start			";
		String optLine2 = "		2. Load			";
		String optLine3 = "		3. About			";
		String optLine4 = "		4. Quit				";

		ctx.getTextBuffer().insert(1, heading);
		ctx.getTextBuffer().insert(2, optLine1);
		ctx.getTextBuffer().insert(3, optLine2);
		ctx.getTextBuffer().insert(4, optLine3);
		ctx.getTextBuffer().insert(5, optLine4);
	}
}

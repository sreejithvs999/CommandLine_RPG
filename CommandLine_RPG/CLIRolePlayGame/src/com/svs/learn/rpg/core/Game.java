package com.svs.learn.rpg.core;

import java.util.Deque;
import java.util.LinkedList;

import com.svs.learn.rpg.worker.GameWorker;
import com.svs.learn.rpg.worker.MainWorker;

public class Game {

	GameContext ctx = new GameContext();;

	Deque<GameWorker> gameWorkers = new LinkedList<>();

	ConsoleManager consoleMgr;

	public Game() {

		consoleMgr = new ConsoleManager(ctx);
		gameWorkers.add(new MainWorker());
	}

	public void start() {

		while (!gameWorkers.isEmpty()) {

			ctx.setFlowCtrl(new FlowControl());
			gameWorkers.peek().execute(ctx);

			FlowControl flowCtrl = ctx.getFlowCtrl();
			ctx.setConsoleInput(null);

			if (flowCtrl.shouldRefreshScreen()) {
				consoleMgr.refreshScreen();
			}

			if (flowCtrl.shouldOffLoad()) {
				gameWorkers.pop();
				continue;
			}

			GameWorker nextWorker = flowCtrl.getNextWorker();
			if (nextWorker != null) {
				gameWorkers.push(nextWorker);
				continue;
			}

			if (!flowCtrl.shouldSkipInput()) {
				consoleMgr.readInput();
			}
		}
	}

}

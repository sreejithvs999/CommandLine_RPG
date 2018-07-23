package com.svs.learn.rpg.core;

import com.svs.learn.rpg.worker.GameWorker;

public class FlowControl {

	private GameWorker nextWorker;
	private boolean offLoad;
	private boolean skipRefreshScreen;
	private boolean skipInput;

	public GameWorker getNextWorker() {
		return nextWorker;
	}

	public void setNextWorker(GameWorker nextWorker) {
		this.nextWorker = nextWorker;
	}

	public boolean shouldOffLoad() {
		return offLoad;
	}

	public void setOffLoad(boolean offLoad) {
		this.offLoad = offLoad;
	}

	public boolean shouldRefreshScreen() {
		return !skipRefreshScreen;
	}

	public void skipRefreshScreen(boolean skipRefreshScreen) {
		this.skipRefreshScreen = skipRefreshScreen;
	}

	public boolean shouldSkipInput() {
		return skipInput;
	}

	public void setSkipInput(boolean skipInput) {
		this.skipInput = skipInput;
	}

}

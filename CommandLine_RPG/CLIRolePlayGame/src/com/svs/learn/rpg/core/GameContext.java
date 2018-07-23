package com.svs.learn.rpg.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import com.svs.learn.rpg.state.StateObject;

public class GameContext {

	private GameTextBuffer gameTextBuffer = new GameTextBuffer(80, 40);
	private String consoleInput;
	private FlowControl flowCtrl;
	private Map<String, Object> gameStates = new HashMap<>();

	public void setConsoleInput(String consoleInput) {
		this.consoleInput = consoleInput;
	}

	public String getConsoleInput() {
		return consoleInput;
	}

	public GameTextBuffer getTextBuffer() {
		return this.gameTextBuffer;
	}

	public void setFlowCtrl(FlowControl flowCtrl) {
		this.flowCtrl = flowCtrl;
	}

	public FlowControl getFlowCtrl() {
		return flowCtrl;
	}

	public void setStateAttr(String key, Object value) {
		gameStates.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T createAndGet(Class<? extends T> type) {

		try {
			Object value = type.newInstance();
			String key = value instanceof StateObject ? ((StateObject) value).getObjectName() : type.getName();
			gameStates.put(key, value);
			return (T) value;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T getStateAttr(Class<? extends T> type) {

		try {
			String key = (String) (type.getDeclaredField("NAME") != null ? type.getDeclaredField("NAME").get(null)
					: type.getName());
			Object value = gameStates.get(key);
			if (type.isInstance(value)) {
				return (T) gameStates.get(key);
			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> T getStateAttr(String key, Class<? extends T> type) {

		Object value = gameStates.get(key);
		if (type.isInstance(value)) {
			return (T) gameStates.get(key);
		}
		return null;
	}

	public void removeStateAttr(String key) {
		gameStates.remove(key);
	}

	public void setGameStates(Map<String, Object> gameStates) {
		this.gameStates = gameStates;
	}

	public Map<String, Object> getGameStates() {
		return gameStates;
	}
}

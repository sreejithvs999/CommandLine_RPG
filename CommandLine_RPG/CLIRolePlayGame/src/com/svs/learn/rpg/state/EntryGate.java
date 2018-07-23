package com.svs.learn.rpg.state;

import java.io.Serializable;

import com.svs.learn.rpg.core.GameTextBuffer;

public class EntryGate implements StateObject, Serializable {

	private static final long serialVersionUID = 8265372836111076222L;
	
	public static final String NAME = "EntryGate";

	public GameTextBuffer getFigure() {
		GameTextBuffer buff = new GameTextBuffer(40, 24);
		buff.set(0, 0, Figures.GATE_DEFAULT);
		return buff;
	}

	@Override
	public String getObjectName() {
		return NAME;
	}
}

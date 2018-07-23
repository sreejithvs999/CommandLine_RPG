package com.svs.learn.rpg.state;

import java.io.Serializable;

import com.svs.learn.rpg.core.GameTextBuffer;

public class Monster implements StateObject, Serializable {

	private static final long serialVersionUID = 4817746617289370912L;

	public static final String NAME = "Monster";

	private Integer zone = 0;

	public void moveZone() {
		if (zone + 1 > 5) {
			zone = 0;
		} else {
			zone++;
		}
	}

	public boolean hit(Integer pos) {
		return zone.equals(pos);
	}

	public GameTextBuffer getMyFigure() {
		GameTextBuffer buff = new GameTextBuffer(70, 12);
		buff.set(zone * 10, 0, Figures.MONSTER);
		return buff;
	}

	@Override
	public String getObjectName() {
		return NAME;
	}
}

package com.svs.learn.rpg.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class PersistanceManager {

	private static final String FILE = "RPG_STATE.DAT";

	public void saveState(GameContext ctx) {

		File file = new File(FILE);
		if (file.exists()) {
			file.delete();
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

			oos.writeObject(ctx.getGameStates());
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public void loadState(GameContext ctx) {
		File file = new File(FILE);
		if (file.exists()) {

			try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {

				@SuppressWarnings("unchecked")
				Map<String, Object> map = (Map<String, Object>) ois.readObject();
				ctx.setGameStates(map);

			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}

	}
}

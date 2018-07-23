package com.svs.learn.rpg.core;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Rectangular Text buffer hold character data or figures. Support different way
 * to fill with texts.
 * 
 * @author Sreejith VS
 *
 */
public class GameTextBuffer implements Iterable<char[]> {

	private char[][] buffer;

	private final int WIDTH;
	private final int HEIGHT;

	boolean drawMode;

	public GameTextBuffer(int width, int height) {
		WIDTH = width;
		HEIGHT = height;
		buffer = new char[HEIGHT][WIDTH];

	}

	public void clear() {
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				buffer[i][j] = '\0';
			}
		}
	}

	public void set(int x, int y, char c) {
		if (y < HEIGHT && y >= 0) {
			if (x < WIDTH && x >= 0) {
				if (drawMode) {
					if (c != '\0' && !Character.isWhitespace(c)) {
						buffer[y][x] = c;
					}
				} else {
					buffer[y][x] = c;
				}

			}
		}
	}

	public void set(int x, int y, char[] data) {
		for (int i = 0, j = x; i < data.length; i++, j++) {
			char c = data[i];
			if('\n' != c) {
				set(j, y, c);
			} else {
				y++;
				j = x - 1;
			}
		}
	}

	public void set(int x, int y, String figure) {
		char[] data = figure.toCharArray();
		set(x, y, data);
	}

	public void set(int x, int y, GameTextBuffer figure) {
		for (char[] part : figure) {
			set(x, y++, part);
		}
	}

	public void insert(int y, String line) {
		if (HEIGHT > y) {
			System.arraycopy(line.toCharArray(), 0, buffer[y], 0, Math.min(WIDTH, line.length()));
		}
	}

	public void insert(int x, int y, String line) {
		if (HEIGHT > y && WIDTH > x) {
			System.arraycopy(line.toCharArray(), 0, buffer[y], x, WIDTH);
		}
	}

	public void fill(int x, int y, String text) {
		char[] data = text.toCharArray();

		for (int i = 0, j = x; i < data.length; i++, j++) {
			char c = data[i];
			set(j, y, c);
			if ('\n' == c || j >= WIDTH) {
				y++;
				j = -1;
			}
		}
	}

	public void drawMode() {
		drawMode = true;
	}

	public void overwriteMode() {
		drawMode = false;
	}

	public boolean isDrawMode() {
		return drawMode;
	}

	@Override
	public Iterator<char[]> iterator() {

		return new Iterator<char[]>() {

			int i = 0;

			@Override
			public char[] next() {
				return buffer[i++];
			}

			@Override
			public boolean hasNext() {
				return i < HEIGHT;
			}
		};
	}
}

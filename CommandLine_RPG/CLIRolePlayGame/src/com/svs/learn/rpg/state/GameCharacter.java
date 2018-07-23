package com.svs.learn.rpg.state;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.svs.learn.rpg.core.GameTextBuffer;

public class GameCharacter implements StateObject, Serializable {

	private static final long serialVersionUID = -2561256702154758586L;

	public static final String NAME = "Player";

	private String name;
	private Integer birthYear;
	private String gender;

	private Integer energy = 0;
	private Integer money = 100;
	private Integer age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
		age = GregorianCalendar.getInstance().get(Calendar.YEAR) - birthYear;
	}

	public Integer getAge() {
		return age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getEnergy() {
		return energy;
	}

	public void setEnergy(Integer energy) {
		this.energy = energy;
	}

	public GameTextBuffer getMyFigure() {

		GameTextBuffer myFigure = new GameTextBuffer(18, 18);

		if ("M".equals(gender)) {
			myFigure.set(0, 0, Figures.MAN_DEF);
		} else if ("F".equals(gender)) {
			myFigure.set(0, 0, Figures.WOMAN_DEF);
		} else {
			throw new IllegalStateException("Gender undefined.");
		}
		myFigure.set(6, 7, String.format("$%3d", money));
		myFigure.set(6, 8, String.format("*%3d", energy));

		return myFigure;
	}

	/**
	 * Eats by taking cost and adding energy.
	 * 
	 * @param cost
	 * @return true if success, false if fail
	 */

	public void reward(Integer energy) {
		this.energy += energy;
	}

	public boolean feed(Integer cost) {
		if (money - cost >= 0) {
			money -= cost;
			energy += cost;
			return true;
		}
		return false;
	}

	public boolean work(Integer cost) {
		if (energy - cost >= 0) {
			energy -= cost;
			return true;
		}
		return false;
	}

	public boolean run(Integer cost) {
		if (energy - cost >= 0) {
			energy -= cost;
			money += cost;
			return true;
		}
		return false;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}

	@Override
	public String getObjectName() {
		return NAME;
	}
}

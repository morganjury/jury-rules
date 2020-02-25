package com.jury.rules.evaluation;

import java.util.List;

public class Field<T> {

	public static final Field<String> NAME = new Field<>("Name", String.class);
	public static final Field<Integer> TIME_HOUR = new Field<>("Time_Hour", Integer.class);
	public static final Field<Integer> TIME_MINUTE = new Field<>("Time_Minute", Integer.class);
	public static final Field<Integer> TIME_SECOND = new Field<>("Time_Second", Integer.class);
	public static final Field<Double> PRICE = new Field<>("Price", Double.class);

	private String name;
	private Class<T> clazz;

	public Field(String name, Class<T> clazz) {
		this.name = name;
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public Class<T> getClazz() {
		return clazz;
	}

	public List<Operator> getOperators() {
		return (clazz.equals(Integer.class) || clazz.equals(Double.class))
				? Operator.getNumberOperators()
				: Operator.getStringOperators();
	}

	public boolean isNumber() {
		return clazz.equals(Integer.class) || clazz.equals(Double.class);
	}

	public boolean isString() {
		return clazz.equals(String.class);
	}

}

package com.jury.rules.evaluation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Operator {

	LESS_THAN(false, true),
	GREATER_THAN(false, true),
	STARTS_WITH(true, false),
	ENDS_WITH(true, false),
	EQUALS(true, true);

	private boolean appliesToStrings;
	private boolean appliesToNumbers;

	Operator(boolean appliesToStrings, boolean appliesToNumbers) {
		this.appliesToStrings = appliesToStrings;
		this.appliesToNumbers = appliesToNumbers;
	}

	public boolean isAppliesToStrings() {
		return appliesToStrings;
	}

	public boolean isAppliesToNumbers() {
		return appliesToNumbers;
	}

	public static List<Operator> getStringOperators() {
		return Arrays.stream(values()).filter(Operator::isAppliesToStrings).collect(Collectors.toList());
	}

	public static List<Operator> getNumberOperators() {
		return Arrays.stream(values()).filter(Operator::isAppliesToNumbers).collect(Collectors.toList());
	}

}

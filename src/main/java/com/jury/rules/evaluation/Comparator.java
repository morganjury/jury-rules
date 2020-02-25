package com.jury.rules.evaluation;

public interface Comparator<T> {

	boolean compare(T valueToEvaluate, T valueToEvaluateAgainst);

}

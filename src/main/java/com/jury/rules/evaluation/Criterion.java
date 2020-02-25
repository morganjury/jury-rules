package com.jury.rules.evaluation;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Criterion<T> implements Criteria {

	private Field<T> field;
	private Operator operator;
	private T value;

	public Criterion(Field<T> field, Operator operator, T value) {
		if (!field.getOperators().contains(operator)) {
			throw new IllegalArgumentException("Operator (" + operator.name() + ") not allowed for field (" + field.getName() + ")");
		}
		this.field = field;
		this.operator = operator;
		this.value = value;
	}

	public Field<T> getField() {
		return field;
	}

	public Operator getOperator() {
		return operator;
	}

	public T getValue() {
		return value;
	}

	public Set<Criteria> getCriteria() {
		return new HashSet<>(Collections.singletonList(this));
	}

	public Logic getLogic() {
		throw new UnsupportedOperationException();
	}

}

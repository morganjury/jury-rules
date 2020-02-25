package com.jury.rules;

import com.jury.rules.evaluation.*;
import com.jury.rules.exceptions.OperatorException;
import com.jury.rules.exceptions.RuleException;
import com.jury.rules.exceptions.UnknownLogicException;
import com.jury.rules.exceptions.UnknownOperatorException;
import com.jury.rules.parameters.Parameter;

public class Rule<R> {

	private Criteria criteria;
	private R response;

	public Rule(Criteria criteria, R response) {
		this.criteria = criteria;
		this.response = response;
	}

	public R evaluate(Parameter parameter) {
		if (evaluate(criteria, parameter)) {
			return response;
		} else {
			throw new RuleException();
		}
	}

	private <T> boolean evaluate(Criteria criteria, Parameter param) {
		// stop condition
		if (criteria instanceof Criterion) {
			Criterion<T> criterion = (Criterion<T>) criteria;
			return evaluateCriterion(criterion, param);
		}
		// recurse
		switch (criteria.getLogic()) {
			case AND:
				for (Criteria c : criteria.getCriteria()) {
					if (!evaluate(c, param)) {
						return false;
					}
				}
				return true;
			case OR:
				for (Criteria c : criteria.getCriteria()) {
					if (evaluate(c, param)) {
						return true;
					}
				}
				return false;
			default:
				throw new UnknownLogicException();
		}
	}

	private <T> boolean evaluateCriterion(Criterion<T> criterion, Parameter param) {
		Field<T> field = criterion.getField();
		T valueToEvaluate = param.getArg(field);
		T valueToEvaluateAgainst = criterion.getValue();
		Operator operator = criterion.getOperator();
		if (field.getOperators().contains(operator)) {
			Comparator<T> comparator = getComparator(field, operator);
			return comparator.compare(valueToEvaluate, valueToEvaluateAgainst);
		}
		throw new OperatorException("Operator " + operator.name() + " is not compatible with the field " + field.getName());
	}

	private <T> Comparator<T> getComparator(Field<T> field, Operator operator) {
		if (field.isNumber()) {
			switch (operator) {
				case LESS_THAN:
					return (a,b) -> Double.parseDouble(String.valueOf(a)) < Double.parseDouble(String.valueOf(b));
				case GREATER_THAN:
					return (a,b) -> Double.parseDouble(String.valueOf(a)) > Double.parseDouble(String.valueOf(b));
				case EQUALS:
					return (a,b) -> Double.parseDouble(String.valueOf(a)) == Double.parseDouble(String.valueOf(b));
			}
		}
		if (field.isString()) {
			switch (operator) {
				case STARTS_WITH:
					return (a,b) -> String.valueOf(a).startsWith(String.valueOf(b));
				case ENDS_WITH:
					return (a,b) -> String.valueOf(a).endsWith(String.valueOf(b));
				case EQUALS:
					return (a,b) -> String.valueOf(a).equals(String.valueOf(b));
			}
		}
		throw new UnknownOperatorException();
	}

}

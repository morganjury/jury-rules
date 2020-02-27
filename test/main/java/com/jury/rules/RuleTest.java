package com.jury.rules;

import com.jury.rules.evaluation.*;
import com.jury.rules.exceptions.RuleException;
import com.jury.rules.parameters.Parameter;
import com.jury.rules.parameters.ParameterMap;
import com.jury.rules.parameters.ParameterObject;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RuleTest {

	private static final String SUCCESS_MESSAGE = "SUCCESS";

	@Test
	public void validParameters() {
		iteratePriceTimeCriteria(getPriceTimeMap(-0.85, 12));
		iteratePriceTimeCriteria(getPriceTimeMap(-1.5, 12));
	}

	@Test(expected = RuleException.class)
	public void priceTooHigh() {
		iteratePriceTimeCriteria(getPriceTimeMap(-3.45, 12));
	}

	@Test(expected = RuleException.class)
	public void timeTooEarly() {
		iteratePriceTimeCriteria(getPriceTimeMap(-0.85, 9));
	}

	private ParameterMap<String, Double> getPriceTimeMap(double price, double time) {
		ParameterMap<String, Double> parameter = new ParameterMap<>();
		parameter.put(Field.PRICE.getName(), price);
		parameter.put(Field.TIME_HOUR.getName(), time);
		return parameter;
	}

	private <T> void iteratePriceTimeCriteria(Parameter<T> parameter) {
		// price >= -1.5
		Criterion<Double> priceCriteria1 = new Criterion<>(Field.PRICE, Operator.GREATER_THAN, -1.5);
		Criterion<Double> priceCriteria2 = new Criterion<>(Field.PRICE, Operator.EQUALS, -1.5);
		CriterionWrapper priceCriteriaWrapper = new CriterionWrapper(new HashSet<>(Arrays.asList(priceCriteria1, priceCriteria2)), Logic.OR);
		// time_hour >= 11
		Criterion<Integer> timeCriteria1 = new Criterion<>(Field.TIME_HOUR, Operator.GREATER_THAN, 11);
		Criterion<Integer> timeCriteria2 = new Criterion<>(Field.TIME_HOUR, Operator.EQUALS, 11);
		CriterionWrapper timeCriteriaWrapper = new CriterionWrapper(new HashSet<>(Arrays.asList(timeCriteria1, timeCriteria2)), Logic.OR);
		// price >= -1.5 AND time_hour >= 11
		CriterionWrapper criteriaWrapper = new CriterionWrapper(new HashSet<>(Arrays.asList(priceCriteriaWrapper, timeCriteriaWrapper)), Logic.AND);

		Rule<String> rule = new Rule<>(priceCriteriaWrapper, SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameter));

		rule = new Rule<>(timeCriteriaWrapper, SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameter));

		rule = new Rule<>(criteriaWrapper, SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameter));
	}

	public static class TestParameterObject extends ParameterObject<String> {
		private String variableName;
		public TestParameterObject(String variableName) {
			this.variableName = variableName;
		}
		public String getVariableName() {
			return variableName;
		}
	}

	@Test
	public void parameterObject() {
		Field<String> testField = new Field<>("variableName", String.class);
		TestParameterObject parameterObject = new TestParameterObject("TEST_VALUE");
		assertEquals("TEST_VALUE", parameterObject.getArg(testField));
		Criterion<String> criterion = new Criterion<>(testField, Operator.EQUALS, "TEST_VALUE");
		Rule<String> rule = new Rule<>(criterion, SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameterObject));
	}

	@Test
	public void stringEquals() {
		ParameterMap<String, String> parameterMap = new ParameterMap<>();
		parameterMap.put(Field.NAME.getName(), "someone@somewhere.com");
		Criterion<String> nameEquals = new Criterion<>(Field.NAME, Operator.EQUALS, "someone@somewhere.com");
		Rule<String> rule = new Rule<>(nameEquals, SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameterMap));

		parameterMap = new ParameterMap<>();
		parameterMap.put(Field.PRICE.getName(), "-1");
		Criterion<Double> priceIsOutgoing = new Criterion<>(Field.PRICE, Operator.LESS_THAN, 0.0);
		rule = new Rule<>(priceIsOutgoing, SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameterMap));

		parameterMap = new ParameterMap<>();
		parameterMap.put(Field.NAME.getName(), "someone@somewhere.com");
		parameterMap.put(Field.PRICE.getName(), "-1");
		rule = new Rule<>(new CriterionWrapper(new HashSet<>(Arrays.asList(nameEquals, priceIsOutgoing)), Logic.AND), SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameterMap));

		parameterMap = new ParameterMap<>();
		parameterMap.put(Field.NAME.getName(), "someone@somewhere.com");
		parameterMap.put(Field.PRICE.getName(), "1");
		rule = new Rule<>(new CriterionWrapper(new HashSet<>(Arrays.asList(nameEquals, priceIsOutgoing)), Logic.OR), SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameterMap));

		parameterMap = new ParameterMap<>();
		parameterMap.put(Field.NAME.getName(), "com");
		parameterMap.put(Field.PRICE.getName(), "-1");
		rule = new Rule<>(new CriterionWrapper(new HashSet<>(Arrays.asList(nameEquals, priceIsOutgoing)), Logic.OR), SUCCESS_MESSAGE);
		assertEquals(SUCCESS_MESSAGE, rule.evaluate(parameterMap));

		parameterMap = new ParameterMap<>();
		parameterMap.put(Field.NAME.getName(), "com");
		parameterMap.put(Field.PRICE.getName(), "-1");
		rule = new Rule<>(new CriterionWrapper(new HashSet<>(Arrays.asList(nameEquals, priceIsOutgoing)), Logic.AND), SUCCESS_MESSAGE);
		try {
			rule.evaluate(parameterMap);
		} catch (Exception e) {
			assertTrue(e instanceof RuleException);
		}
	}

}

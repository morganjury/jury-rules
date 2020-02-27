package com.jury.rules.evaluation;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.jury.rules.json.deserialize.CriteriaJsonDeserializer;

import java.util.Set;

@JsonDeserialize(using = CriteriaJsonDeserializer.class)
public interface Criteria {

	Set<Criteria> getCriteria();

	Logic getLogic();

}
/*
{
	field:
	operator:
	value:
}
{
	criterion: {
		field:
		operator:
		value:
	},
	criteria: {
		criterion: {
			field:
			operator:
			value:
		},
		criterion: {
			field:
			operator:
			value:
		},
		logic:
	},
	logic:
}
*/
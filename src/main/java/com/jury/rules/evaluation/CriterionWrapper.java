package com.jury.rules.evaluation;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Set;

public class CriterionWrapper implements Criteria {

	@JsonManagedReference
	private Set<Criteria> criteria;
	private Logic logic;

	public CriterionWrapper(Set<Criteria> criteria, Logic logic) {
		this.criteria = criteria;
		this.logic = logic;
	}

	public Set<Criteria> getCriteria() {
		return criteria;
	}

	public Logic getLogic() {
		return logic;
	}

}

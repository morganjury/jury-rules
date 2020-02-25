package com.jury.rules.evaluation;

import java.util.Set;

public interface Criteria {

	Set<Criteria> getCriteria();

	Logic getLogic();

}

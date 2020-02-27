package com.jury.rules.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.jury.rules.evaluation.CriterionWrapper;

import java.io.IOException;

public class CriterionWrapperDeserializer extends CriteriaJsonDeserializer {

	@Override
	public CriterionWrapper deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		return (CriterionWrapper) super.deserialize(jsonParser, deserializationContext);
	}

}

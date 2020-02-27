package com.jury.rules.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jury.rules.evaluation.CriterionWrapper;

import java.io.IOException;

public class CriterionWrapperJsonSerializer extends JsonSerializer<CriterionWrapper> {

	@Override
	public void serialize(CriterionWrapper criterionWrapper, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeObjectField("criteria", criterionWrapper.getCriteria());
		jsonGenerator.writeObjectField("logic", criterionWrapper.getLogic());
		jsonGenerator.writeEndObject();
	}

}

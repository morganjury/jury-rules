package com.jury.rules.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jury.rules.evaluation.Criterion;

import java.io.IOException;

public class CriterionJsonSerializer extends JsonSerializer<Criterion<?>> {

	@Override
	public void serialize(Criterion<?> criterion, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeObjectField("field", criterion.getField());
		jsonGenerator.writeObjectField("operator", criterion.getOperator());
		jsonGenerator.writeObjectField("value", criterion.getValue()); // TODO might need to reconsider type usage here
		jsonGenerator.writeEndObject();
	}

}

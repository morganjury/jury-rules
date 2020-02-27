package com.jury.rules.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jury.rules.Rule;

import java.io.IOException;

public class RuleJsonSerializer extends JsonSerializer<Rule<?>> {

	@Override
	public void serialize(Rule<?> rule, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeObjectField("criteria", rule.getCriteria());
		jsonGenerator.writeObjectField("response", rule.getResponse());
		jsonGenerator.writeEndObject();
	}

}

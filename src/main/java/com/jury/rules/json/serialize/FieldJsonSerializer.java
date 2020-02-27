package com.jury.rules.json.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.jury.rules.evaluation.Field;

import java.io.IOException;

public class FieldJsonSerializer extends JsonSerializer<Field<?>> {

	@Override
	public void serialize(Field<?> field, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeStringField("name", field.getName());
		jsonGenerator.writeObjectField("clazz", field.getClazz());
		jsonGenerator.writeEndObject();
	}

}

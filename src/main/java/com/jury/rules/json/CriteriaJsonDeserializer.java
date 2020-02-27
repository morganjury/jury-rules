package com.jury.rules.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jury.rules.evaluation.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CriteriaJsonDeserializer extends JsonDeserializer<Criteria> {

	// need to put transformers into their own project i.e. jury-transform

	@Override
	public Criteria deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
		return extractCriteria(rootNode);
	}

	protected static Criteria extractCriteria(JsonNode node) {
		if (!node.has("criteria")) {
			Field<String> field = null;
			Operator operator = null;
			String value = null;
			return new Criterion<>(field, operator, value);
		}
		Set<Criteria> criteriaSet = new HashSet<>();
		for (JsonNode n : node.get("criteria")) {
			criteriaSet.add(extractCriteria(n));
		}
		return new CriterionWrapper(criteriaSet, Logic.valueOf(node.get("logic").asText().toUpperCase()));
	}

}

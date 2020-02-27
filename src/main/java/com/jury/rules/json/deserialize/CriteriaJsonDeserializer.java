package com.jury.rules.json.deserialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.jury.rules.evaluation.*;
import com.jury.transform.impl.JsonTransformer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CriteriaJsonDeserializer extends JsonDeserializer<Criteria> {

	@Override
	public Criteria deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
		JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser);
		return extractCriteria(rootNode);
	}

	private Criteria extractCriteria(JsonNode node) {
		if (!node.has("criteria")) {
			String fieldJson = node.get("field").toString();
			JsonTransformer<Field> fieldJsonTransformer = new JsonTransformer<>(Field.class);
			Field<String> field = fieldJsonTransformer.produce(fieldJson);
			Operator operator = Operator.valueOf(node.get("operator").asText());
			String value = node.get("value").asText();
			return new Criterion<>(field, operator, value);
		}
		Set<Criteria> criteriaSet = new HashSet<>();
		for (JsonNode n : node.get("criteria")) {
			criteriaSet.add(extractCriteria(n));
		}
		return new CriterionWrapper(criteriaSet, Logic.valueOf(node.get("logic").asText().toUpperCase()));
	}

}

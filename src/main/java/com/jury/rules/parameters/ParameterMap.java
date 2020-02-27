package com.jury.rules.parameters;

import com.jury.rules.evaluation.Field;
import com.jury.rules.exceptions.FieldMissingException;

import java.util.HashMap;

public class ParameterMap<K, T> extends HashMap<K, T> implements Parameter<T> {

	@Override
	public T getArg(Field<T> field) throws FieldMissingException, ClassCastException {
		return (T) get(field.getName());
	}

}

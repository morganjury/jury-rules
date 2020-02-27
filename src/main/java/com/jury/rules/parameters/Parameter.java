package com.jury.rules.parameters;

import com.jury.rules.evaluation.Field;
import com.jury.rules.exceptions.FieldMissingException;

public interface Parameter<T> {

	T getArg(Field<T> field) throws FieldMissingException, ClassCastException;

}

package com.jury.rules.parameter;

import com.jury.rules.evaluation.Field;
import com.jury.rules.exception.FieldMissingException;

public interface Parameter<T> {

	T getArg(Field<T> field) throws FieldMissingException, ClassCastException;

}

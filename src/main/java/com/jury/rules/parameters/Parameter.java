package com.jury.rules.parameters;

import com.jury.rules.evaluation.Field;
import com.jury.rules.exceptions.FieldMissingException;

public interface Parameter {

	<V> V getArg(Field<V> field) throws FieldMissingException, ClassCastException;

}

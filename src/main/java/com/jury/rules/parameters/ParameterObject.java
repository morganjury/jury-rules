package com.jury.rules.parameters;

import com.jury.rules.evaluation.Field;
import com.jury.rules.exceptions.FieldMissingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParameterObject<T> implements Parameter<T> {

	@Override
	public T getArg(Field<T> field) throws FieldMissingException, ClassCastException {
		try {
			for (Method method : getClass().getMethods()) {
				if (method.getName().equalsIgnoreCase("get" + field.getName())) {
					// not possible to retrieve the type of the generic parameter T so must do an unchecked cast here
					return (T) method.invoke(this);
				}
			}
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		throw new FieldMissingException();
	}

}

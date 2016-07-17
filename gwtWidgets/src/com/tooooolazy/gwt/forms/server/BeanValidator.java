package com.tooooolazy.gwt.forms.server;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.tooooolazy.gwt.forms.shared.HasValidators;
import com.tooooolazy.gwt.forms.shared.RelativeValidator;
import com.tooooolazy.gwt.forms.shared.Validator;
import com.tooooolazy.util.TLZUtils;

/**
 * Server side utility class that validates beans.
 * <p>
 * Every bean that we need to validate, mus implement <code>HasValidators</code> interface
 * and have the getters representing the values we want to validate, annotated with <code>Validation</code> annotation.<br>
 * </p> 
 * <p>
 * Uses reflection to retrieve all annotated getters, create the required validator and invoke it.
 * If validation fails a RuntimeException of type <code>ValidatorException</code> is thrown.
 * </p> 
 * @author tooooolazy
 *
 * @see com.tooooolazy.gwt.forms.shared.HasValidators
 * @see com.tooooolazy.gwt.forms.shared.Validator
 * @see com.tooooolazy.gwt.forms.shared.ValidatorException
 * @see com.tooooolazy.gwt.forms.server.Validation
 */
public class BeanValidator {
	public static void validate(HasValidators bean) {
		Class dtoClass = bean.getClass();
		Method[] methods = dtoClass.getMethods();

		for(Method method : methods) {
			Annotation[] annotations = method.getAnnotations();

			for(Annotation annotation : annotations){
				if(annotation instanceof Validation){
					Validation validation = (Validation) annotation;
					Class vClass = validation.validatorClass();
					Validator validator = getValidator(method, vClass);
					if (validator instanceof RelativeValidator)
						validate(bean, method.getName(), validator, validation.related());
					else
						validate(bean, method.getName(), validator);
				}
			}
		}
	}

	/**
	 * @param bean
	 * @param method
	 * @param validator
	 * @param relMethod
	 */
	protected static void validate(HasValidators bean, String method, Validator validator, String relMethod) {
		try {
			Object value = TLZUtils.invokeMethod(bean, method, null);
			Object relValue = TLZUtils.invokeMethod(bean, relMethod, null);
			((RelativeValidator) validator).validate(value, relValue);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
	}

	/**
	 * @param bean
	 * @param method
	 * @param v
	 */
	protected static void validate(HasValidators bean, String method, Validator v) {
		try {
			v.validate(TLZUtils.invokeMethod(bean, method, null));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param method
	 * @param vClass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected static Validator getValidator(Method method, Class vClass) {
		try {
			return (Validator)TLZUtils.loadObject(vClass, new Object[] {method.getName()});
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

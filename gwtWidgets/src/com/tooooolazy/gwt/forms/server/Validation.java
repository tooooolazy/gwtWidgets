package com.tooooolazy.gwt.forms.server;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Provides validation support for DTO objects. Works along with <code>BeanValidator</code>.
 * If the validator class specified by the user is a <code>RelativeValidator</code> then the related field must also be specified
 * @author tooooolazy
 *
 * @see com.tooooolazy.gwt.forms.shared.HasValidators
 * @see com.tooooolazy.gwt.forms.shared.Validator
 * @see com.tooooolazy.gwt.forms.shared.RelativeValidator
 * @see com.tooooolazy.gwt.forms.shared.ValidatorException
 * @see com.tooooolazy.gwt.forms.server.BeanValidator
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Validation {
	Class validatorClass();
	String related() default "";
}

/**
 * 
 */
package ar.com.proevolutionit.efacturador.base.view.validator;

import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.data.Validator.InvalidValueException;

/**
 * @author svillarreal
 *
 */
@SuppressWarnings("serial")
public class CustomInvalidValueException extends InvalidValueException {

	/**
	 * @param message
	 */
	public CustomInvalidValueException(String messageKey, Object... args) {
		super(VaadinUtil.getMessage(messageKey, args));
	}

}

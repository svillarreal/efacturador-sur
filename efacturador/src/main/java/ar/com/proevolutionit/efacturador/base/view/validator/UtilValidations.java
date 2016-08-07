package ar.com.proevolutionit.efacturador.base.view.validator;

import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;

/**
 * @author svillarreal
 *
 */
public class UtilValidations {

	/**
	 * @return
	 */
	public static RegexpValidator validadorTelefono() {
		return new RegexpValidator("^[0-9]{1,20}$", VaadinUtil.getMessage("error.efacturador.cliente.telefono"));
	}

	/**
	 * @return
	 */
	public static RegexpValidator validadorNombreYApellido() {
		return new RegexpValidator("^[a-zA-Z '.üéâäàåçêëèïîìÄÅÉôöòûùÿÖÜáíóúñÑÁÂÀãÃÊËÈÍÎÏÌÓÔÒõÕÚÛÙýÝ]{5,60}$",
				VaadinUtil.getMessage("error.efacturador.cliente.nombreYApellido"));
	}

	/**
	 * @return
	 */
	public static RegexpValidator validadorEmail() {
		return new EmailValidator(VaadinUtil.getMessage("error.efacturador.cliente.mail"));
	}

}

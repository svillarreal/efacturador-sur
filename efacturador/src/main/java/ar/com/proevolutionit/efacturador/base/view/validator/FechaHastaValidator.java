package ar.com.proevolutionit.efacturador.base.view.validator;

import java.util.Date;

import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.data.Validator;
import com.vaadin.ui.DateField;

/**
 * @author svillarreal
 *
 */
@SuppressWarnings("serial")
public class FechaHastaValidator implements Validator {

	private DateField fechaDesdeField;

	/**
	 * @param fechaDesdeField
	 */
	public FechaHastaValidator(DateField field) {
		super();
		this.fechaDesdeField = field;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (fechaDesdeField != null && fechaDesdeField.getValue() != null) {
			if (value != null) {
				if (fechaDesdeField.getValue().compareTo((Date) value) > 0) {
					throw new InvalidValueException(VaadinUtil.getMessage("error.cotizacion.search.fechaHasta"));
				}
			}
		}

	}

}

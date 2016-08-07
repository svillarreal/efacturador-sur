package ar.com.proevolutionit.efacturador.base.view.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.data.Validator;

@Component
public class NumericValidator implements Validator {

	private static final long serialVersionUID = -3985609920543481798L;

	private String message;

	private final String ERROR_VALIDACION = "Debe ingresar un valor num\u00E9rico";

	private Integer rangoMinimo;

	private Integer rangoMaximo;

	public NumericValidator(String message, Object... args) {
		super();
		this.message = VaadinUtil.getMessage(message, args);
	}

	public NumericValidator() {
		super();
		this.message = ERROR_VALIDACION;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {

		if (!isValid(value)) {
			throw new InvalidValueException(message);
		} else {
			if (rangoMinimo != null && rangoMaximo != null && value != null) {
				Integer valor = (Integer) value;
				if (valor.compareTo(rangoMinimo) < 0 || valor.compareTo(rangoMaximo) > 0) {
					throw new InvalidValueException("El valor ingresado debe estar comprendido entre " + rangoMinimo + " y " + rangoMaximo);
				}
			}
		}

	}

	public boolean isValid(Object value) {
		if (value != null) {
			String valueString = String.valueOf(value);
			return StringUtils.isNumeric(valueString);
		} else {
			return true;
		}
	}

	public Integer getRangoMinimo() {
		return rangoMinimo;
	}

	public void setRangoMinimo(Integer rangoMinimo) {
		this.rangoMinimo = rangoMinimo;
	}

	public Integer getRangoMaximo() {
		return rangoMaximo;
	}

	public void setRangoMaximo(Integer rangoMaximo) {
		this.rangoMaximo = rangoMaximo;
	}

}

package ar.com.proevolutionit.efacturador.base.view.validator;

import java.math.BigDecimal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.topgroup.commons.vaadin.util.VaadinUtil;
import ar.com.proevolutionit.efacturador.base.view.util.CurrencyFormatterUtil;
import com.vaadin.data.Validator;

@Component
@Scope("prototype")
public class NumericBigDecimalValidator implements Validator {

	private static final long serialVersionUID = 1788913134645422302L;

	private final String ERROR_VALIDACION = "Debe ingresar un valor num\u00E9rico";

	private String message;

	private BigDecimal rangoMinimo;

	private BigDecimal rangoMaximo;

	public NumericBigDecimalValidator(String message, Object... args) {
		super();
		this.message = VaadinUtil.getMessage(message, args);
	}

	public NumericBigDecimalValidator() {
		super();
		this.message = ERROR_VALIDACION;
	}

	@Override
	public void validate(Object value) throws InvalidValueException {
		if (!isValid(value)) {
			throw new InvalidValueException(message);
		} else {
			if (rangoMinimo != null && rangoMaximo != null && value != null) {
				BigDecimal valor = (BigDecimal) value;
				if (valor.compareTo(rangoMinimo) < 0 || valor.compareTo(rangoMaximo) > 0) {
					throw new InvalidValueException("El valor ingresado debe estar comprendido entre " + CurrencyFormatterUtil.format(rangoMinimo)
							+ " y " + CurrencyFormatterUtil.format(rangoMaximo));
				}
			}
		}

	}

	public boolean isValid(Object value) {
		if (value != null) {
			try {
				CurrencyFormatterUtil.parse(value.toString());
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return true;
		}
	}

	public BigDecimal getRangoMinimo() {
		return rangoMinimo;
	}

	public void setRangoMinimo(BigDecimal rangoMinimo) {
		this.rangoMinimo = rangoMinimo;
	}

	public BigDecimal getRangoMaximo() {
		return rangoMaximo;
	}

	public void setRangoMaximo(BigDecimal rangoMaximo) {
		this.rangoMaximo = rangoMaximo;
	}

}

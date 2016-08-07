package ar.com.proevolutionit.efacturador.base.view.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import ar.com.proevolutionit.efacturador.base.business.util.Constants;

/**
 * @author svillarreal
 *
 */
public class CurrencyFormatterUtil {
	public static final String DECIMAL_SEPARATOR = ",";

	public static final String GROUPING_SEPARATOR = ".";

	/**
	 * Aplica el formato de moneda con separador de miles un punto y separador de decimales una coma.
	 * 
	 * @param value
	 *            valor a formatear
	 * @return cadena formateada.
	 */
	public static String format(Object value) {
		if (value != null) {
			return new DecimalFormat(Constants.MONEY_FORMAT, getSymbols()).format(value);
		}
		return null;
	}

	/**
	 * Parsea una cadena para convertirla a un numero.
	 * 
	 * @param value
	 *            cadena a parsear.
	 * @return numero.
	 * @throws ParseException
	 */
	public static Number parse(String value) throws ParseException {
		DecimalFormat decimalFormat = new DecimalFormat(Constants.MONEY_FORMAT, getSymbols());
		decimalFormat.setParseBigDecimal(true);
		return decimalFormat.parse(value);
	}

	private static DecimalFormatSymbols getSymbols() {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(DECIMAL_SEPARATOR.charAt(0));
		symbols.setGroupingSeparator(GROUPING_SEPARATOR.charAt(0));
		return symbols;
	}

	/**
	 * Convierte una cadena a un BigDecimal.
	 * 
	 * @param value
	 *            cadena a transformar.
	 * @return numero o null.
	 */
	public static BigDecimal valueOf(String value) {
		DecimalFormat decimalFormat = new DecimalFormat(Constants.MONEY_FORMAT, getSymbols());
		decimalFormat.setParseBigDecimal(true);
		Number parse;
		try {
			parse = decimalFormat.parse(value);
		} catch (ParseException e) {
			return null;
		}
		return new BigDecimal(parse.toString());
	}

}

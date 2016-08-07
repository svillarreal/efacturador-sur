package ar.com.proevolutionit.efacturador.base.view.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author svillarreal
 *
 */
public class DateFormatter {

	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}
}

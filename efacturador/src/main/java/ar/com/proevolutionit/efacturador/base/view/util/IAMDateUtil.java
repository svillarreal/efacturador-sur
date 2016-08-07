package ar.com.proevolutionit.efacturador.base.view.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.topgroup.commons.vaadin.util.VaadinUtil;

/**
 * @author svillarreal
 *
 */
public class IAMDateUtil {

	public static final SimpleDateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");

	public static String[] MESES = { VaadinUtil.getMessage("fopUtil.mes.1"), VaadinUtil.getMessage("fopUtil.mes.2"),
			VaadinUtil.getMessage("fopUtil.mes.3"), VaadinUtil.getMessage("fopUtil.mes.4"), VaadinUtil.getMessage("fopUtil.mes.5"),
			VaadinUtil.getMessage("fopUtil.mes.6"), VaadinUtil.getMessage("fopUtil.mes.7"), VaadinUtil.getMessage("fopUtil.mes.8"),
			VaadinUtil.getMessage("fopUtil.mes.9"), VaadinUtil.getMessage("fopUtil.mes.10"), VaadinUtil.getMessage("fopUtil.mes.11"),
			VaadinUtil.getMessage("fopUtil.mes.12") };

	public static String getMesDesc(int mes) {
		return mes >= 0 && mes < MESES.length ? MESES[mes] : VaadinUtil.getMessage("fopUtil.mes.error");
	}

	public static String getFechaEnTextoDescriptivo(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int mes = calendar.get(Calendar.MONTH);
		String mesStr = getMesDesc(mes);
		Integer dia = calendar.get(Calendar.DAY_OF_MONTH);
		Integer anio = calendar.get(Calendar.YEAR);
		String fechaStr = dia.toString() + " de " + mesStr + " de " + anio;
		return fechaStr;
	}

	public static String getFechaEnTextoDescriptivoLargo(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);
		int mes = calendar.get(Calendar.MONTH);
		String mesStr = getMesDesc(mes);
		Integer dia = calendar.get(Calendar.DAY_OF_MONTH);
		Integer anio = calendar.get(Calendar.YEAR);
		String dias = dia == 1 ? VaadinUtil.getMessage("cotizacion.view.constancia.dia") : VaadinUtil.getMessage("cotizacion.view.constancia.dias");
		String fechaStr = dia.toString() + " " + dias + " del mes de " + mesStr + " de " + anio;
		return fechaStr;
	}

	/**
	 * @param fecha
	 * @return
	 */
	public static String format(Date fecha) {
		return SDF.format(fecha);
	}

}

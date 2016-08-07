package ar.com.proevolutionit.efacturador.base.util;

/**
 * @author svillarreal
 *
 */
public class StringDataVerificator {

	public static boolean isNullOrEmpty(String s) {
		if (s != null) {
			if ((s = s.trim()).length() > 0) {
				return false;
			}
		}
		return true;
	}

	public static boolean isValidInteger(String val) {
		if (val != null && val.length() > 0) {
			for (char c : val.toCharArray()) {
				if (!Character.isDigit(c) && !("-".charAt(0) == c)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static boolean isValidFloat(String val, boolean decimalIsComma) {
		String[] spchk;
		if (val != null) {
			val = val.trim();

			if (val.length() < 1) {
				return false;
			}

			if (decimalIsComma) {
				spchk = val.split(",");
				if (spchk.length < 3 && spchk.length > 1) {
					if (spchk[1].contains(",")) {
						return false;
					}
				}
			} else {
				spchk = val.split("\\.");
				if (spchk.length < 3 && spchk.length > 1) {
					if (spchk[1].contains(".")) {
						return false;
					}
				}
			}
			val = val.trim();
			for (char c : val.toCharArray()) {
				if (!Character.isDigit(c) && (c != ",".charAt(0) && c != ".".charAt(0) && c != "-".charAt(0))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public static String capitalize(String s) {
		if (s != null) {
			if (s.length() == 0) {
				return s;
			}
			return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
		}
		return null;
	}

	public static String capitalizeFirstOfAll(String str) {
		if (str != null && str.length() > 0) {
			StringBuilder sb = new StringBuilder();
			String[] words = str.split("\\s");
			for (String s : words) {
				sb.append(capitalize(s));
				sb.append(" ");
			}
			return sb.toString().trim();
		}
		return str;
	}

	public static String removeLeftZeroes(String str) {
		if (str != null && str.length() > 0) {
			String aux = str;
			while (aux.length() > 0 && str.substring(0, 1).equals("0")) {
				aux = aux.substring(1);
			}
			return aux;
		}
		return str;
	}
}

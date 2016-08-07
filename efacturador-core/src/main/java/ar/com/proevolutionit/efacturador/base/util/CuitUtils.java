package ar.com.proevolutionit.efacturador.base.util;

/**
 * @author svillarreal
 *
 */
public class CuitUtils {

	public static final int VALIDATION_OK = 0;

	public static final int VALIDATION_BAD_FORMAT = 1;

	public static final int VALIDATION_BAD_PREFIX = 2;

	public static final int VALIDATION_BAD_CHECKSUM = 3;

	public static final int TYPE_PERSON_MALE = 0;

	public static final int TYPE_PERSON_FEMALE = 1;

	public static final int TYPE_SOCIETY = 2;

	public static final int[][] pfxRuleset = { { TYPE_PERSON_MALE, 20 },// 20-Hombres
			{ TYPE_PERSON_FEMALE, 27 },// 27-Mujeres
			{ TYPE_PERSON_MALE, 23 },// 23-Hombres
			{ TYPE_PERSON_FEMALE, 23 },// 23-Mujeres
			{ TYPE_PERSON_MALE, 24 },// 24-Hombres
			{ TYPE_PERSON_FEMALE, 24 },// 24-Mujeres
			{ TYPE_SOCIETY, 30 },// 30-Sociedades
			{ TYPE_SOCIETY, 33 },// 33-Sociedades
			{ TYPE_SOCIETY, 34 } // 34-Sociedades
	};

	public static int validateCUITL(String cuitl) {
		return validateCUITL(cuitl, null);
	}

	public static String getDNIFromCUITL(String cuitl) {
		if (validateCUITL(cuitl) == VALIDATION_OK) {
			cuitl = normalizeInput(cuitl);
			return cuitl.substring(2, cuitl.length() - 1);
		}
		return null;
	}

	private static String normalizeInput(String i) {
		if (i != null) {
			i = i.replaceAll("-", "");
			i = i.replaceAll(" ", "");
			i = i.replaceAll("\\.", "");
			return i;
		}
		return null;
	}

	/**
	 * @param cuitl
	 *            : Cuil/CUIt de la forma XX-XXXXXXXX-X, XXXXXXXXXX, XX.XXXXXXXX.X
	 * @param type
	 * @return VALIDATION_CONSTANT
	 */
	public static int validateCUITL(String cuitl, Integer type) {
		if (cuitl != null) {
			cuitl = normalizeInput(cuitl);
			if (cuitl.length() == 11) {
				if (StringDataVerificator.isValidInteger(cuitl)) {
					int pfx = Integer.valueOf(cuitl.substring(0, 2));
					if (type != null) {
						boolean found = false;

						for (int[] tuple : pfxRuleset) {
							if (tuple[0] == type && tuple[1] == pfx) {
								found = true;
								break;
							}
						}
						if (!found) {
							return VALIDATION_BAD_PREFIX;
						}
					}
					String pfs = cuitl.substring(0, 2);
					String npr = cuitl.substring(2, cuitl.length() - 1);
					if (cuitl.replaceAll("-", "").equals(generate(pfs, npr))) {
						return VALIDATION_OK;
					} else {
						return VALIDATION_BAD_CHECKSUM;
					}

				}
			}
		}
		return VALIDATION_BAD_FORMAT;
	}

	/**
	 * @param cuitl
	 *            : Cuil/CUIt de la forma XX-XXXXXXXX-X, XXXXXXXXXX, XX.XXXXXXXX.X
	 * @param type
	 * @return VALIDATION_CONSTANT
	 */
	public static int validateCUITLWithSex(String cuitl, Integer type) {
		if (cuitl != null) {
			cuitl = normalizeInput(cuitl);
			if (cuitl.length() == 11) {
				if (StringDataVerificator.isValidInteger(cuitl)) {
					int pfx = Integer.valueOf(cuitl.substring(0, 2));
					if (type != null) {
						boolean found = false;

						for (int[] tuple : pfxRuleset) {
							if (tuple[0] == type && tuple[1] == pfx) {
								found = true;
								break;
							}
						}
						if (!found) {
							return VALIDATION_BAD_PREFIX;
						}
					}
					String pfs = cuitl.substring(0, 2);
					String npr = cuitl.substring(2, cuitl.length() - 1);

					if (cuitl.equals(pfs + npr + generate(pfs, npr))) {
						return VALIDATION_OK;
					} else {
						return VALIDATION_BAD_CHECKSUM;
					}

				}
			}
		}
		return VALIDATION_BAD_FORMAT;
	}

	/**
	 *
	 * @param prefix
	 *            :Prefijo zona Codificación Entidad
	 * @param number
	 *            :Número de ID.
	 * @return
	 */
	public static String generate(String prefix, String number) {
		String suffix = "";
		int x = 0;
		int accumulator = 0;
		int f[] = { 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		String total = null;

		if (prefix != null && number != null && (prefix.length() == 2) && (number.length() == 8)) {
			total = prefix + number;
			for (int i = 0; i < 10; i++) {
				if (!Character.isDigit(total.charAt(i))) {
					return "";
				}
				accumulator = accumulator + (f[i] * Character.digit(total.charAt(i), 10));
			}
			x = 11 - (accumulator % 11);
			if (x == 11) {
				x = 0;
			} else if (x == 10) {
				x = 9;
			}
			suffix = String.valueOf(x);
		} else {
			return "";
		}
		return total + suffix;
	}

	/**
	 *
	 * @param type
	 * @param dninumber
	 * @return
	 */
	public static String generateFromSexType(int type, String dninumber) {
		String suffix = "";
		String rval = "";
		int x = 0;
		int accumulator = 0;
		int f[] = { 5, 4, 3, 2, 7, 6, 5, 4, 3, 2 };
		String total;

		String pfxA;
		if (type > -1 && dninumber != null && (String.valueOf(type).length() == 1) && (dninumber.length() == 8)) {
			switch (type) {
			case TYPE_PERSON_MALE:
				pfxA = String.valueOf(pfxRuleset[0][1]);
				break;
			case TYPE_PERSON_FEMALE:
				pfxA = String.valueOf(pfxRuleset[1][1]);
				break;
			default:
				return "";

			}
			total = pfxA + dninumber;
			for (int i = 0; i < 10; i++) {
				if (!Character.isDigit(total.charAt(i))) {
					return "";
				}
				accumulator = accumulator + (f[i] * Character.digit(total.charAt(i), 10));
			}
			int acc = (accumulator % 11);
			x = 11 - acc;

			if (acc < 2) {
				if (acc == 0) {
					suffix = "0";
				}
				if (acc == 1) {
					pfxA = "23";
					if (type == TYPE_PERSON_MALE) {
						suffix = "9";
					}
					if (type == TYPE_PERSON_FEMALE) {
						suffix = "4";

					}
				}
			} else {
				suffix = String.valueOf(x);
			}

		} else {
			return "";
		}
		return pfxA + dninumber + suffix;
	}

}

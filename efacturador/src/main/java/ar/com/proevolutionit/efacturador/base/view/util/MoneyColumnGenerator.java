package ar.com.proevolutionit.efacturador.base.view.util;

import java.math.BigDecimal;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnGenerator;

public class MoneyColumnGenerator implements ColumnGenerator {

	private static final long serialVersionUID = 2753036046231978668L;

	public Object generateCell(Table source, Object itemId, Object columnId) {
		// Get the object stored in the cell as a property
		Item item = source.getItem(itemId);
		if (item == null)
			return null;
		Property prop = item.getItemProperty(columnId);
		if (prop != null) {
			Object value = prop.getValue();
			if (value != null) {
				Class type = prop.getType();
				if (type.equals(BigDecimal.class)) {
					return CurrencyFormatterUtil.format(value);
				} else if (type.equals(String.class)) {
					try {
						BigDecimal toBigDecimal = new BigDecimal(value.toString());
						return CurrencyFormatterUtil.format(toBigDecimal);
					} catch (NumberFormatException e) {
						return null;
					}
				}
			}
		}
		return null;
	}

}

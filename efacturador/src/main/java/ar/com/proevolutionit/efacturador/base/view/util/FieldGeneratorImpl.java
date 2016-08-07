package ar.com.proevolutionit.efacturador.base.view.util;

import com.topgroup.commons.vaadin.view.form.FieldGenerator;
import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;

/**
 * @author svillarreal
 *
 */
public class FieldGeneratorImpl implements FieldGenerator {

	private Field field;

	public FieldGeneratorImpl(Field field) {
		super();
		this.field = field;
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		return field;
	}

}

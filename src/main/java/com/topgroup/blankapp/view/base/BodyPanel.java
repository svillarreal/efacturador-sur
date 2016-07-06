package com.topgroup.blankapp.view.base;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Panel;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class BodyPanel extends Panel {

	private static final long serialVersionUID = -3260555486686997009L;

	private com.vaadin.ui.Component component = null;

	public BodyPanel() {
		super();
		init();
	}

	private void init() {
		this.setWidth(100, UNITS_PERCENTAGE);
		this.setHeight(95, UNITS_PERCENTAGE);
		// Elimina el margen inferior
		((AbstractLayout) getContent()).setMargin(true, true, false, true);
	}

	public void setBody(com.vaadin.ui.Component aComponent) {
		if (component != null) {
			this.removeComponent(component);
		}

		this.component = aComponent;
		this.addComponent(aComponent);
	}

}

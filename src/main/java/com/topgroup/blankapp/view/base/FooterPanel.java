package com.topgroup.blankapp.view.base;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

import com.topgroup.commons.security.model.UserRepresentation;
import com.topgroup.commons.security.util.SecurityUtil;
import com.topgroup.commons.vaadin.util.VaadinUtil;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class FooterPanel extends GridLayout {

	private static final long serialVersionUID = -4264065575135591364L;

	public FooterPanel() {
		super(3, 1);
		init();
	}

	private void init() {
		this.setWidth(100, UNITS_PERCENTAGE);
		this.setHeight(25, UNITS_PIXELS);
		this.setSpacing(true);
		this.setMargin(false, true, false, true);

		UserRepresentation ur = (UserRepresentation) SecurityUtil.getUsuario();
		if (ur != null) {
			Label label = new Label(VaadinUtil.getMessage("user.logged.info", ur.getUsername(), ur.getProfileName()));
			addComponent(label, 0, 0);
			setComponentAlignment(label, Alignment.MIDDLE_CENTER);
		}

		Link link = new Link();
		link.setIcon(new ThemeResource("img/topgroup_web.jpg"));
		addComponent(link, 2, 0);
		setComponentAlignment(link, Alignment.MIDDLE_RIGHT);
	}

}

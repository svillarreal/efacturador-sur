
package com.topgroup.blankapp.view.base;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Link;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class HeaderPanel extends GridLayout {

	private static final long serialVersionUID = -8722545657596919242L;

	public HeaderPanel() {
		super(3, 1);
		init();
	}

	private void init() {
		this.setWidth(100, UNITS_PERCENTAGE);
		this.setHeight(80, UNITS_PIXELS);
		this.setSpacing(true);
		this.setMargin(false, true, false, true);

		Link link = new Link();
		link.setDescription("Blankapp 2.0");
		link.setIcon(new ThemeResource("img/logo-site.png"));
		addComponent(link, 0, 0);
		setComponentAlignment(link, Alignment.MIDDLE_LEFT);

		link = new Link();
		link.setDescription("Topgroup S.A.");
		link.setResource(new ExternalResource("http://www.topgroup.com.ar"));
		link.setTargetName("_blank");
		link.setIcon(new ThemeResource("img/logo-topgroup.png"));
		addComponent(link, 2, 0);
		setComponentAlignment(link, Alignment.MIDDLE_RIGHT);
	}

}

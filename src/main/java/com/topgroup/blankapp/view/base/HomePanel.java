package com.topgroup.blankapp.view.base;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class HomePanel extends HorizontalLayout {

	private static final long serialVersionUID = -5507539446432635966L;

	private Link homeLink;

	private Link linkLogout;

	public HomePanel() {
		super();
		init();
	}

	private void init() {

		setWidth(80, UNITS_PIXELS);
		setSpacing(true);
		setMargin(false, true, false, false);

		homeLink = new Link();
		homeLink.setDescription("Home");
		homeLink.setIcon(new ThemeResource("img/home.png"));
		addComponent(homeLink);

		linkLogout = new Link();
		linkLogout.setDescription("Logout");
		linkLogout.setIcon(new ThemeResource("img/logout.png"));
		addComponent(linkLogout);

		Link link = new Link();
		link.setDescription("Help");
		link.setIcon(new ThemeResource("img/help.png"));
		addComponent(link);
	}

	@Override
	public void attach() {

		homeLink.setResource(new ExternalResource(getApplication().getURL().toExternalForm() + "?restartApplication"));
		linkLogout.setResource(VaadinUtil.getRelativeResource(this, "/j_spring_security_logout"));

		super.attach();
	}

}

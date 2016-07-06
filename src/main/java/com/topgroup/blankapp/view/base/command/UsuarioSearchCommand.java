package com.topgroup.blankapp.view.base.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.ui.MenuBar.MenuItem;

import com.topgroup.blankapp.view.security.UserSearch;
import com.topgroup.commons.menu.ui.vaadin.command.TgCommand;
import com.topgroup.commons.vaadin.view.MainApplication;

@SuppressWarnings("serial")
@Component("usuario/search")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UsuarioSearchCommand implements TgCommand {

	private MainApplication application;

	@Autowired
	private UserSearch userSearch;

	@Override
	public void menuSelected(MenuItem selectedItem) {
		application.setBody(userSearch);
	}

	public void setApplication(MainApplication application) {
		this.application = application;
	}
}

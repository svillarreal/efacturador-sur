package com.topgroup.blankapp.view.base.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.blankapp.view.security.ChangePassForm;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("serial")
@Component("usuario/cambioDeClave")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class CambioClaveCommand extends BaseCommand {

	@Autowired
	protected ChangePassForm changePassForm;
	
	@Override
	public void menuSelected(MenuItem selectedItem) {
		changePassForm.init();
		window = changePassForm.createWindow();
		super.menuSelected(selectedItem);
	}
	
}

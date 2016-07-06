package com.topgroup.blankapp.view.base.command;

import com.topgroup.commons.menu.ui.vaadin.command.TgCommand;
import com.topgroup.commons.vaadin.view.MainApplication;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;

@SuppressWarnings("serial")
public class BaseCommand implements TgCommand {

	protected MainApplication application;
	
	protected Window window;
	
	@Override
	public void menuSelected(MenuItem selectedItem) {
		application.getMainWindow().addWindow(window);
	}

	@Override
	public void setApplication(MainApplication application) {
		this.application = application;		
	}

}

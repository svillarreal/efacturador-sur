/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.topgroup.blankapp.view.base;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.blankapp.view.security.ChangePassForm;
import com.topgroup.commons.vaadin.error.DefaultErrorHandler;
import com.topgroup.commons.vaadin.error.ErrorHandler;
import com.topgroup.commons.vaadin.util.MultiWindow;
import com.topgroup.commons.vaadin.view.MainApplication;
import com.vaadin.Application;
import com.vaadin.ui.Window;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class ApplicationMain extends Application implements ApplicationContextAware, MainApplication {

	private ErrorHandler errorHandler;

	private ApplicationContext applicationContext;

	/** Configuración para el blanqueo de password */
	@Autowired
	private ChangePassForm changePassForm;

	@Override
	public void init() {
		setTheme("applayout");

		if (errorHandler == null) {
			errorHandler = new DefaultErrorHandler(this);
		}

	}

	@Override
	public void setBody(com.vaadin.ui.Component component) {
		((MainWindow) getMainWindow()).setBody(component);
	}

	@Override
	public Window getWindow(String name) {

		Window window = super.getWindow(name);

		if (window == null) {
			window = registerWindow(name);
		}
		if (changePassForm.requiredChangePassword()) {
			changePassForm.init();
			Window subWindow = changePassForm.createWindow();
			subWindow.setClosable(false);
			window.addWindow(subWindow);
		}
		return window;
	}

	protected Window registerWindow(String name) {
		Window multiWindow = null;
		try {
			multiWindow = (Window) applicationContext.getBean(name, MultiWindow.class);
			if (((MultiWindow) multiWindow).isMainWindow()) {
				setMainWindow(multiWindow);
			} else {
				// Registra la ventana en la aplicación
				addWindow(multiWindow);
			}
		} catch (Exception e) {
			e.printStackTrace();
			multiWindow = null;
		}

		return multiWindow;
	}

	@Override
	public void terminalError(com.vaadin.terminal.Terminal.ErrorEvent event) {
		if (errorHandler.isHandler(event)) {
			errorHandler.terminalError(event);
		} else {
			super.terminalError(event);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	// Se desactiva la pantalla de que notifica que el tiempo de sesion ha expirado.
	public static SystemMessages getSystemMessages() {
		CustomizedSystemMessages messages = new CustomizedSystemMessages();
		messages.setSessionExpiredNotificationEnabled(false);
		messages.setCommunicationErrorNotificationEnabled(false);
		return messages;
	}

}

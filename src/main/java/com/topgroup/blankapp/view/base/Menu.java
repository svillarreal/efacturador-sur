package com.topgroup.blankapp.view.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import com.topgroup.commons.menu.ui.vaadin.TgMenuBar;
import com.vaadin.ui.Component;

@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Menu extends TgMenuBar {

	private static final long serialVersionUID = 9060978338749318148L;

	protected static final Logger logger = LoggerFactory.getLogger(TgMenuBar.class);

	public Menu() {
		super();
	}

	@Override
	protected void init() {
		super.init();
		this.setWidth(100, UNITS_PERCENTAGE);
		this.setHeight(20, UNITS_PIXELS);
	}

	@Autowired
	@Value("${security.configuration.systemId}")
	@Override
	public void setIdSystem(Integer idSystem) {
		this.idSystem = idSystem;
	}

	public Component getComponent(final String beanName) {
		Component component = null;
		try {
			component = (Component) applicationContext.getBean(beanName);
		} catch (Exception e) {
			logger.error("No se encontro el bean " + beanName, e);
		}
		return component;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}

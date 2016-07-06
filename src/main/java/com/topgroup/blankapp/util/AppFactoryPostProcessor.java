package com.topgroup.blankapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import com.topgroup.commons.vaadin.util.VaadinUtil;

@Component
public class AppFactoryPostProcessor implements BeanFactoryPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(AppFactoryPostProcessor.class);

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

		try {
			VaadinUtil.setMessageSource(beanFactory.getBean("messageSource", MessageSource.class));
		} catch (Exception e) {
			logger.error("No se pudo inicializar la propiedad messageSource de la clase VaadinUtil.", e);
		}

	}

}

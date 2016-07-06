package com.topgroup.blankapp.view.security;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.Item;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.PasswordField;

import com.topgroup.blankapp.view.security.bean.PasswordBean;
import com.topgroup.commons.security.service.IUserService;
import com.topgroup.commons.vaadin.callback.CallbackForm;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.FieldGenerator;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.panel.BaseFormPanel;

@SuppressWarnings("serial")
public abstract class BasePassForm<T extends PasswordBean> extends BaseFormPanel<T> implements CallbackForm {

	@Autowired
	protected IUserService userService;

	public BasePassForm(String caption) {
		super(caption);
	}

	@Override
	public void cancel(Event event) {
		closeVindow();
	}

	@Override
	protected void addFieldGenerator(PanelBeanForm<T> beanForm) {
		beanForm.addFieldGenerator("password", createPasswordField());
		beanForm.addFieldGenerator("confirmPassword", createPasswordField());
	}

	protected FieldGenerator createPasswordField() {
		return new FieldGenerator() {

			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				return new PasswordField();
			}
		};
	}

	@Override
	protected String getWindowWidth() {
		return "400px";
	}

	@Override
	protected boolean validate(PanelBeanForm<T> panelBeanForm) {

		boolean validForm = true;
		PasswordBean bean = panelBeanForm.getBeanItem().getBean();

		if (!bean.validarPassword()) {
			panelBeanForm.setComponentError(new UserError(VaadinUtil.getMessage("security.user.error.invalidConfirmPassword.notEquals")));
			validForm = false;
		}

		return validForm;
	}

}

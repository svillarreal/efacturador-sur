package com.topgroup.blankapp.view.security;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import com.topgroup.blankapp.view.security.bean.UserBean;
import com.topgroup.commons.vaadin.callback.CallbackForm;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.panel.BaseFormPanel;

@org.springframework.stereotype.Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UserDataForm extends BaseFormPanel<UserBean> implements CallbackForm {

	private static final String TITLE_PANEL = VaadinUtil.getMessage("user.view.title");

	private static final long serialVersionUID = -6241486511113847293L;

	@Override
	public void accept(Event event) {
		closeVindow();
	}

	@Override
	public void cancel(Event event) {
		accept(event);
	}

	@Override
	protected String[] getVisibleItemProperties() {
		return new String[] { "username", "profileName", "name", "lastName", "sector", "email", "phone", "fax" };
	}

	@Override
	protected String getTitlePanel() {
		return TITLE_PANEL;
	}

	@Override
	protected String getWindowWidth() {
		return "400px";
	}

	@Override
	protected int getModeForm() {
		return MODE_VIEW_FORM;
	}

	@Override
	protected void addFieldGenerator(PanelBeanForm<UserBean> beanForm) {
	}

	@Override
	protected PanelBeanForm<UserBean> createBeanForm(UserBean parameter, int mode) {

		@SuppressWarnings("serial")
		PanelBeanForm<UserBean> beanForm = new PanelBeanForm<UserBean>(parameter) {

			private Panel layoutLogin;

			private Panel layoutDatos;

			@Override
			protected void prepareLayout() {

				VerticalLayout verticalLayout = (VerticalLayout) getContent();
				verticalLayout.setMargin(false);
				verticalLayout.setSpacing(true);

				FormLayout layout = createLayoutPanel();
				layoutLogin = new Panel(VaadinUtil.getMessage("user.view.login.title"), layout);
				addComponent(layoutLogin);

				layout = createLayoutPanel();
				layoutDatos = new Panel(VaadinUtil.getMessage("user.view.data.title"), layout);
				addComponent(layoutDatos);
			}

			protected FormLayout createLayoutPanel() {
				FormLayout layout = new FormLayout();
				layout.setWidth(100, UNITS_PERCENTAGE);
				layout.setMargin(true, true, true, true);
				return layout;
			}

			@Override
			protected void attachField(Object propertyId, Field field) {
				String property = (String) propertyId;
				field.setWidth(100, UNITS_PERCENTAGE);
				if ("username".equals(property) || "profileName".equals(property)) {
					layoutLogin.addComponent(field);
				} else {
					layoutDatos.addComponent(field);
				}
			}

		};

		if (mode == MODE_VIEW_FORM) {
			// No ejecuta las validaciones del formulario
			beanForm.setJsr303Validate(false);
			beanForm.setReadOnly(true);
		}

		setStyleName(Reindeer.PANEL_LIGHT);

		return beanForm;
	}

}

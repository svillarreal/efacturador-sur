package com.topgroup.blankapp.view.security;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.UserError;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import com.topgroup.blankapp.view.security.bean.UserBean;
import com.topgroup.commons.security.exception.BusinessException;
import com.topgroup.commons.security.model.Profile;
import com.topgroup.commons.security.model.User;
import com.topgroup.commons.security.service.IProfileService;
import com.topgroup.commons.security.service.IUserService;
import com.topgroup.commons.vaadin.callback.CallbackForm;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.FieldGenerator;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.panel.BaseFormPanel;
import com.topgroup.commons.vaadin.view.panel.BaseSearchPanel;

@org.springframework.stereotype.Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class UserEditDataForm extends BaseFormPanel<UserBean> implements CallbackForm {

	private static final String[] DATOS_LOGIN_PROPERTIES = new String[] { "username", "profile", "password", "confirmPassword" };

	private static final String[] VISIBLE_EDIT_PROPERTIES = new String[] { "username", "profile", "name", "lastName", "sector", "email", "phone",
			"fax" };

	protected static final String[] VISIBLE_NEW_PROPERTIES = new String[] { "username", "profile", "password", "confirmPassword", "name", "lastName",
			"sector", "email", "phone", "fax" };

	private static final String TITLE_PANEL = VaadinUtil.getMessage("user.edit.title");

	private static final long serialVersionUID = -6241486511113847293L;

	@Autowired
	private IProfileService profileService;

	@Autowired
	private IUserService userService;

	private BaseSearchPanel<?> searchPanel;
	
	public void setSearchPanel(BaseSearchPanel<?> searchPanel) {
		this.searchPanel = searchPanel;
	}
	
	@Override
	public void accept(Event event) {

		UserBean userBean = form.getBeanItem().getBean();
		boolean isNew = userBean.isNew();

		User user = null;
		String message = null;
		if (isNew) {
			user = new User();
			user.setPassword(userBean.getPassword());
			message = "user.save.message.success";
		} else {
			user = userService.loadUser(userBean.getIdUser());
			message = "user.update.message.success";
		}

		user.setUsername(userBean.getUsername());
		user.setName(userBean.getName());
		user.setLastName(userBean.getLastName());
		user.setSector(userBean.getSector());
		user.setPhone(userBean.getPhone());
		user.setFax(userBean.getFax());
		user.setEmail(userBean.getEmail());

		try {
			userService.saveOrUpdate(user, userBean.getProfile().getId(), isNew);
		} catch (BusinessException e) {
			form.setComponentError(new UserError(VaadinUtil.getMessage(e.getErrorDescriptionKey(), e.getValues())));
			return;
		}

		if(searchPanel != null ) {
			searchPanel.search((ClickEvent) event);
			setSearchPanel(null);
		}
		
		Window owner = getWindow().getParent();
		closeVindow();
		VaadinUtil.showMessage(owner, VaadinUtil.getMessage(message, userBean.getUsername()));
	}
		
	@Override
	public void cancel(Event event) {
		closeVindow();
	}

	@Override
	protected String[] getVisibleItemProperties() {

		UserBean userBean = form.getBeanItem().getBean();
		String[] properties = null;
		if (userBean.isNew()) {
			properties = VISIBLE_NEW_PROPERTIES;
		} else {
			properties = VISIBLE_EDIT_PROPERTIES;
		}

		return properties;
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
	protected void addFieldGenerator(PanelBeanForm<UserBean> beanForm) {

		beanForm.addFieldGenerator("profile", new FieldGenerator() {

			@Override
			public Field createField(Item item, Object propertyId, com.vaadin.ui.Component uiContext) {
				ComboBox comboBox = new ComboBox();
				final BeanItemContainer<Profile> items = new BeanItemContainer<Profile>(Profile.class);
				items.addAll(profileService.findAllProfiles());
				comboBox.setContainerDataSource(items);
				comboBox.setItemCaptionPropertyId("name");
				return comboBox;
			}
		});

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
	protected boolean validate(PanelBeanForm<UserBean> panelBeanForm) {
		boolean validForm = true;
		UserBean userBean = form.getBeanItem().getBean();

		if (userBean.isNew()) {

			if (userService.loadUser(userBean.getUsername()) != null) {
				panelBeanForm.setComponentError(new UserError(VaadinUtil.getMessage("security.user.created.exists")));
				validForm = false;
			}

			if (!userBean.validarPassword()) {
				panelBeanForm.setComponentError(new UserError(VaadinUtil.getMessage("security.user.error.invalidConfirmPassword.notEquals")));
				validForm = false;
			}
		}

		return validForm;
	}

	@SuppressWarnings("serial")
	@Override
	protected PanelBeanForm<UserBean> createBeanForm(UserBean parameter, int mode) {

		PanelBeanForm<UserBean> beanForm = new PanelBeanForm<UserBean>(parameter) {

			private Panel layoutLogin;

			private Panel layoutDatos;

			@Override
			protected void prepareLayout() {

				VerticalLayout verticalLayout = (VerticalLayout) getContent();
				verticalLayout.setMargin(false);
				verticalLayout.setSpacing(true);

				FormLayout layout = createLayoutPanel();
				layoutLogin = new Panel(VaadinUtil.getMessage("user.edit.login.title"), layout);
				addComponent(layoutLogin);

				layout = createLayoutPanel();
				layoutDatos = new Panel(VaadinUtil.getMessage("user.edit.data.title"), layout);
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
				if (ArrayUtils.contains(DATOS_LOGIN_PROPERTIES, property)) {
					layoutLogin.addComponent(field);
				} else {
					layoutDatos.addComponent(field);
				}
			}

		};

		setStyleName(Reindeer.PANEL_LIGHT);

		return beanForm;
	}

}

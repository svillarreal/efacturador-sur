package com.topgroup.blankapp.view.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import com.topgroup.blankapp.view.security.bean.InhabilitarBean;
import com.topgroup.commons.security.model.UserRepresentation;
import com.topgroup.commons.security.service.IUserService;
import com.topgroup.commons.security.util.SecurityUtil;
import com.topgroup.commons.vaadin.callback.CallbackForm;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.FieldGenerator;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;
import com.topgroup.commons.vaadin.view.panel.BaseFormPanel;
import com.topgroup.commons.vaadin.view.panel.BaseSearchPanel;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class InhabilitarUserForm extends BaseFormPanel<InhabilitarBean> implements CallbackForm {

	protected static final String[] VISIBLE_ITEMS_COLUMNS = new String[] { "motivo" };

	private static final String TITLE_WINDOW_PANEL = VaadinUtil.getMessage("user.inhabilitar.title");

	private static final String TITLE_PANEL = VaadinUtil.getMessage("user.inhabilitar.panel.title");

	@Autowired
	private IUserService userService;

	private BaseSearchPanel<?> searchPanel;

	public InhabilitarUserForm() {
		super(TITLE_PANEL);
	}

	public void setSearchPanel(BaseSearchPanel<?> searchPanel) {
		this.searchPanel = searchPanel;
	}

	@Override
	public void accept(Event event) {
		InhabilitarBean bean = form.getBeanItem().getBean();

		userService.disableUser(bean.getIdUser(), (UserRepresentation) SecurityUtil.getUsuario(), bean.getMotivo());

		if( searchPanel != null ) {
			searchPanel.search((ClickEvent) event);
			setSearchPanel(null);
		}
		
		Window owner = getWindow().getParent();
		closeVindow();
		VaadinUtil.showMessage(owner, VaadinUtil.getMessage("user.inhabilitar.message.success", bean.getUsername()));
	}

	@Override
	public void cancel(Event event) {
		closeVindow();
	}

	@Override
	protected String[] getVisibleItemProperties() {
		return VISIBLE_ITEMS_COLUMNS;
	}

	@Override
	protected void addFieldGenerator(PanelBeanForm<InhabilitarBean> beanForm) {
		beanForm.addFieldGenerator("motivo", new FieldGenerator() {

			@Override
			public Field createField(Item item, Object propertyId, Component uiContext) {
				TextArea textArea = new TextArea();
				textArea.setRows(5);
				textArea.setColumns(20);
				return textArea;
			}
		});
	}

	@Override
	protected String getTitlePanel() {
		return TITLE_WINDOW_PANEL;
	}

	@Override
	protected String getWindowWidth() {
		return "350px";
	}

	@Override
	protected PanelBeanForm<InhabilitarBean> createBeanForm(InhabilitarBean parameter, int mode) {

		PanelBeanForm<InhabilitarBean> beanForm = new PanelBeanForm<InhabilitarBean>(parameter) {

			private VerticalLayout layout;

			@Override
			protected void prepareLayout() {
				layout = new VerticalLayout();
				layout.setMargin(false, false, true, false);
				layout.setSpacing(true);
				getContent().addComponent(layout);
			}

			@Override
			protected void attachField(Object propertyId, Field field) {
				layout.addComponent(field);
			}

		};

		return beanForm;
	}

}

package com.topgroup.blankapp.view.security;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.terminal.UserError;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Window;

import com.topgroup.blankapp.view.security.bean.ChangePassBean;
import com.topgroup.commons.security.exception.BusinessException;
import com.topgroup.commons.security.model.UserRepresentation;
import com.topgroup.commons.security.util.SecurityUtil;
import com.topgroup.commons.vaadin.util.VaadinUtil;
import com.topgroup.commons.vaadin.view.form.PanelBeanForm;

@org.springframework.stereotype.Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class ChangePassForm extends BasePassForm<ChangePassBean> {

	private static final long serialVersionUID = -1605422329108601749L;

	private static final String TITLE_PANEL = VaadinUtil.getMessage("user.changepass.title");

	private boolean verificarChangePassword = true;

	public ChangePassForm() {
		super(TITLE_PANEL);
	}

	public void init() {
		ChangePassBean bean = new ChangePassBean();
		bean.setUser(userService.loadUser(((UserRepresentation) SecurityUtil.getUsuario()).getId()));
		init(bean, this);
	}

	@Override
	protected String[] getVisibleItemProperties() {
		return new String[] { "username", "name", "lastName", "oldPassword", "password", "confirmPassword" };
	}

	@Override
	public void accept(Event event) {
		try {
			ChangePassBean bean = (ChangePassBean) form.getBeanItem().getBean();

			Integer idUser = bean.getUser().getId();
			userService.updateUserPwd(idUser, bean.getOldPassword(), bean.getPassword());

			SecurityUtil.setUsuario(userService.loadById(idUser));
			verificarChangePassword = true;

			Window owner = getWindow().getParent();
			closeVindow();
			VaadinUtil.showMessage(owner, VaadinUtil.getMessage("user.changepass.message.success", bean.getUsername()));

		} catch (BusinessException e) {
			logger.debug("Error al intentar cambiar el password", e);
			form.setComponentError(new UserError(VaadinUtil.getMessage(e.getErrorDescriptionKey())));
		}
	}

	public boolean requiredChangePassword() {
		boolean result = verificarChangePassword && SecurityUtil.userHasRole("ROLE_CAMBIAR-PASSWORD");
		if (verificarChangePassword) {
			verificarChangePassword = false;
		}
		return result;
	}

	@Override
	protected void addFieldGenerator(PanelBeanForm<ChangePassBean> beanForm) {
		beanForm.addFieldGenerator("oldPassword", createPasswordField());
		super.addFieldGenerator(beanForm);
	}

	@Override
	protected void afterInitForm() {
		((ComponentContainer) this.cancelButton.getParent()).removeComponent(cancelButton);
	}

	@Override
	protected String getTitlePanel() {
		return TITLE_PANEL;
	}

}

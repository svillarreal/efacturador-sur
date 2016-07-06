package com.topgroup.blankapp.view.security;

import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

import com.vaadin.ui.Window;

import com.topgroup.blankapp.view.security.bean.BlankPassBean;
import com.topgroup.commons.vaadin.util.VaadinUtil;

@org.springframework.stereotype.Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class BlankPassForm extends BasePassForm<BlankPassBean> {

	private static final long serialVersionUID = -32020363946126372L;

	private static final String TITLE_PANEL = VaadinUtil.getMessage("user.blankpass.title");

	public BlankPassForm() {
		super(TITLE_PANEL);
	}

	@Override
	public void accept(Event event) {
		BlankPassBean bean = form.getBeanItem().getBean();
		userService.blankPassword(bean.getUser().getId(), bean.getPassword());

		Window owner = getWindow().getParent();
		closeVindow();
		VaadinUtil.showMessage(owner, VaadinUtil.getMessage("user.blankpass.message.success", bean.getUsername()));
	}

	@Override
	protected String[] getVisibleItemProperties() {
		return new String[] { "username", "name", "lastName", "password", "confirmPassword" };
	}

	@Override
	protected String getTitlePanel() {
		return TITLE_PANEL;
	}

}

package com.topgroup.blankapp.view.base.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.blankapp.view.security.UserDataForm;
import com.topgroup.blankapp.view.security.bean.UserBean;
import com.topgroup.commons.security.model.Participation;
import com.topgroup.commons.security.model.UserRepresentation;
import com.topgroup.commons.security.service.IParticipationService;
import com.topgroup.commons.security.service.ISystemService;
import com.topgroup.commons.security.util.SecurityUtil;
import com.vaadin.ui.MenuBar.MenuItem;

@SuppressWarnings("serial")
@Component("usuario/datosUsuario")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class DatosUsuariosCommand extends BaseCommand {

	@Autowired
	protected ISystemService systemService;
		
	@Autowired
	private IParticipationService participationService;
		
	@Autowired
	private UserDataForm userData;
	
	@Override
	public void menuSelected(MenuItem selectedItem) {
		Participation participation = participationService.findById(((UserRepresentation) SecurityUtil.getUsuario()).getId(), systemService.getCurrentSystem().getId());
		UserBean userBean = new UserBean();
		userBean.setUser(participation);
		userData.init(userBean, userData);
		window = userData.createWindow();
		super.menuSelected(selectedItem);
	}

}

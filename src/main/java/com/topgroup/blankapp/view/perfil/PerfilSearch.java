package com.topgroup.blankapp.view.perfil;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.topgroup.commons.crud.business.service.CRUDService;
import com.topgroup.commons.crud.command.CrudBaseSearchPanel;
import com.topgroup.commons.crud.model.IEntity;
import com.topgroup.commons.security.service.ISystemService;

@SuppressWarnings("serial")
@Component("perfil/perfilBeanSearch")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class PerfilSearch extends CrudBaseSearchPanel{
	
	@Autowired
	private ISystemService systemService;
	
	@Resource(name = "entityPerfilBean")
	void setEntityPerfilBean(IEntity entity) {
		this.setEntity(entity);
	}

	@Autowired
	@Override
	public void setCrudService(CRUDService<Object> crudService) {
		super.setCrudService(crudService);
	}

	@Resource(name = "perfil/perfilBeanEdit")
	public void setCrudEditDataForm(PerfilCrudEditDataForm perfilCrudEditDataForm) {
		super.setCrudEditDataForm(perfilCrudEditDataForm);
	}

	@Override
	protected boolean isVisibleNewButton() {
		return true;
	}
	
	@Override
	protected void customFilterValues(Map<String, Object> filterValues) {
		filterValues.put("system", systemService.getCurrentSystem());
		super.customFilterValues(filterValues);
	}
		
}

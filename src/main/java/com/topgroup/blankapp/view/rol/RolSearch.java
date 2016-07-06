package com.topgroup.blankapp.view.rol;

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
@Component("rol/rolBeanSearch")
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class RolSearch extends CrudBaseSearchPanel{
	
	@Autowired
	private ISystemService systemService;
	
	@Resource(name = "entityRolBean")
	void setEntityRolBean(IEntity entity) {
		this.setEntity(entity);
	}

	@Autowired
	@Override
	public void setCrudService(CRUDService<Object> crudService) {
		super.setCrudService(crudService);
	}

	@Resource(name = "rol/rolBeanEdit")
	public void setCrudEditDataForm(RolCrudEditDataForm rolCrudEditDataForm) {
		super.setCrudEditDataForm(rolCrudEditDataForm);
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

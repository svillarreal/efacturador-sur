package com.topgroup.blankapp.interceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.topgroup.blankapp.model.RolBean;
import com.topgroup.commons.crud.business.service.IActionInterceptor;
import com.topgroup.commons.crud.business.service.ICRUDService;
import com.topgroup.commons.crud.exception.CrudException;
import com.topgroup.commons.crud.model.CrudError;
import com.topgroup.commons.security.model.Role;
import com.topgroup.commons.security.service.IProfileService;
import com.topgroup.commons.security.service.IRoleService;
import com.topgroup.commons.security.service.ISystemService;

public class RolBeanActionInterceptor implements IActionInterceptor<RolBean> {

	@Autowired
	private ISystemService systemService;
	
	@Autowired
	private IProfileService profileService;
		
	@Autowired
	private IRoleService roleService;
		
	@Override
	public void preHandleSave(RolBean entity) {
		if(entity.getSystem()==null)
			entity.setSystem(systemService.getCurrentSystem());
	}

	@Override
	public void postHandleSave(RolBean entity) {
	}

	@Override
	public void preHandleDelete(RolBean entity, Errors errors) {
		ArrayList<CrudError> crudErrors = new ArrayList<CrudError>();	
		Set<Role> roles = roleService.findRoles(systemService.getCurrentSystem().getId(), entity.getId(), entity.getName());
		if(!profileService.findProfiles(systemService.getCurrentSystem().getId(), null, null, roles).isEmpty()){
				crudErrors.add(new CrudError(null, null, "error.rol.delete", null));
				throw new CrudException(crudErrors);
			}
		}

	@Override
	public void postHandleDelete(Serializable id) {
	}
	
	@Override
	public void setService(ICRUDService<RolBean> service)throws CrudException {
	}
	
}

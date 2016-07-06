package com.topgroup.blankapp.interceptor;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;

import com.topgroup.blankapp.model.PerfilBean;
import com.topgroup.commons.crud.business.service.IActionInterceptor;
import com.topgroup.commons.crud.business.service.ICRUDService;
import com.topgroup.commons.crud.exception.CrudException;
import com.topgroup.commons.crud.model.CrudError;
import com.topgroup.commons.security.service.IParticipationService;
import com.topgroup.commons.security.service.ISystemService;

public class PerfilBeanActionInterceptor implements IActionInterceptor<PerfilBean>{

	@Autowired
	private ISystemService systemService;
	
	@Autowired 
	private IParticipationService participationService;
		
	@Override
	public void preHandleSave(PerfilBean entity) {
		if(entity.getSystem()==null)
			entity.setSystem(systemService.getCurrentSystem());
	}

	@Override
	public void postHandleSave(PerfilBean entity) {
	}

	@Override
	public void preHandleDelete(PerfilBean entity, Errors errors) {
		ArrayList<CrudError> crudErrors = new ArrayList<CrudError>();		
		if(!participationService.loadParticipationsWithProfile(systemService.getCurrentSystem().getId(), entity.getId()).isEmpty()){
			crudErrors.add(new CrudError(null, null, "error.perfil.delete", null));
			throw new CrudException(crudErrors);
		}
	}

	@Override
	public void postHandleDelete(Serializable id) {
	}

	@Override
	public void setService(ICRUDService<PerfilBean> service)throws CrudException {
	}
	
}

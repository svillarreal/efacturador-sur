package com.topgroup.blankapp.converter;

import org.springframework.core.convert.converter.Converter;

import com.topgroup.blankapp.model.AccessRowBean;
import com.topgroup.blankapp.model.AccessId;
import com.topgroup.commons.security.model.Access;

public class AccessRowBeanConverter implements Converter<Access, AccessRowBean>{

	@Override
	public AccessRowBean convert(Access acceso) {
		
		AccessRowBean accessBean = new AccessRowBean();
		
		AccessId id = new AccessId(acceso.getActionId(), acceso.getResourceId());
		accessBean.setId(id);
		accessBean.setActionId(acceso.getActionId());
		accessBean.setActionName(acceso.getActionName());
		accessBean.setActionDescription(acceso.getActionDescription());
		accessBean.setResourceId(acceso.getResourceId());
		accessBean.setResourceName(acceso.getResourceName());
		accessBean.setResourceDescription(acceso.getResourceDescription());
		accessBean.setSystemId(acceso.getSystemId());
		accessBean.setDescripcion(acceso.getActionDescription() + " - " + acceso.getResourceDescription());
		
		return accessBean;
		
	}	
	
}

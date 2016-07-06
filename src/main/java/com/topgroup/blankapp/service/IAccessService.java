package com.topgroup.blankapp.service;

import java.util.Set;

import com.topgroup.commons.security.model.Access;
import com.topgroup.commons.security.service.IBaseService;

public interface IAccessService extends IBaseService<Access> {
	
	Set<Access> findAllAccesses(Integer SystemId);
	
	Set<Access> findAccesses(Integer SystemId, Integer actionId, Integer resourceId);

}

package com.topgroup.blankapp.service;

import java.util.HashSet;
import java.util.Set;

import com.topgroup.blankapp.dao.AccessDao;
import com.topgroup.blankapp.filter.AccessFilter;
import com.topgroup.commons.security.filter.Filter;
import com.topgroup.commons.security.model.Access;
import com.topgroup.commons.security.service.BaseService;

public class AccessService extends BaseService<Access, AccessDao> implements IAccessService {
		
	@Override
	public Set<Access> findAccesses(Integer SystemId, Integer actionId,
			Integer resourceId) {
		Filter filter = new AccessFilter();
		filter.put(AccessFilter.ACCESS_IDSYSTEM, SystemId);
		filter.put(AccessFilter.ACCESS_IDACTION, actionId);
		filter.put(AccessFilter.ACCESS_IDRESOURCE, resourceId);
		return new HashSet<Access>(filter(filter));
	}
	
	@Override
	public Set<Access> findAllAccesses(Integer SystemId) {
		// TODO Auto-generated method stub
		return null;
	}
	
		
}

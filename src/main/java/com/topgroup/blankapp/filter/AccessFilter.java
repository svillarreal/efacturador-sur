package com.topgroup.blankapp.filter;

import org.hibernate.criterion.DetachedCriteria;

import com.topgroup.commons.security.filter.Filter;
import com.topgroup.commons.security.filter.PropertyFilter;

public class AccessFilter extends Filter {

	private static final long serialVersionUID = 7120413285914700711L;
	
	public static final PropertyFilter ACCESS_IDACTION = new PropertyFilter("actionId", "idAction");
	public static final PropertyFilter ACCESS_IDRESOURCE = new PropertyFilter("resourceId", "idResource" );
	public static final PropertyFilter ACCESS_IDSYSTEM = new PropertyFilter("systemId", "idSystem");

	@Override
	protected void load() {
		this.propertyFilters.add(ACCESS_IDACTION);
		this.propertyFilters.add(ACCESS_IDRESOURCE);
		this.propertyFilters.add(ACCESS_IDSYSTEM);
	}

	@Override
	public void setCriteria(DetachedCriteria criteria) {
		restrictionsFilter.eq(criteria, ACCESS_IDACTION);
		restrictionsFilter.eq(criteria, ACCESS_IDRESOURCE);
		restrictionsFilter.eq(criteria, ACCESS_IDSYSTEM);
	}

}

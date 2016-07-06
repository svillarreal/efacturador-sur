package com.topgroup.blankapp.customizer;

import java.util.Collection;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.topgroup.commons.crud.dao.customizer.hibernate.IHibernateQueryCustomizer;
import com.topgroup.commons.security.service.ISystemService;

public class PerfilBeanQueryCustomizer implements IHibernateQueryCustomizer{

	@Autowired
	private ISystemService systemService;
	
	@Override
	public void setParameters(Map<String, Object> parameters) {
	}

	
	@Override
	public String customSQLQuery(String sql) {
		return sql;
	}

	@Override
	public Criteria processCriterias(Criteria criteria,	Collection<Criterion> criterion) {
		for (Criterion c : criterion) {
			criteria.add(c);
		}
		criteria.add(Restrictions.eq("system", systemService.getCurrentSystem()));
		criteria.addOrder(Order.asc("name"));
		return criteria;
	}

	@Override
	public boolean addCriterias() {
		return false;
	}
		
}

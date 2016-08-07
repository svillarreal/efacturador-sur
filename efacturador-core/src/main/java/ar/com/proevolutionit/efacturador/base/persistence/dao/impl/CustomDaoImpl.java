package ar.com.proevolutionit.efacturador.base.persistence.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.topgroup.commons.utils.dao.impl.BaseDaoImpl;
import com.topgroup.commons.utils.lang.BaseFilter;
import com.topgroup.commons.utils.model.Persistence;
import ar.com.proevolutionit.efacturador.base.domain.dto.RangeType;
import ar.com.proevolutionit.efacturador.base.domain.model.Auditable;
import ar.com.proevolutionit.efacturador.base.persistence.dao.CustomDao;
import ar.com.proevolutionit.efacturador.base.persistence.filter.AuditableFilter;

public class CustomDaoImpl<T extends Persistence<PK>, PK extends Serializable> extends BaseDaoImpl<T, PK> implements CustomDao<T, PK> {

	@Autowired
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void saveOrUpdate(T entity) {
		auditEntity(entity);
		super.saveOrUpdate(entity);
	}

	@Override
	public PK save(T entity) {
		auditEntity(entity);
		return super.save(entity);
	}

	/**
	 * Registra los datos de auditoría sobre la entidad.
	 * 
	 * @param entity
	 */
	protected void auditEntity(T entity) {
		if (Auditable.class.isAssignableFrom(entity.getClass())) {
			Auditable<PK> auditable = (Auditable<PK>) entity;
			auditable.audit(getIdUsuario());
		}
	}

	@Override
	public void delete(T entity) {
		if (Auditable.class.isAssignableFrom(entity.getClass())) {
			Auditable<PK> auditable = (Auditable<PK>) entity;
			auditable.delete(getIdUsuario());
			super.saveOrUpdate(entity);
		} else {
			super.delete(entity);
		}
	}

	@Override
	public void deleteAll() {
		getSession().createQuery("DELETE FROM " + getPersistentClass().getSimpleName()).executeUpdate();
	}

	@Override
	protected Criteria createCriteria() {
		Criteria criteria = super.createCriteria();
		if (Auditable.class.isAssignableFrom(getPersistentClass())) {
			criteria.add(Restrictions.eq(Auditable.Attribute.ACTIVO, true));
		}
		return criteria;
	}

	@Override
	protected DetachedCriteria createFilterCriteria(BaseFilter<PK> baseFilter) {
		DetachedCriteria criteria = super.createFilterCriteria(baseFilter);
		// si el objeto admite borrado lógico
		if (Auditable.class.isAssignableFrom(getPersistentClass()) && AuditableFilter.class.isAssignableFrom(baseFilter.getClass())) {
			AuditableFilter<PK> filter = (AuditableFilter<PK>) baseFilter;
			if (filter.getActivo() != null) {
				criteria.add(Restrictions.eq(Auditable.Attribute.ACTIVO, filter.getActivo()));
			}
			if (filter.getFechaAlta() != null) {
				Date fechaInicio = DateUtils.setHours(filter.getFechaAlta(), 0);
				fechaInicio = DateUtils.setMinutes(fechaInicio, 0);
				fechaInicio = DateUtils.setSeconds(fechaInicio, 0);
				fechaInicio = DateUtils.setMilliseconds(fechaInicio, 0);
				Date fechaFin = DateUtils.setHours(filter.getFechaAlta(), 23);
				fechaFin = DateUtils.setMinutes(fechaFin, 59);
				fechaFin = DateUtils.setSeconds(fechaFin, 59);
			}
		}
		return criteria;
	}

	@Override
	public List<T> findAll(String... orderBy) {
		DetachedCriteria criteria = createDetachedCriteria();
		if (Auditable.class.isAssignableFrom(getPersistentClass())) {
			criteria.add(Restrictions.eq(Auditable.Attribute.ACTIVO, true));
		}
		for (String property : orderBy) {
			criteria.addOrder(Order.asc(property));
		}
		return executeCriteria(criteria);
	}

	protected void createRangeRestriction(DetachedCriteria criteria, RangeType<?> range, String field) {
		if (range != null) {
			if (range.getTo() != null && range.getFrom() != null) {
				criteria.add(Restrictions.between(field, range.getFrom(), range.getTo()));
			} else {
				if (range.getFrom() != null) {
					criteria.add(Restrictions.ge(field, range.getFrom()));
				}
				if (range.getTo() != null) {
					criteria.add(Restrictions.le(field, range.getTo()));
				}
			}
		}
	}

	protected long count(DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		Long result = (Long) executeCriteriaForUniqueResult(criteria);
		return ((result == null) ? 0L : result.longValue());
	}

	protected Integer getIdUsuario() {
		return 0;
	}

}
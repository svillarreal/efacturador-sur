package ar.com.proevolutionit.efacturador.base.business.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.topgroup.commons.utils.lang.BaseFilter;
import com.topgroup.commons.utils.lang.Errors;
import com.topgroup.commons.utils.model.Persistence;
import com.topgroup.commons.utils.service.impl.BaseServiceImpl;
import ar.com.proevolutionit.efacturador.base.business.service.CustomService;

@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public abstract class CustomServiceImpl<T extends Persistence<PK>, PK extends Serializable> extends BaseServiceImpl<T, PK>
		implements CustomService<T, PK> {

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public PK save(T entity, Errors errors) {
		return super.save(entity, errors);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void saveOrUpdate(T entity, Errors errors) {
		super.saveOrUpdate(entity, errors);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void delete(T entity, Errors errors) {
		super.delete(entity, errors);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void deleteById(PK id, Errors errors) {
		super.deleteById(id, errors);
	}

	@Override
	public List<T> filter(BaseFilter<PK> filter) {
		return super.filter(filter);
	}

	@Override
	public T getById(PK id) {
		return super.getById(id);
	}

	@Override
	public <E> E getById(PK id, Class<E> returnClass) {
		return super.getById(id, returnClass);
	}

	@Override
	public T findById(PK id) {
		return super.findById(id);
	}

	@Override
	public <E> E findById(PK id, Class<E> returnClass) {
		return super.findById(id, returnClass);
	}

	@Override
	public List<T> findAll(String... orderBy) {
		return super.findAll(orderBy);
	}

	@Override
	public T filterUnique(BaseFilter<PK> filter) {
		return super.filterUnique(filter);
	}

	@Override
	public <E> E filterUnique(BaseFilter<PK> filter, Class<E> returnClass) {
		List<E> results = filter(filter, returnClass);
		return results.isEmpty() ? null : results.get(0);
	}

	@Override
	public <E> List<E> filter(BaseFilter<PK> filter, Class<E> returnClass) {
		return super.filter(filter, returnClass);
	}

	@Override
	public long count(BaseFilter<PK> filter) {
		return super.count(filter);
	}

}

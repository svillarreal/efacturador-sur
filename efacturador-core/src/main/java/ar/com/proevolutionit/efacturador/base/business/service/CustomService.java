package ar.com.proevolutionit.efacturador.base.business.service;

import java.io.Serializable;

import com.topgroup.commons.utils.lang.BaseFilter;
import com.topgroup.commons.utils.model.Persistence;
import com.topgroup.commons.utils.service.BaseService;

public interface CustomService<T extends Persistence<PK>, PK extends Serializable> extends BaseService<T, PK> {

	public <E> E filterUnique(BaseFilter<PK> filter, Class<E> returnClass);

}

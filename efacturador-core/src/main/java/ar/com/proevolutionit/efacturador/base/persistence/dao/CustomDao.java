package ar.com.proevolutionit.efacturador.base.persistence.dao;

import java.io.Serializable;

import com.topgroup.commons.utils.dao.BaseDao;
import com.topgroup.commons.utils.model.Persistence;

public interface CustomDao<T extends Persistence<PK>, PK extends Serializable> extends BaseDao<T, PK> {

	public void deleteAll();

}
